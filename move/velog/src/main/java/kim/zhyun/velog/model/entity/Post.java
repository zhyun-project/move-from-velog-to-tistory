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

    @Column(columnDefinition = "LONGTEXT")
    private String originBody;
    @Column(columnDefinition = "LONGTEXT")
    private String changedBody;

    private boolean isPrivate;
    private String releasedAt;
    private String updatedAt;

    private String seriesName;

    // photo 1:N
    @OneToMany
    @Builder.Default
    private List<Photo> photos = new ArrayList<>();

    // tag N:N
    @ManyToMany(cascade = PERSIST)
    @Builder.Default
    private List<Tag> tags = new ArrayList<>();

}
