package kim.zhyun.tistory.data.vo.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PhotoFromTistory {
    private String status;      // "200",
    private String url;         // "http://cfile6.uf.tistory.com/image/1328CE504DB79F5932B13F",
    private String replacer;    // %5b%23%23_1N%7ccfile6.uf%401328CE504DB79F5932B13F%7cwidth%3d\"500\"+height%3d\"300\"%7c_%23%23%5d"
}