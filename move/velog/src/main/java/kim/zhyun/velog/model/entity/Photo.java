package kim.zhyun.velog.model.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@ToString
@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Photo {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long seq;

    private String keyword;
    private String imgOriginPath;
    private String imgLocalPath;
    private long postSeq;

}
