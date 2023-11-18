package kim.zhyun.tistory.data.vo.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter @Setter
public class UserInfo {

    private String id;        // "blog_oauth_test@daum.net",
    private String userId;    // "12345",
    private ArrayList<BlogInfo> blogs = new ArrayList<>();

}
