package kim.zhyun.velog.model.repository;

import kim.zhyun.velog.model.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

}
