package kim.zhyun.tistory.model.service.impl;

import kim.zhyun.tistory.client.VelogLocalClient;
import kim.zhyun.tistory.data.dto.PostDto;
import kim.zhyun.tistory.data.vo.CategoryVo;
import kim.zhyun.tistory.model.entity.Post;
import kim.zhyun.tistory.model.repository.CategoryRepository;
import kim.zhyun.tistory.model.repository.PhotoRepository;
import kim.zhyun.tistory.model.repository.PostRepository;
import kim.zhyun.tistory.model.service.VelogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class VelogServiceImpl implements VelogService {
    private final VelogLocalClient velogClient;
    private final PostRepository postRepository;
    private final PhotoRepository photoRepository;
    private final CategoryRepository categoryRepository;

    private final Map<String, CategoryVo> mapCategory = new HashMap<>();

    @Override
    public void savePostDataFromVelog() {
        // category map 생성
        categoryRepository
                .findAll()
                .forEach(category -> mapCategory.put(
                        category.getCategoryName().trim(),
                        CategoryVo.of(category.getBlogName(), category.getCategoryId())));

        // post db 저장
        velogClient.getPostsFromH2Velog()
                .forEach(velog -> {
                    Post from = PostDto.from(velog);
                    photoRepository.saveAll(from.getPhotos());

                    String categoryName = from.getCategoryName().trim();
                    from.setBlogName(mapCategory.get(categoryName).getBlogName());
                    from.setCategoryId(mapCategory.get(categoryName).getCategoryId());

                    postRepository.save(from);
                });
    }

}
