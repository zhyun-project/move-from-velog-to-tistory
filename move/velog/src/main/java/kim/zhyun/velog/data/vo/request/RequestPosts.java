package kim.zhyun.velog.data.vo.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class RequestPosts {

    private String username;
    private String cursor;
    private int limit;

}
