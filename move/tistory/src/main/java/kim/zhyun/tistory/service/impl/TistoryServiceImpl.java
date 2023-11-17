package kim.zhyun.tistory.service.impl;

import kim.zhyun.tistory.client.TistoryClient;
import kim.zhyun.tistory.service.TistoryService;
import kim.zhyun.tistory.vo.request.RequestBlogInfo;
import kim.zhyun.tistory.vo.request.RequestCategory;
import kim.zhyun.tistory.vo.request.RequestFileupload;
import kim.zhyun.tistory.vo.response.BlogInfoFromTistory;
import kim.zhyun.tistory.vo.response.CategoryFromTistory;
import kim.zhyun.tistory.vo.response.PhotoFromTistory;
import kim.zhyun.tistory.vo.response.PostFromTistory;
import kim.zhyun.tistory.vo.request.RequestPostWrite;
import kim.zhyun.tistory.vo.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Slf4j
@RequiredArgsConstructor
@Service
public class TistoryServiceImpl implements TistoryService {
    private final TistoryClient tistoryClient;

    @Value("${tistory.accessToken}") private String accessToken;

    @Override
    public Response<BlogInfoFromTistory> blogInfo() {

        return tistoryClient.blogInfo(RequestBlogInfo.of(accessToken));
    }

    public Response<PostFromTistory> postUpload() {
        // TODO: 기능 구현 해야 됨 
        Response<PostFromTistory> response = tistoryClient.postUpload(RequestPostWrite.builder()
                .access_token(accessToken)
                .title("")
                .content("")
                .category("")
                .slogan("") // url-slug
                .tag("").build());

        log.info("Post Url = {}", response.getTistory().getUrl());
        return response;
    }

    @Override
    public Response<PhotoFromTistory> fileUpload() {
        // TODO: 기능 구현 해야 됨 > 파일 업로드 후 H2 저장

        return tistoryClient.fileUpload("multipart/form-data",
                RequestFileupload.of(accessToken),
                new File("pathname"));
    }

    @Override
    public Response<CategoryFromTistory> getCategory() {
        // TODO: 기능 구현 해야 됨 > H2 저장
        
        return tistoryClient.getCategory(RequestCategory.of(accessToken));
    }


}
