package kim.zhyun.tistory.vo.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RequestBlogInfo {

    private String access_token;
    private String output = "json";

    public static RequestBlogInfo of (String access_token) {
        RequestBlogInfo requestCategory = new RequestBlogInfo();
        requestCategory.setAccess_token(access_token);
        return requestCategory;
    }

}
