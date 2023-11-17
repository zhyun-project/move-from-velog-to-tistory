package kim.zhyun.velog.controller;

import kim.zhyun.velog.data.vo.response.ResponsePhotoToTistory;
import kim.zhyun.velog.data.vo.response.ResponsePostsToTistory;
import kim.zhyun.velog.model.service.VelogService;
import kim.zhyun.velog.model.service.impl.GithubApiServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class VelogController {
    private final VelogService service;
    private final GithubApiServiceImpl gitService;

    @GetMapping("/posts")
    public String posts() {

        return String.format("%d개의 post가 저장되었습니다 👍", service.saveAllPosts());
    }

    @GetMapping("/post-detail")
    public String saveBody() {

        return String.format("%d개의 post가 저장되었습니다 👍", service.savePost());
    }

    @GetMapping("/markdown-to-html")
    public void convert() {
        gitService.convertMarkdownToHtml();
    }

    @GetMapping("/find-all-posts")
    public List<ResponsePostsToTistory> findAllPost() {

        return service.findAllPost();
    }

    @GetMapping("/find-all-photo")
    public List<ResponsePhotoToTistory> findAllPhoto() {

        return service.findAllPhoto();
    }

}
