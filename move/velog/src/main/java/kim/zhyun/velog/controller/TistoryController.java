package kim.zhyun.velog.controller;

import kim.zhyun.velog.data.vo.response.ResponsePhotoToTistory;
import kim.zhyun.velog.data.vo.response.ResponsePostsToTistory;
import kim.zhyun.velog.model.service.VelogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TistoryController {
    private final VelogService service;

    @GetMapping("/find-all-posts")
    public List<ResponsePostsToTistory> findAllPost() {

        return service.findAllPost();
    }

    @GetMapping("/find-all-photo")
    public List<ResponsePhotoToTistory> findAllPhoto() {

        return service.findAllPhoto();
    }

}
