package kim.zhyun.velog.client;

import kim.zhyun.velog.data.vo.request.GithubVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "githubClient", url = "https://api.github.com/markdown")
public interface GithubClient {

    @PostMapping
    String gitApi(@RequestHeader String authorization,
                  @RequestHeader String accept,
                  @RequestBody GithubVo githubVo);

}
