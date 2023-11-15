package kim.zhyun.velog.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.GenerationType.IDENTITY;

@ToString
@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post {

    @Id @GeneratedValue(strategy = IDENTITY)
    private long seq;

    private String postId;
    private String urlSlug;
    private String title;
    private String originBody;
    private String changedBody;
    private boolean isPrivate;
    private String releasedAt;
    private String updatedAt;

    
    // 시리즈 1:1
    @OneToOne
    private Series series;

    // photo 1:N
    @OneToMany
    @Builder.Default
    private List<Photo> photos = new ArrayList<>();

    // tag N:N
    @ManyToMany(cascade = PERSIST)
    @Builder.Default
    private List<Tag> tags = new ArrayList<>();

}
