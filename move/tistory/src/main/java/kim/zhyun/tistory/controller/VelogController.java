package kim.zhyun.tistory.controller;

import kim.zhyun.tistory.model.service.VelogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class VelogController {
    private final VelogService velogService;

    @GetMapping("/post/save-from-velog")
    public void savePost() {
        velogService.savePostDataFromVelog();
    }

}
