package kim.zhyun.tistory.client;

import kim.zhyun.tistory.config.FeignConfig;
import kim.zhyun.tistory.data.vo.Response;
import kim.zhyun.tistory.data.vo.response.BlogInfoFromTistory;
import kim.zhyun.tistory.data.vo.response.CategoryFromTistory;
import kim.zhyun.tistory.data.vo.response.PhotoFromTistory;
import kim.zhyun.tistory.data.vo.response.PostFromTistory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "tistoryClient", url = "https://www.tistory.com/apis", configuration = FeignConfig.class)
public interface TistoryClient {

    @GetMapping("/blog/info")
    Response<BlogInfoFromTistory> blogInfo(@RequestParam String access_token,
                                           @RequestParam String output);

    @PostMapping("/post/write")
    Response<PostFromTistory> postUpload(@RequestParam String access_token,
                                         @RequestParam String blogName,    //  Blog Name (필수)
                                         @RequestParam String title,       //  글 제목 (필수)
                                         @RequestParam String content,     //  글 내용
                                         @RequestParam int visibility,     //  발행상태 (0: 비공개 - 기본값, 1: 보호, 3: 발행)
                                         @RequestParam String category,    //  카테고리 아이디 (기본값: 0)
                                         @RequestParam String published,   //  발행시간 (TIMESTAMP 이며 미래의 시간을 넣을 경우 예약. 기본값: 현재시간)
                                         @RequestParam String slogan,      //  문자 주소 urlSlug
                                         @RequestParam String tag,         //  태그 (',' 로 구분)
                                         @RequestParam int acceptComment); //  댓글 허용 (0, 1 - 기본값)

    @PostMapping(value = "/post/attach", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/json")
    Response<PhotoFromTistory> fileUpload(@RequestParam String access_token,
                                          @RequestParam String output,
                                          @RequestParam String blogName,
                                          @RequestPart MultipartFile uploadedfile);

    @GetMapping("/category/list")
    Response<CategoryFromTistory> getCategory(@RequestParam String access_token,
                                              @RequestParam String output,
                                              @RequestParam String blogName);

}
