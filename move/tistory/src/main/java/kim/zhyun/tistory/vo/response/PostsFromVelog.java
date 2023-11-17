package kim.zhyun.tistory.vo.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostsFromVelog {

    private String title;
    private String body;
    private String urlSlug;
    private boolean isPrivate;

    private String tags;
    private String series;

    private String releasedAt;

}
