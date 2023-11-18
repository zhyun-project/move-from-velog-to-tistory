package kim.zhyun.tistory.controller;

import kim.zhyun.tistory.model.service.TransDataService;
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
    private final TransDataService transformService;

    /**
     * `velog module`에서 post 데이터와 photo 데이터 받아와서 tistory h2 db에 저장
     */
    @GetMapping("/post/save-from-velog")
    public void savePost() {
        velogService.savePostDataFromVelog();
    }

    /**
     * `tistory module`의 ` h2 db post table`에 저장 된 htmlContent에서
     * keyword를 replacer로 치환하는 작업 수행
     */
    @GetMapping("/content/transform/keyword")
    public void transformKeywordReplacer() {
        transformService.transformContentImgKeywordToReplacer();
    }

}
