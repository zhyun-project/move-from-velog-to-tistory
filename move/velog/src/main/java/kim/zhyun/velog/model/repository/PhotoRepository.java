package kim.zhyun.velog.model.repository;

import kim.zhyun.velog.model.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Photo                " +
            "  SET postSeq = :postSeq  " +
            "WHERE keyword in :keywords ")
    int updateAllByPostSeqInKeyword(long postSeq, Set<String> keywords);
}
