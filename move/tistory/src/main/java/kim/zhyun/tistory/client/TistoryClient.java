package kim.zhyun.tistory.client;

import kim.zhyun.tistory.config.FeignConfig;
import kim.zhyun.tistory.data.vo.Response;
import kim.zhyun.tistory.data.vo.request.RequestPostWrite;
import kim.zhyun.tistory.data.vo.response.BlogInfoFromTistory;
import kim.zhyun.tistory.data.vo.response.CategoryFromTistory;
import kim.zhyun.tistory.data.vo.response.PhotoFromTistory;
import kim.zhyun.tistory.data.vo.response.PostFromTistory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "tistoryClient", url = "https://www.tistory.com/apis", configuration = FeignConfig.class)
public interface TistoryClient {

    @GetMapping("/blog/info")
    Response<BlogInfoFromTistory> blogInfo(@RequestParam String access_token,
                                           @RequestParam String output);

    @PostMapping("/post/write")
    Response<PostFromTistory> postUpload(@RequestParam String output,
                                         @RequestBody RequestPostWrite request);

    @PostMapping(value = "/post/attach", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/json")
    Response<PhotoFromTistory> fileUpload(@RequestParam String access_token,
                                          @RequestParam String output,
                                          @RequestParam String blogName,
                                          @RequestPart MultipartFile uploadedfile);

    @GetMapping("/category/list")
    Response<CategoryFromTistory> getCategory(@RequestParam String access_token,
                                              @RequestParam String output,
                                              @RequestParam String blogName);

}
