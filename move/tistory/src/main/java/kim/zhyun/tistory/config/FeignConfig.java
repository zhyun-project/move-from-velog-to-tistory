package kim.zhyun.tistory.config;

import feign.Logger;
import feign.Retryer;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import kim.zhyun.tistory.data.vo.TistoryClientConfigVo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class FeignConfig {
    private final TistoryClientConfigVo vo;

    @Bean
    Retryer.Default retryer() {
        return new Retryer.Default(vo.getPeriod(), vo.getMaxPeriod(), vo.getMaxAttempts());
    }

    @Bean
    public Encoder multipartFormEncoder() {
        return new SpringFormEncoder(new SpringEncoder(() -> new HttpMessageConverters(new RestTemplate().getMessageConverters())));
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

}
