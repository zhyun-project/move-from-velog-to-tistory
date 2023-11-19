package kim.zhyun.tistory.model.service;

import kim.zhyun.tistory.data.vo.CategoryVo;
import kim.zhyun.tistory.data.vo.Response;
import kim.zhyun.tistory.data.vo.response.BlogInfoFromTistory;

import java.util.Map;

public interface TistoryService {

    /**
     * tistory 계정에서 관리중인 blog 정보 조회
     */
    Response<BlogInfoFromTistory> blogInfo();

    /**
     * tistory로 post upload
     * - replacer content 값이 있고, 업로드 하지 않았던 게시글 최대 15개 업로드
     */
    void postUpload();

    /**
     * tistory에서 관리하는 블로그의 카테고리 정보 조회
     * - 블로그를 2개 관리할 계획으로,
     *   2개의 블로그에서 category 정보를 받아와 h2 db에 저장
     */
    Map<String, CategoryVo> getCategory();

}
