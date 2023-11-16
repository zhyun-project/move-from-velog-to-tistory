package kim.zhyun.velog.controller;

import kim.zhyun.velog.model.entity.Post;
import kim.zhyun.velog.model.service.VelogService;
import kim.zhyun.velog.model.service.impl.GithubApiServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class VelogController {
    private final VelogService service;
    private final GithubApiServiceImpl gitService;

    @GetMapping("/posts")
    public List<Post> posts() {

        return service.saveAllPosts();
    }

    @GetMapping("/post-detail")
    public List<Post> saveBody() {

        return service.savePost();
    }

    @GetMapping("/markdown-to-html")
    public void convert() {
        gitService.convertMarkdownToHtml();
    }

}
