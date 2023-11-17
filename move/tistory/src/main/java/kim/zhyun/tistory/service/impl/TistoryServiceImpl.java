package kim.zhyun.tistory.service.impl;

import kim.zhyun.tistory.client.TistoryClient;
import kim.zhyun.tistory.service.TistoryService;
import kim.zhyun.tistory.vo.Response;
import kim.zhyun.tistory.vo.request.RequestPostWrite;
import kim.zhyun.tistory.vo.response.BlogInfoFromTistory;
import kim.zhyun.tistory.vo.response.CategoryFromTistory;
import kim.zhyun.tistory.vo.response.PhotoFromTistory;
import kim.zhyun.tistory.vo.response.PostFromTistory;
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

    @Value("${tistory.accessToken}")    private String accessToken;
    @Value("${tistory.blogName}")       private String blogName;
    @Value("${tistory.output}")         private String output;

    @Override
    public Response<BlogInfoFromTistory> blogInfo() {

        return tistoryClient.blogInfo(accessToken, output);
    }

    @Override
    public Response<CategoryFromTistory> getCategory() {
        // TODO: 기능 구현 해야 됨 > H2 저장

        return tistoryClient.getCategory(accessToken, output, blogName);
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

}
