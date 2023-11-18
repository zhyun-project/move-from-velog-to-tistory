package kim.zhyun.velog.data.vo.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePhotoToTistory {

    private Long seq;
    private String keyword;
    private String imgOriginPath;
    private String imgLocalPath;
    private Long postSeq;

}
