package kim.zhyun.velog.model.repository;

import kim.zhyun.velog.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
