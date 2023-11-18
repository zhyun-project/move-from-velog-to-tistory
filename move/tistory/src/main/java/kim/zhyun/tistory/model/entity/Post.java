package kim.zhyun.tistory.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post {

    @Id
    private Long seq;

    private boolean uploadYn;

    private String slogan;
    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String htmlContent;
    @Column(columnDefinition = "LONGTEXT")
    private String replacerContent;

    private int visibility;
    private String categoryId;
    private String categoryName;
    private String blogName;
    private String published;
    private String tag;

    @OneToMany
    @JoinColumn(name = "post_seq")
    private List<Photo> photos = new ArrayList<>();

}
