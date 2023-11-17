package kim.zhyun.tistory.vo.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RequestCategory {

    private String access_token;
    private String output = "json";
    private String blogName = "wlgus-gim";

    public static RequestCategory of (String access_token) {
        RequestCategory requestCategory = new RequestCategory();
        requestCategory.setAccess_token(access_token);
        return requestCategory;
    }

}
