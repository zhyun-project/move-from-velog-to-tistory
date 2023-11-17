package kim.zhyun.tistory.vo.response;

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
