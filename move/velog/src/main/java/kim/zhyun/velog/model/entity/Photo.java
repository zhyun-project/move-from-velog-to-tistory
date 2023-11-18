package kim.zhyun.velog.model.entity;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;


@ToString
@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Photo {

    @Id @GeneratedValue(strategy = IDENTITY)
    private long seq;

    private String keyword;
    private String imgOriginPath;
    private String imgLocalPath;

    @Column(name = "post_seq")
    private long postSeq;

}
