package kim.zhyun.velog.data.dto;

import kim.zhyun.velog.model.entity.Post;
import kim.zhyun.velog.data.vo.response.ResponsePosts;

public class PostsDto {


    public static Post to (ResponsePosts res) {
        return Post.builder()
                .postId(res.getId())
                .title(res.getTitle())
                .urlSlug(res.getUrl_slug())
                .isPrivate(res.is_private())

                .tags(res.getTags().stream()
                        .map(TagDto::to)
                        .toList())

                .releasedAt(res.getReleased_at())
                .updatedAt(res.getUpdated_at())
                .build();
    }

}
