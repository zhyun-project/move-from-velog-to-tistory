package kim.zhyun.velog.data.vo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter @Setter
@Component
public class Query {
    @Value("${velog.query.series}") private String series;
    @Value("${velog.query.posts}") private String posts;
    @Value("${velog.query.read_post}") private String pos;
}
