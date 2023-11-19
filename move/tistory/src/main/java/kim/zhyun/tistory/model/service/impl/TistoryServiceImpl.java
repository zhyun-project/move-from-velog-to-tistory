package kim.zhyun.tistory.model.service.impl;

import feign.FeignException;
import kim.zhyun.tistory.client.TistoryClient;
import kim.zhyun.tistory.data.dto.PostDto;
import kim.zhyun.tistory.data.vo.CategoryVo;
import kim.zhyun.tistory.data.vo.Response;
import kim.zhyun.tistory.data.vo.TistoryConnectVo;
import kim.zhyun.tistory.data.vo.request.RequestPostWrite;
import kim.zhyun.tistory.data.vo.response.BlogInfoFromTistory;
import kim.zhyun.tistory.data.vo.response.PostFromTistory;
import kim.zhyun.tistory.model.entity.Category;
import kim.zhyun.tistory.model.repository.CategoryRepository;
import kim.zhyun.tistory.model.repository.PostRepository;
import kim.zhyun.tistory.model.service.TistoryService;
import kim.zhyun.tistory.model.service.TransDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.data.domain.Sort.Order.asc;

@Slf4j
@RefreshScope
@RequiredArgsConstructor
@Transactional(noRollbackFor = { FeignException.class })
@Service
public class TistoryServiceImpl implements TistoryService {
    private final TistoryClient tistoryClient;

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    private final TransDataService transDataService;

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

    @Override
    public void postUpload() {
        // 업로드 할 게시글 13개 중 사진 있는 경우 사진만 먼저 티스토리 업로드후 게시글에 반영
        transDataService.transformContentImgKeywordToReplacer();

        AtomicInteger cnt = new AtomicInteger();
        postRepository
                .findTop13ByUploadYnIsFalseAndReplacerContentIsNotNull(Sort.by(asc("published")))
                .forEach(post -> {
                    String accessToken = post.getBlogName().equals(tistoryConnect.getBlogNameDev())
                            ? tistoryConnect.getAccessTokenDev()
                            : tistoryConnect.getAccessTokenLife();

                    var response = clientMappedPostDto(PostDto.from(post, accessToken));
                    post.setUploadYn(true);

                    log.info("Post Url = {}", response.getTistory().getUrl());
                    cnt.getAndIncrement();
                });

        log.info("{}개의 Post가 업로드 되었습니다.", cnt);
    }



    private Response<PostFromTistory> clientMappedPostDto(RequestPostWrite requestPostWrite) {

        return tistoryClient.postUpload(tistoryConnect.getOutput(), requestPostWrite);
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
