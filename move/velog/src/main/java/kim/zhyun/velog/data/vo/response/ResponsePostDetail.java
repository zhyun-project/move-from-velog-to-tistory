package kim.zhyun.velog.data.vo.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResponsePostDetail {

    private String id;
    private String body;

    private SeriesVo series;

}
