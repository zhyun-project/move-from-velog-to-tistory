package kim.zhyun.tistory.data.vo.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhotosFromVelog {

    private String keyword;
    private String imgOriginPath;
    private String imgLocalPath;
    private Long postSeq;

}
