package kim.zhyun.tistory.model.repository;

import kim.zhyun.tistory.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
}
