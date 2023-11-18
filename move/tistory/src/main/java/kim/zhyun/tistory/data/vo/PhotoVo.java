package kim.zhyun.tistory.data.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PhotoVo {
    private String htmlImgSource;
    private String tistoryReplacer;

    public static PhotoVo of (String htmlImgSource, String tistoryReplacer) {
        return new PhotoVo(htmlImgSource, tistoryReplacer);
    }

}
