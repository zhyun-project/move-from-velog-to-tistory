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

    /**
     * `velog module`에서 post 데이터와 photo 데이터 받아와서 tistory h2 db에 저장
     */
    @GetMapping("/post/save-from-velog")
    public void savePost() {
        velogService.savePostDataFromVelog();
    }

}
