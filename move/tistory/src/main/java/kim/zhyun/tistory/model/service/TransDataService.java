package kim.zhyun.tistory.model.service;

public interface TransDataService {

    /**
     * tistory로 첨부파일 upload 후
     * `post table`의 `htmlContent`필드에서 `keyword`를 `replacer`로 교환하는 작업
     */
    void transformContentImgKeywordToReplacer();

}
