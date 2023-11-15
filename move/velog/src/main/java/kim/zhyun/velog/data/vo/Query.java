package kim.zhyun.velog.data.vo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public record Query (
    @Value("${velog.query.series}") String series,
    @Value("${velog.query.posts}") String posts,
    @Value("${velog.query.read_post}") String post
) { }
