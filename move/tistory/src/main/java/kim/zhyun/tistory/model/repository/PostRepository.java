package kim.zhyun.tistory.model.repository;

import kim.zhyun.tistory.model.entity.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * WHERE `uploadYn` = false  AND  `replacerContent` IS NULL
     * LIMIT 15
     */
    List<Post> findTop15ByUploadYnIsFalseAndReplacerContentIsNull(Sort sort);

    /**
     * WHERE `uploadYn` = false  AND  `replacerContent` IS NOT NULL
     * LIMIT 15
     */
    List<Post> findTop15ByUploadYnIsFalseAndReplacerContentIsNotNull(Sort sort);

}
