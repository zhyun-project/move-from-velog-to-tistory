package kim.zhyun.velog.model.service.impl;

import kim.zhyun.velog.client.VelogClient;
import kim.zhyun.velog.data.dto.PostsDto;
import kim.zhyun.velog.data.vo.OperationName;
import kim.zhyun.velog.data.vo.Query;
import kim.zhyun.velog.data.vo.request.Request;
import kim.zhyun.velog.data.vo.request.RequestPostDetail;
import kim.zhyun.velog.data.vo.request.RequestPosts;
import kim.zhyun.velog.data.vo.response.Response;
import kim.zhyun.velog.data.vo.response.ResponsePosts;
import kim.zhyun.velog.data.vo.response.SeriesVo;
import kim.zhyun.velog.model.entity.Photo;
import kim.zhyun.velog.model.entity.Post;
import kim.zhyun.velog.model.repository.PhotoRepository;
import kim.zhyun.velog.model.repository.PostRepository;
import kim.zhyun.velog.model.service.VelogService;
import kim.zhyun.velog.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class VelogServiceImpl implements VelogService {
    private final VelogClient client;
    private final OperationName operationName;
    private final Query query;

    private final PostRepository postRepository;
    private final PhotoRepository photoRepository;
    private final FileUtils fileUtils;

    @Value("${velog.username}")     private String USERNAME;
    @Value("${imgs.dir}")           private String dir;
    @Value("${imgs.keywordFormat}") private String keywordFormat;
    @Value("${user.dir}")           private String imgPathRoot;

    @Override
    public List<Post> saveAllPosts() {
        String cursor = null;

        while (true) {
            Request<RequestPosts> request = getRequestPosts(cursor);
            Response<ResponsePosts> response = client.getPosts(request);

            if (response.getData().getPosts().size() == 0)
                break;

            List<ResponsePosts> responsePosts = response.getData().getPosts();

            postRepository.saveAll(responsePosts.stream()
                    .map(PostsDto::to)
                    .toList());

            cursor = responsePosts.get(responsePosts.size() - 1).getId();
        }

        return postRepository.findAll();
    }

    @Transactional
    @Override
    public List<Post> savePost() {
        String sep = "![](http";

        postRepository.findAll()
                .forEach(dbPost -> {
                    var responsePostDetail = client
                            .getPostDetail(getRequestPostDetail(dbPost.getUrlSlug()))
                            .getData().getPost();

                    String body = responsePostDetail.getBody();
                    // 혹시 모를 일을 대비해 원본 body는 미리 db에 저장
                    dbPost.setOriginBody(body);

                    // 원래 있던 이미지 url을 대체 키워드로 치환할 원본 body(본문) 카피
                    String changeBody = body;

                    // 파일 변환 작업
                    // ![](https://velog.velcdn.com/images/zhyun/post/64797348-5c69-4d69-854c-8a6ec0beb20e/image.png)\n\n<br>
                    if (body.contains(sep)) {
                        String[] arr = body
                                .substring(body.indexOf(sep) + sep.length())
                                .split("!\\[]\\(http");

                        for (String source: arr) {
                            // https://cloudflare.com/asdh/sdf/images.png
                            String imgUrl = String.format(
                                    "http%s",
                                    source.substring(0, source.indexOf(")")));

                            // filname.png
                            String filename = String.format(
                                    "%s%s",
                                    UUID.randomUUID().toString(),
                                    imgUrl.substring(imgUrl.lastIndexOf(".")));

                            // 프로젝트-루트-디렉토리-절대경로/imgs/velog
                            String path = imgPathRoot + dir;
                            fileDownloadProcess(path, dir, imgUrl, filename); // path에 파일 생성

                            // 원본 body의 이미지 주소값이랑 치환 할 키워드 생성
                            String replaceKeyword = String.format(keywordFormat, UUID.randomUUID().toString());
                            changeBody = changeBody.replace(imgUrl, replaceKeyword);

                            // 사진 정보 db 저장 
                            photoRepository.save(Photo.builder()
                                            .keyword(replaceKeyword)
                                            .imgLocalPath(path + filename)
                                            .imgOriginPath(imgUrl)
                                    .build());
                        }
                        // 이미지 주소값이랑 치환 된 body를 post 테이블에 저장
                        dbPost.setChangedBody(changeBody);
                    }

                    // 카테고리 있는경우 저장
                    SeriesVo series = responsePostDetail.getSeries();
                    if (series != null) {
                        dbPost.setSeriesName(series.getName());
                    }
                });

        return postRepository.findAll();
    }

    // 실제 파일 다운로드
    private void fileDownloadProcess(String path, String dir, String imgUrl, String filename) {
        try {
            byte[] image = fileUtils.patchImage(imgUrl);
            fileUtils.makeDirectory(imgPathRoot, dir);
            fileUtils.download(path, filename, image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // post detail 요청값 생성
    private Request<RequestPostDetail> getRequestPostDetail(String urlSlug) {

        return Request.<RequestPostDetail>builder()
                .operationName(operationName.post())
                .variables(RequestPostDetail.builder()
                        .username(USERNAME)
                        .url_slug(urlSlug).build())
                .query(query.post()).build();
    }


    // posts 요청값 생성
    private Request<RequestPosts> getRequestPosts(String cursor) {

        return Request.<RequestPosts>builder()
                .operationName(operationName.posts())
                .variables(RequestPosts.builder()
                        .username(USERNAME)
                        .cursor(cursor)
                        .limit(100).build())
                .query(query.posts()).build();
    }

}
