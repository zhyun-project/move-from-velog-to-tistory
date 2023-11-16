package kim.zhyun.velog.model.service;

import kim.zhyun.velog.data.vo.response.ResponsePostsToTistory;

import java.util.List;

public interface VelogService {

    int saveAllPosts();

    int savePost();

    List<ResponsePostsToTistory> findAllPost();

}
