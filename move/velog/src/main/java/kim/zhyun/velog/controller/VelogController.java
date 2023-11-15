package kim.zhyun.velog.controller;

import kim.zhyun.velog.model.entity.Post;
import kim.zhyun.velog.model.service.VelogService;
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

    @GetMapping("/posts")
    public List<Post> posts() {

        return service.saveAllPosts();
    }

}
