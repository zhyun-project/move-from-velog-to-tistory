package kim.zhyun.tistory.model.repository;

import kim.zhyun.tistory.model.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * WHERE `uploadYn` = false  AND  `replacerContent` IS NULL
     */
    List<Post> findAllByUploadYnIsFalseAndReplacerContentIsNull(Pageable pageable);

    /**
     * WHERE `uploadYn` = false  AND  `replacerContent` IS NOT NULL
     */
    List<Post> findAllByUploadYnIsFalseAndReplacerContentIsNotNull(Pageable pageable);

}
