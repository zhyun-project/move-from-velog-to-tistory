package kim.zhyun.velog.data.vo.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePostsToTistory {

    private Long seq;

    private String title;
    private String body;
    private String urlSlug;
    private boolean isPrivate;

    private String tags;
    private String series;

    private String releasedAt;

    private List<ResponsePhotoToTistory> photos;

}
