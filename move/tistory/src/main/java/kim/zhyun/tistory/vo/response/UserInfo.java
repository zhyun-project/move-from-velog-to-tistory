package kim.zhyun.tistory.vo.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserInfo {

    private String id;        // "blog_oauth_test@daum.net",
    private String userId;    // "12345",
    private BlogInfo blogs;

}
