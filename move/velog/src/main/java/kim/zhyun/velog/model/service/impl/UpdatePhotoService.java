package kim.zhyun.velog.model.service.impl;

import kim.zhyun.velog.model.repository.PhotoRepository;
import kim.zhyun.velog.model.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/*
 *  photo table에 post seq 추가 😭
 */

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class UpdatePhotoService {
    private final PostRepository postRepository;
    private final PhotoRepository photoRepository;

    @Value("${imgs.keywordPrefix}")
    private String prefix;
    private final String suffix = "-\">";

    public Map<Long, Set<String>> mapPhotoKeywordPostSeq() {
        Map<Long, Set<String>> photoPostSeqMap = new HashMap<>();
        postRepository.findAll()
                .forEach(post -> {
                    String htmlBody = post.getHtmlBody();
                    if (htmlBody != null && htmlBody.contains(prefix)) {
                        Set<String> keywords = new HashSet<>();
                        Arrays.stream(htmlBody
                                        .substring(htmlBody.indexOf(prefix))
                                        .split(prefix))
                                .filter(str -> str.length() > 0)
                                .forEach(keywordSuffix -> {
                                    keywords.add(
                                            prefix + keywordSuffix
                                                    .substring(0, keywordSuffix.indexOf(suffix) + 1)
                                    );
                                });

                        photoPostSeqMap.put(post.getSeq(), keywords);
                    }
                });
        return photoPostSeqMap;
    }

    public void updatePhoto(Map<Long, Set<String>> photoPostSeqMap) {
        photoPostSeqMap.forEach((postSeq, keywords) -> {
            int cnt = photoRepository.updateAllByPostSeqInKeyword(postSeq, keywords);
            log.info("updated --> {}", cnt);
        });
    }

}
