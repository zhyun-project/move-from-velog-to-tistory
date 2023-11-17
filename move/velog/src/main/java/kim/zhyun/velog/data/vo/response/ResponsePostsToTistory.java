package kim.zhyun.velog.data.vo.response;

import kim.zhyun.velog.data.dto.PhotoDto;
import lombok.*;

import java.util.List;

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

    private List<ResponsePhotoToTistory> photos;

}
