package kim.zhyun.velog.model.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Series {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long seq;
    private String name;

}
