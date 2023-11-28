package kim.zhyun.tistory.data.vo;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Getter
@RefreshScope
@Component
public class TistoryClientConfigVo {

    @Value("${my.feign.retryer.period}")        private long period;
    @Value("${my.feign.retryer.maxPeriod}")     private long maxPeriod;
    @Value("${my.feign.retryer.maxAttempts}")   private int maxAttempts;

}
