package kim.zhyun.velog.data.vo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter @Setter
@Component
public class OperationName {

    @Value("${velog.operationName.series}") private String series;
    @Value("${velog.operationName.posts}")          private String posts;
    @Value("${velog.operationName.read_post}")       private String pos;

}
