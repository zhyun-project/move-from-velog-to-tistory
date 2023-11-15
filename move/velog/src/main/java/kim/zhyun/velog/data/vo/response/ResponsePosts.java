package kim.zhyun.velog.data.vo.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter @Setter
public class ResponsePosts {

    private String id;
    private String title;
    private String url_slug;
    private boolean is_private;

    private List<String> tags;

    private String released_at;
    private String updated_at;

}
