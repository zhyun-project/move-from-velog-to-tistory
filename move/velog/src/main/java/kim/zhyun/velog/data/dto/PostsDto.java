package kim.zhyun.velog.data.dto;

import kim.zhyun.velog.data.vo.response.ResponsePosts;
import kim.zhyun.velog.data.vo.response.ResponsePostsToTistory;
import kim.zhyun.velog.model.entity.Post;
import kim.zhyun.velog.model.entity.Tag;

import java.util.stream.Collectors;

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

    public static ResponsePostsToTistory from (Post post) {
        return ResponsePostsToTistory.builder()
                .title(post.getTitle())
                .body(post.getHtmlBody())
                .urlSlug(post.getUrlSlug())
                .isPrivate(post.isPrivate())

                .tags(post.getTags().stream()
                        .map(Tag::getTag)
                        .collect(Collectors.joining(",")))
                .series(post.getSeriesName())

                .releasedAt(post.getReleasedAt())
                .build();
    }

}
