package kim.zhyun.tistory.data.dto;

import kim.zhyun.tistory.data.vo.request.RequestPostWrite;
import kim.zhyun.tistory.data.vo.response.PostsFromVelog;
import kim.zhyun.tistory.model.entity.Post;

public class PostDto {

    public static Post from (PostsFromVelog velog) {
        return Post.builder()
                .seq(velog.getSeq())

                .title(velog.getTitle())
                .htmlContent(velog.getBody())
                .slogan(velog.getUrlSlug())
                .visibility(velog.isPrivate() ? 0 : 3)

                .tag(velog.getTags())
                .categoryName(velog.getSeries())
                .photos(velog.getPhotos())

                .published(velog.getReleasedAt())
                .build();
    }

    public static RequestPostWrite from (Post post, String accessToken) {
        return RequestPostWrite.builder()
                .access_token(accessToken)

                .blogName(post.getBlogName())
                .title(post.getTitle())
                .content(post.getReplacerContent())
                .category(post.getCategoryName())
                .slogan(post.getSlogan())
                .tag(post.getTag()).build();
    }
}
