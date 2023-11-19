package kim.zhyun.tistory.controller;

import kim.zhyun.tistory.data.vo.CategoryVo;
import kim.zhyun.tistory.data.vo.Response;
import kim.zhyun.tistory.data.vo.response.BlogInfoFromTistory;
import kim.zhyun.tistory.model.service.TistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class TistoryController {

    private final TistoryService tistoryService;

    /**
     * `tistory`에서 blog 정보 조회
     */
    @GetMapping("/blog-info")
    public Response<BlogInfoFromTistory> blogInfo() {

        return tistoryService.blogInfo();
    }

    /**
     * `tistory`에서 category 정보 조회 후 h2 db 저장
     */
    @GetMapping("/category")
    public Map<String, CategoryVo> category() {

        return tistoryService.getCategory();
    }

    /**
     * `tistory`에 post upload
     * - 매일 0시 1분 실행
     */
    @Scheduled(cron = "0 1 0 * * *", zone = "Asia/Seoul")
    @GetMapping("/post-upload")
    public void postUpload() {
        tistoryService.postUpload();
    }

}
