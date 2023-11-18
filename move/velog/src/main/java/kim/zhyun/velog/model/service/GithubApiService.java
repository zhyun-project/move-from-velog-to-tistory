package kim.zhyun.velog.model.service;


public interface GithubApiService {

    /**
     * `github api`를 이용하여 
     * H2 DB에 저장 된 markdown 형식의 본문을 읽어서 
     * html 형식으로 변환 후 H2 DB 저장
     */
    void convertMarkdownToHtml();

}
