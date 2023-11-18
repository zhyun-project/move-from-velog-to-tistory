package kim.zhyun.tistory.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category {

    @Id
    private String categoryId;
    private String categoryName;
    private String blogName;

}
