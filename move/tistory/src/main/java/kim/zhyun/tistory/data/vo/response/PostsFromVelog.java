package kim.zhyun.tistory.data.vo.response;

import kim.zhyun.tistory.model.entity.Photo;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostsFromVelog {

    private long seq;

    private String title;
    private String body;
    private String urlSlug;
    private boolean isPrivate;

    private String tags;
    private String series;

    private String releasedAt;
    private List<Photo> photos;

}
