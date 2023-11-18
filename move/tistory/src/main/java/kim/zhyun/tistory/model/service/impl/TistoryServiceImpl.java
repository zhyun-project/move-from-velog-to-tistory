package kim.zhyun.tistory.model.service.impl;

import feign.FeignException;
import kim.zhyun.tistory.client.TistoryClient;
import kim.zhyun.tistory.data.dto.PostDto;
import kim.zhyun.tistory.data.vo.CategoryVo;
import kim.zhyun.tistory.data.vo.Response;
import kim.zhyun.tistory.data.vo.TistoryConnectVo;
import kim.zhyun.tistory.data.vo.request.RequestPostWrite;
import kim.zhyun.tistory.data.vo.response.BlogInfoFromTistory;
import kim.zhyun.tistory.data.vo.response.PhotoFromTistory;
import kim.zhyun.tistory.data.vo.response.PostFromTistory;
import kim.zhyun.tistory.model.entity.Category;
import kim.zhyun.tistory.model.repository.CategoryRepository;
import kim.zhyun.tistory.model.repository.PhotoRepository;
import kim.zhyun.tistory.model.repository.PostRepository;
import kim.zhyun.tistory.model.service.TistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RefreshScope
@RequiredArgsConstructor
@Transactional(noRollbackFor = { FeignException.BadRequest.class })
@Service
public class TistoryServiceImpl implements TistoryService {
    private final TistoryClient tistoryClient;

    private final PostRepository postRepository;
    private final PhotoRepository photoRepository;
    private final CategoryRepository categoryRepository;

    private final TistoryConnectVo tistoryConnect;

    @Override
    public Response<BlogInfoFromTistory> blogInfo() {

        return tistoryClient.blogInfo(
                tistoryConnect.getAccessTokenDev(),
                tistoryConnect.getOutput());
    }

    @Override
    public Map<String, CategoryVo> getCategory() {

        categorySaveH2DB(tistoryConnect.getAccessTokenDev(), tistoryConnect.getBlogNameDev());
        categorySaveH2DB(tistoryConnect.getAccessTokenLife(), tistoryConnect.getBlogNameLife());

        return getCategoryMap();
    }

    /**
     * TODO: 첨부 파일 업로드 프로세스 수정 필요
     * 첨부 파일 수량이 앞으로 늘어나지 않고,
     * 지금 가지고 있는 파일만 올리는거라서 한번에 전체(1190개) 업로드하게끔 작업했는데
     * 소량 업로드 되도록 수정 필요
     * - 90일동안 500개의 첨부파일이 올라가면 티스토리에 사유서를 제출해야 하는것 같았다. 일단 업로드 제한에 걸림..
     * - https://diary.zhyun.kim/15
     */
    @Override
    public void fileUpload() {
        postRepository.findAll().stream()
                .filter(post -> post.getPhotos().size() > 0)
                .forEach(post -> {
                    String nowPostBlogName = post.getBlogName();
                    String accesstoken = nowPostBlogName.equals(tistoryConnect.getBlogNameDev())
                            ? tistoryConnect.getAccessTokenDev()
                            : tistoryConnect.getAccessTokenLife();

                    post.getPhotos().stream()
                            .filter(photo -> photo.getReplacer() == null)
                            .forEach(photo -> {
                                Response<PhotoFromTistory> response = null;
                                try {
                                    response = clientMappedFile(accesstoken, nowPostBlogName, photo.getImgLocalPath());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                photo.setReplacer(response.getTistory().getReplacer());

                                // 파일 전송시 갯수 짤리는 순간을 예측하기 어려워서 건별로 저장
                                photoRepository.saveAndFlush(photo);
                            });
                });
    }

    @Override
    public void postUpload() {
        AtomicInteger cnt = new AtomicInteger();
        postRepository.findAll()
                .stream()
                .filter(post -> !post.isUploadYn())
                .filter(post -> post.getReplacerContent() != null)
                .limit(15)
                .forEach(post -> {
                    String accessToken = post.getBlogName().equals(tistoryConnect.getBlogNameDev())
                            ? tistoryConnect.getAccessTokenDev()
                            : tistoryConnect.getAccessTokenLife();

                    var response = clientMappedPostDto(PostDto.from(post, accessToken));

                    log.info("Post Url = {}", response.getTistory().getUrl());
                    post.setUploadYn(true);
                    cnt.getAndIncrement();
                });

        log.info("{}개의 Post가 업로드 되었습니다.", cnt);
    }



    private Response<PostFromTistory> clientMappedPostDto(RequestPostWrite requestPostWrite) {

        return tistoryClient.postUpload(
                requestPostWrite.getAccess_token(),
                tistoryConnect.getOutput(),
                requestPostWrite.getBlogName(),

                requestPostWrite.getTitle(),
                requestPostWrite.getContent(),
                requestPostWrite.getVisibility(), // 비공개 : 0, 공개 : 3
                requestPostWrite.getCategory(),
                requestPostWrite.getPublished(),
                requestPostWrite.getSlogan(),
                requestPostWrite.getTag(),
                1);
    }

    private Response<PhotoFromTistory> clientMappedFile(String accessToken,
                                                        String blogName,
                                                        String pathname) throws IOException {
        File file = new File(pathname);
        FileItem fileItem = new DiskFileItem("file", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());

        try {
            InputStream input = new FileInputStream(file);
            OutputStream os = fileItem.getOutputStream();
            IOUtils.copy(input, os);
        } catch (IOException ex) {
            log.info(ex.getMessage());
        }

        MultipartFile mFile = new CommonsMultipartFile(fileItem);
        return tistoryClient.fileUpload(accessToken, tistoryConnect.getOutput(), blogName, mFile);
    }

    private void categorySaveH2DB(String accessToken, String blogName) {
        tistoryClient.getCategory(accessToken, tistoryConnect.getOutput(), blogName).getTistory().getItem().getCategories()
                .forEach(categoryInfo -> categoryRepository
                        .save(Category.builder()
                                .blogName(blogName)
                                .categoryId(categoryInfo.getId())
                                .categoryName(categoryInfo.getName()).build()));
    }

    private Map<String, CategoryVo> getCategoryMap() {
        Map<String, CategoryVo> blogCategory = new HashMap<>();

        categoryRepository.findAll()
                .forEach(category -> {
                    String blogName = category.getBlogName().equals(tistoryConnect.getBlogNameDev())
                            ? tistoryConnect.getBlogNameDev()
                            : tistoryConnect.getBlogNameLife();

                    blogCategory.put(category.getCategoryName(), CategoryVo.of(blogName, category.getCategoryId()));
                });

        return blogCategory;
    }

}
