package kim.zhyun.tistory.model.service;

public interface VelogService {

    /**
     * velog module에서 post 데이터를 받아와 저장
     * - 응답 데이터에 photo table 정보가 함께 들어있다.
     */
    void savePostDataFromVelog();

}
