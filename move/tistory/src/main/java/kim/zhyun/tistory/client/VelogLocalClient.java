package kim.zhyun.tistory.client;

import kim.zhyun.tistory.config.FeignConfig;
import kim.zhyun.tistory.data.vo.response.PhotosFromVelog;
import kim.zhyun.tistory.data.vo.response.PostsFromVelog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "velogLocalClient", url = "http://localhost:9090/", configuration = FeignConfig.class)
public interface VelogLocalClient {

    @GetMapping("/find-all-posts")
    List<PostsFromVelog> getPostsFromH2Velog();

    @GetMapping("/find-all-photo")
    List<PhotosFromVelog> getPhotosFromH2Velog();

}
