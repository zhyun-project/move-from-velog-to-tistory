package kim.zhyun.velog.model.service;

import kim.zhyun.velog.data.vo.response.ResponsePhotoToTistory;
import kim.zhyun.velog.data.vo.response.ResponsePostsToTistory;

import java.util.List;

public interface VelogService {
    
    /**
     * velog 서버로부터 post 목록 정보 받아와서 H2 DB 저장
     * - post 상세정보를 조회하기 위한 `urlSlug`가 들어있다.
     */
    int saveAllPosts();

    /**
     * velog 서버로부터 post 상세 정보 받아와서  H2 DB 저장
     * - saveAllPosts() 를 통해 `urlSlug`를 알아야 한다.
     */
    int savePost();

    /**
     * tisotry module에서 필요한 게시글 정보 조회
     * - H2 DB에서 post 게시글 전체 조회 후 반환
     */
    List<ResponsePostsToTistory> findAllPost();

    /**
     * tisotry module에서 필요한 사진 정보 조회
     * - H2 DB에서 photo 게시글 전체 조회 후 반환
     */
    List<ResponsePhotoToTistory> findAllPhoto();

}
