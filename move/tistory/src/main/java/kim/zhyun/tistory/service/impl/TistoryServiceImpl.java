package kim.zhyun.tistory.service.impl;

import kim.zhyun.tistory.client.TistoryClient;
import kim.zhyun.tistory.client.VelogLocalClient;
import kim.zhyun.tistory.model.entity.Category;
import kim.zhyun.tistory.model.repository.CategoryRepository;
import kim.zhyun.tistory.model.repository.PhotoRepository;
import kim.zhyun.tistory.model.repository.PostRepository;
import kim.zhyun.tistory.service.TistoryService;
import kim.zhyun.tistory.vo.CategoryVo;
import kim.zhyun.tistory.vo.Response;
import kim.zhyun.tistory.vo.request.RequestPostWrite;
import kim.zhyun.tistory.vo.response.BlogInfoFromTistory;
import kim.zhyun.tistory.vo.response.PhotoFromTistory;
import kim.zhyun.tistory.vo.response.PostFromTistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RefreshScope
@RequiredArgsConstructor
@Service
public class TistoryServiceImpl implements TistoryService {
    private final TistoryClient tistoryClient;
    private final VelogLocalClient velogClient;

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
    public Response<PhotoFromTistory> fileUpload() {
        // TODO: 기능 구현 해야 됨 > 파일 업로드 후 H2 저장

        return clientMappedFile("pathname");
    }

    public Response<PostFromTistory> postUpload() {
        // TODO: 기능 구현 해야 됨 
        RequestPostWrite requestPostWrite = RequestPostWrite.builder()
                .access_token(accessToken)
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

        return tistoryClient.postUpload(accessToken,
                blogName,
                requestPostWrite.getTitle(),
                requestPostWrite.getContent(),
                requestPostWrite.getVisibility(), // 비공개 : 0, 공개 : 3
                requestPostWrite.getCategory(),
                requestPostWrite.getPublished(),
                requestPostWrite.getSlogan(),
                requestPostWrite.getTag(),
                1);
    }

    private Response<PhotoFromTistory> clientMappedFile(String pathname) {

        return tistoryClient.fileUpload("multipart/form-data",
                accessToken, blogName,
                new File(pathname));
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
