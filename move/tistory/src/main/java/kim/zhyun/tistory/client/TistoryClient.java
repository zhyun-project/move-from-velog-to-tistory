package kim.zhyun.tistory.client;

import kim.zhyun.tistory.vo.*;
import kim.zhyun.tistory.vo.request.RequestBlogInfo;
import kim.zhyun.tistory.vo.request.RequestCategory;
import kim.zhyun.tistory.vo.request.RequestFileupload;
import kim.zhyun.tistory.vo.request.RequestPostWrite;
import kim.zhyun.tistory.vo.response.BlogInfoFromTistory;
import kim.zhyun.tistory.vo.response.CategoryFromTistory;
import kim.zhyun.tistory.vo.response.PhotoFromTistory;
import kim.zhyun.tistory.vo.response.PostFromTistory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@FeignClient(name = "tistoryClient", url = "https://www.tistory.com/apis")
public interface TistoryClient {

    @GetMapping("/blog/info")
    Response<BlogInfoFromTistory> blogInfo(@RequestParam RequestBlogInfo requestBlogInfo);

    @PostMapping("/post/write")
    Response<PostFromTistory> postUpload(@RequestParam RequestPostWrite requestPostWrite);

    @PostMapping("/post/attach")
    Response<PhotoFromTistory> fileUpload(@RequestHeader(name = "Content-Type") String ContentType,
                                          @RequestParam RequestFileupload requestFileupload,
                                          @RequestBody File uploadedfile);

    @GetMapping("/category/list")
    Response<CategoryFromTistory> getCategory(@RequestParam RequestCategory requestCategory);

}
