package kim.zhyun.tistory.controller;

import kim.zhyun.tistory.data.vo.CategoryVo;
import kim.zhyun.tistory.data.vo.Response;
import kim.zhyun.tistory.data.vo.response.BlogInfoFromTistory;
import kim.zhyun.tistory.data.vo.response.PostFromTistory;
import kim.zhyun.tistory.model.service.TistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class TistoryController {

    private final TistoryService tistoryService;

    @GetMapping("/blog-info")
    public Response<BlogInfoFromTistory> blogInfo() {

        return tistoryService.blogInfo();
    }

    @GetMapping("/category")
    public Map<String, CategoryVo> category() {

        return tistoryService.getCategory();
    }

    @GetMapping("/file-upload")
    public void fileUpload() {
        tistoryService.fileUpload();
    }

    @GetMapping("/post-upload")
    public Response<PostFromTistory> postUpload() {

        return tistoryService.postUpload();
    }

}
