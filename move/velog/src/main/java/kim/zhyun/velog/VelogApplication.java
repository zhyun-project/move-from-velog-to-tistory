package kim.zhyun.velog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class VelogApplication {

    public static void main(String[] args) {
        SpringApplication.run(VelogApplication.class, args);
    }

}
