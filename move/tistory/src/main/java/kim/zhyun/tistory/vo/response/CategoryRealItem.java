package kim.zhyun.tistory.vo.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoryRealItem {

    private String id; // 카테고리 ID
    private String name; // 카테고리 이름
    private String label; // 부모 카테고리를 포함한 전체 이름 ('/'로 구분)

}
