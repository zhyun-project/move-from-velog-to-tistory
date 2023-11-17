package kim.zhyun.tistory.controller;

import kim.zhyun.tistory.service.TistoryService;
import kim.zhyun.tistory.vo.Response;
import kim.zhyun.tistory.vo.response.BlogInfoFromTistory;
import kim.zhyun.tistory.vo.response.CategoryFromTistory;
import kim.zhyun.tistory.vo.response.PhotoFromTistory;
import kim.zhyun.tistory.vo.response.PostFromTistory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TistoryController {

    private final TistoryService tistoryService;

    @GetMapping("/blog-info")
    public Response<BlogInfoFromTistory> blogInfo() {

        return tistoryService.blogInfo();
    }

    @GetMapping("/category")
    public Response<CategoryFromTistory> category() {

        return tistoryService.getCategory();
    }

    @GetMapping("/file-upload")
    public Response<PhotoFromTistory> fileUpload() {

        return tistoryService.fileUpload();
    }

    @GetMapping("/post-upload")
    public Response<PostFromTistory> postUpload() {

        return tistoryService.postUpload();
    }

}
