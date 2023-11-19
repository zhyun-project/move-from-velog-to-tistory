package kim.zhyun.tistory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableFeignClients
@SpringBootApplication
public class TistoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(TistoryApplication.class, args);
    }

}
