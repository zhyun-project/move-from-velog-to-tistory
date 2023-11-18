package kim.zhyun.tistory.data.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BlogInfo {
    private String name;
    private String url;
    private String secondaryUrl;
    private String nickname;
    private String title;
    private String description;

    @JsonProperty("default")
    private String defaultYn;

    private String blogIconUrl;
    private String faviconUrl;
    private String profileThumbnailImageUrl;
    private String profileImageUrl;
    private String role;
    private String blogId;
    private BlogDetailInfo statistics;

}
