package kim.zhyun.tistory.vo.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RequestFileupload {

    private String access_token;
    
    private String blogName = "wlgus-gim";      //  Blog Name (필수)

    public static RequestFileupload of (String access_token) {
        RequestFileupload requestFileupload = new RequestFileupload();
        requestFileupload.setAccess_token(access_token);
        return requestFileupload;
    }

}
