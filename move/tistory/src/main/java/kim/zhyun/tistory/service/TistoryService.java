package kim.zhyun.tistory.service;

import kim.zhyun.tistory.vo.CategoryVo;
import kim.zhyun.tistory.vo.Response;
import kim.zhyun.tistory.vo.response.BlogInfoFromTistory;
import kim.zhyun.tistory.vo.response.PhotoFromTistory;
import kim.zhyun.tistory.vo.response.PostFromTistory;

import java.util.Map;

public interface TistoryService {

    Response<BlogInfoFromTistory> blogInfo();
    Response<PostFromTistory> postUpload();
    Response<PhotoFromTistory> fileUpload();
    Map<String, CategoryVo> getCategory();

}
