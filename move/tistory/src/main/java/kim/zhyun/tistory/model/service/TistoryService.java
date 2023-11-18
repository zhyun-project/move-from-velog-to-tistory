package kim.zhyun.tistory.model.service;

import kim.zhyun.tistory.data.vo.CategoryVo;
import kim.zhyun.tistory.data.vo.Response;
import kim.zhyun.tistory.data.vo.response.BlogInfoFromTistory;
import kim.zhyun.tistory.data.vo.response.PhotoFromTistory;
import kim.zhyun.tistory.data.vo.response.PostFromTistory;

import java.util.Map;

public interface TistoryService {

    Response<BlogInfoFromTistory> blogInfo();
    Response<PostFromTistory> postUpload();
    void fileUpload();
    Map<String, CategoryVo> getCategory();

}
