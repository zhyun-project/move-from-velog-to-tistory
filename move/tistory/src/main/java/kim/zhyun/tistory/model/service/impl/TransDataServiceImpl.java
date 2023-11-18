package kim.zhyun.tistory.model.service.impl;

import kim.zhyun.tistory.data.vo.PhotoVo;
import kim.zhyun.tistory.model.repository.PostRepository;
import kim.zhyun.tistory.model.service.TransDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Transactional
@Service
public class TransDataServiceImpl implements TransDataService {
    private final PostRepository postRepository;

    @Override
    public void transformContentImgKeywordToReplacer() {
        String prefix = "<img src=\"";
        String suffix = "\" alt=\"\" style=\"max-width: 100%;\">";

        postRepository.findAll().stream()
                .filter(post -> post.getReplacerContent() == null)
                .forEach(post -> {
                    String htmlContent = post.getHtmlContent();

                    if (post.getPhotos().size() > 0) {
                        Set<PhotoVo> photoSet = new HashSet<>();
                        post.getPhotos()
                            .forEach(photo -> {
                                if (photo.getReplacer() != null) {
                                    photoSet.add(PhotoVo.of(
                                            prefix + photo.getKeyword() + suffix,
                                            photo.getReplacer()));
                                }
                            });

                        for (PhotoVo info : photoSet) {
                            htmlContent = htmlContent
                                    .replace(info.getHtmlImgSource(), info.getTistoryReplacer());
                        }
                    }

                    post.setReplacerContent(htmlContent);
                });
    }

}
