package kim.zhyun.tistory.data.vo;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Getter
@RefreshScope
@Component
public class TistoryConnectVo {

    @Value("${tistory.dev.accessToken}")    private String accessTokenDev;
    @Value("${tistory.dev.blogName}")       private String blogNameDev;

    @Value("${tistory.life.accessToken}")   private String accessTokenLife;
    @Value("${tistory.life.blogName}")      private String blogNameLife;

    @Value("${tistory.output}")             private String output;

}
