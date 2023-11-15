package kim.zhyun.velog.client;

import kim.zhyun.velog.data.vo.request.Request;
import kim.zhyun.velog.data.vo.request.RequestPostDetail;
import kim.zhyun.velog.data.vo.request.RequestPosts;
import kim.zhyun.velog.data.vo.response.Response;
import kim.zhyun.velog.data.vo.response.ResponsePosts;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "velogClient", url = "https://v2.velog.io/graphql")
public interface VelogClient {

    @PostMapping
    Response<ResponsePosts> getPosts(@RequestBody Request<RequestPosts> req);

    @PostMapping
    Response getPostDetail(@RequestBody Request<RequestPostDetail> req);

}
