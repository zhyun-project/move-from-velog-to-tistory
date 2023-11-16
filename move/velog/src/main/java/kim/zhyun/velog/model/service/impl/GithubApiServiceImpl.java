package kim.zhyun.velog.model.service.impl;

import kim.zhyun.velog.client.GithubClient;
import kim.zhyun.velog.data.vo.request.GithubVo;
import kim.zhyun.velog.model.repository.PhotoRepository;
import kim.zhyun.velog.model.repository.PostRepository;
import kim.zhyun.velog.model.service.GithubApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class GithubApiServiceImpl implements GithubApiService {
    private final GithubClient githubClient;
    private final PostRepository postRepository;
    private final PhotoRepository photoRepository;

    @Value("${imgs.keywordFormat}") private String keyword;
    @Value("${imgs.keywordPrefix}") private String prefix;
    @Value("${github.apiKey}")      private String gitKey;

    @Transactional
    @Override
    public void convertMarkdownToHtml() {
        postRepository.findAll()
                        .forEach(post -> {
                            String body = post.getChangedBody() != null
                                    ? post.getChangedBody()
                                    : post.getOriginBody();

                            keyword = keyword.replace("%s-", "");
                            body = body.replace(keyword, "XChangeXChangeXChangeXChangeXChangeXChangeX"); // 한글 이슈로 변환변환변환을 XChangeXChangeXChange로 변경

                            String htmlBody = githubClient.gitApi(
                                    gitKey,
                                    "application/vnd.github+json",
                                    new GithubVo(body));
                            post.setHtmlBody(htmlBody);
                        });
        
        // markdown -> html 변환시 한글 깨짐 이슈로 photo 테이블의 keyword 함께 변경
        changePhotoKeywordName();
    }

    @Transactional
    public void changePhotoKeywordName() {
        String target = "변환변환변환변환";
        photoRepository
                .findAll()
                .forEach(photo -> {
                    String replaced = photo.getKeyword().replace(target, prefix);
                    photo.setKeyword(replaced);
                });
    }
}
