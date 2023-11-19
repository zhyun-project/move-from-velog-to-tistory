package kim.zhyun.tistory.model.service;

public interface TransDataService {

    /**
     * tistory로 post ${tistory.postUploadCnt}개에 해당하는 첨부파일 업로드 후
     * `post table`의 `htmlContent`필드에서 `keyword`를 `replacer`로 교환하는 작업
     */
    void transformContentImgKeywordToReplacer();

}
