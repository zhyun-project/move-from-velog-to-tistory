package kim.zhyun.velog.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Tag {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long seq;
    private String tag;

}
