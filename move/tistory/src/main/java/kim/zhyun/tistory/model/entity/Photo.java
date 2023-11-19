package kim.zhyun.tistory.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@ToString
@Getter @Setter
@Entity
public class Photo {

    @Id
    private Long seq;

    private String keyword;
    private String imgOriginPath;
    private String imgLocalPath;
    private String replacer;

}
