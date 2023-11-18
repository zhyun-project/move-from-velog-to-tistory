package kim.zhyun.velog.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.GenerationType.IDENTITY;

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
    @Column(columnDefinition = "LONGTEXT")
    private String htmlBody;

    private boolean isPrivate;
    private String releasedAt;
    private String updatedAt;

    private String seriesName;

    // post 1:N photo
    @OneToMany
    @JoinColumn(name = "post_seq")
    @Builder.Default
    private List<Photo> photos = new ArrayList<>();

    // tag N:N
    @ManyToMany(cascade = PERSIST)
    @Builder.Default
    private List<Tag> tags = new ArrayList<>();

}
