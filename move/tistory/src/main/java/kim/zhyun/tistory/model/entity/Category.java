package kim.zhyun.tistory.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

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
