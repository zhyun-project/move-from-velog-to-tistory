package kim.zhyun.velog.data.dto;


import kim.zhyun.velog.model.entity.Tag;

public class TagDto {

    public static Tag to(Long seq, String tag) {
        return new Tag(seq, tag);
    }

    public static Tag to(String tag) {
        return to(null, tag);
    }

}
