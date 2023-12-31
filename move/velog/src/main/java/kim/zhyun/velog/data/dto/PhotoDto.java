package kim.zhyun.velog.data.dto;

import kim.zhyun.velog.data.vo.response.ResponsePhotoToTistory;
import kim.zhyun.velog.model.entity.Photo;

public class PhotoDto {

    public static ResponsePhotoToTistory from (Photo photo) {
        return ResponsePhotoToTistory.builder()
                .seq(photo.getSeq())
                .keyword(photo.getKeyword())
                .imgLocalPath(photo.getImgLocalPath())
                .imgOriginPath(photo.getImgOriginPath())
                .postSeq(photo.getPostSeq())
                .build();
    }
}
