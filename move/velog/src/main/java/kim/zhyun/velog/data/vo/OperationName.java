package kim.zhyun.velog.data.vo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public record OperationName (
    @Value("${velog.operationName.series}") String series,
    @Value("${velog.operationName.posts}") String posts,
    @Value("${velog.operationName.read_post}") String post
) { }
