package kim.zhyun.velog.controller;

import kim.zhyun.velog.model.service.GithubApiService;
import kim.zhyun.velog.model.service.TransDataService;
import kim.zhyun.velog.model.service.VelogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class VelogController {
    private final VelogService service;
    private final GithubApiService gitService;
    private final TransDataService transDataService;

    /**
     * `H2 DB post TABLE`에
     * `body`, `series`를 제외한 데이터를 저장하는 end point
     */
    @GetMapping("/posts")
    public String posts() {

        return String.format("%d개의 post가 저장되었습니다 👍", service.saveAllPosts());
    }

    /**
     * `H2 DB post TABLE`에
     * `series name`과 `body` 데이터를 저장하는 end point
     */
    @GetMapping("/post-detail")
    public String saveBody() {

        return String.format("%d개의 post가 저장되었습니다 👍", service.savePost());
    }

    /**
     * `H2 DB post TABLE`의
     * markdown body를 html body로 변환 후 저장하는 end point
     */
    @GetMapping("/markdown-to-html")
    public void convert() {
        gitService.convertMarkdownToHtml();
    }

    /**
     * `H2 DB photo TABLE`에
     * post seq 저장하는 end point
     */
    @GetMapping("/update-photo-post-seq")
    public void AddPostSeqToPhoto() {
        var mapPhotoKeywordPostSeq = transDataService.mapPhotoKeywordPostSeq();
        transDataService.updatePhoto(mapPhotoKeywordPostSeq);
    }

}
