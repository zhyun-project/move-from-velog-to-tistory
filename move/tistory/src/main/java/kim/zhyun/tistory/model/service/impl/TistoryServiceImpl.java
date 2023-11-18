package kim.zhyun.tistory.model.service.impl;

import feign.FeignException;
import kim.zhyun.tistory.client.TistoryClient;
import kim.zhyun.tistory.data.vo.CategoryVo;
import kim.zhyun.tistory.data.vo.Response;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

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

    @Value("${tistory.dev.accessToken}")    private String accessTokenDev;
    @Value("${tistory.dev.blogName}")       private String blogNameDev;

    @Value("${tistory.life.accessToken}")   private String accessTokenLife;
    @Value("${tistory.life.blogName}")      private String blogNameLife;

    @Value("${tistory.output}")             private String output;

    private Map<String, CategoryVo> blogCategory;


    @Override
    public Response<BlogInfoFromTistory> blogInfo() {

        return tistoryClient.blogInfo(accessTokenDev, output);
    }

    @Override
    public Map<String, CategoryVo> getCategory() {

        categorySaveH2DB(accessTokenDev, blogNameDev);
        categorySaveH2DB(accessTokenLife, blogNameLife);

        return blogCategory = getCategoryMap();
    }

    @Override
    public void fileUpload() {
        postRepository.findAll().stream()
                .filter(post -> post.getPhotos().size() > 0)
                .forEach(post -> {
                    String nowPostBlogName = post.getBlogName();
                    String accesstoken = nowPostBlogName.equals(blogNameDev)
                            ? accessTokenDev
                            : accessTokenLife;

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
    public Response<PostFromTistory> postUpload() {

        String replacer;

        // series

        // TODO: 기능 구현 해야 됨
        RequestPostWrite requestPostWrite = RequestPostWrite.builder()
                .access_token("")
                .blogName("")
                .title("")
                .content("")
                .category("")
                .slogan("") // url-slug
                .tag("").build();

        Response<PostFromTistory> response = clientMappedPostDto(requestPostWrite);

        log.info("Post Url = {}", response.getTistory().getUrl());
        return response;
    }



    private Response<PostFromTistory> clientMappedPostDto(RequestPostWrite requestPostWrite) {

        return tistoryClient.postUpload(
                requestPostWrite.getAccess_token(),
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
        return tistoryClient.fileUpload(accessToken, output, blogName, mFile);
    }

    private void categorySaveH2DB(String accessToken, String blogName) {
        tistoryClient.getCategory(accessToken, output, blogName).getTistory().getItem().getCategories()
                .forEach(categoryInfo -> categoryRepository
                        .save(Category.builder()
                                .blogName(blogName)
                                .categoryId(categoryInfo.getId())
                                .categoryName(categoryInfo.getName()).build()));
    }

    private Map<String, CategoryVo> getCategoryMap() {
        if (blogCategory == null) {
            blogCategory = new HashMap<>();

            categoryRepository.findAll()
                    .forEach(category -> {
                        String blogName = category.getBlogName().equals(blogNameDev)
                                ? blogNameDev
                                : blogNameLife;

                        blogCategory.put(category.getCategoryName(), CategoryVo.of(blogName, category.getCategoryId()));
                    });
        }

        return blogCategory;
    }

}
