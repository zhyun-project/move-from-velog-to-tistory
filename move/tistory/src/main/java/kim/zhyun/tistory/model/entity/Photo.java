package kim.zhyun.tistory.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Photo {

    @Id
    private Long seq;

    private String keyword;
    private String imgOriginPath;
    private String imgLocalPath;
    private String replacer;

}
