package kim.zhyun.tistory.model.repository;

import kim.zhyun.tistory.model.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
