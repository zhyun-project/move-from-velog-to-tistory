package kim.zhyun.tistory.model.repository;

import kim.zhyun.tistory.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
