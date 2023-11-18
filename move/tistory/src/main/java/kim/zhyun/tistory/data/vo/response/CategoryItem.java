package kim.zhyun.tistory.data.vo.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter @Setter
public class CategoryItem {

    private ArrayList<CategoryRealItem> categories = new ArrayList<>();

}
