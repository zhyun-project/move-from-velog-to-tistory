package kim.zhyun.velog.data.vo.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePostsToTistory {

    private String title;
    private String body;
    private String urlSlug;
    private boolean isPrivate;

    private String tags;
    private String series;

    private String releasedAt;

}
