package kim.zhyun.tistory.service;

import kim.zhyun.tistory.vo.response.BlogInfoFromTistory;
import kim.zhyun.tistory.vo.response.CategoryFromTistory;
import kim.zhyun.tistory.vo.response.PhotoFromTistory;
import kim.zhyun.tistory.vo.response.PostFromTistory;
import kim.zhyun.tistory.vo.Response;

public interface TistoryService {

    Response<BlogInfoFromTistory> blogInfo();
    Response<PostFromTistory> postUpload();
    Response<PhotoFromTistory> fileUpload();
    Response<CategoryFromTistory> getCategory();

}
