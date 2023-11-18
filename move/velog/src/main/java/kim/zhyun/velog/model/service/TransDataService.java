package kim.zhyun.velog.model.service;

import java.util.Map;
import java.util.Set;

public interface TransDataService {

    /**
     * map 생성 { key : photoKeyword , value : postSeq }
     */
    Map<Long, Set<String>> mapPhotoKeywordPostSeq();

    /**
     * photo table에 keyword 별 post seq 저장 
     */
    void updatePhoto(Map<Long, Set<String>> photoPostSeqMap);

}
