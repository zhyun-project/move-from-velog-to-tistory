package kim.zhyun.tistory.data.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryVo {
    private String blogName;
    private String categoryId;

    public static CategoryVo of (String blogName, String id) {
        return new CategoryVo(blogName, id);
    }

}
