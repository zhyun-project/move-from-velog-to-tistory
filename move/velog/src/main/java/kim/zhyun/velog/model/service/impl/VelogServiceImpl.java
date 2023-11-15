package kim.zhyun.velog.model.service.impl;

import kim.zhyun.velog.client.VelogClient;
import kim.zhyun.velog.data.dto.PostsDto;
import kim.zhyun.velog.data.vo.OperationName;
import kim.zhyun.velog.data.vo.Query;
import kim.zhyun.velog.model.entity.Post;
import kim.zhyun.velog.model.repository.PostRepository;
import kim.zhyun.velog.model.service.VelogService;
import kim.zhyun.velog.data.vo.request.Request;
import kim.zhyun.velog.data.vo.request.RequestPosts;
import kim.zhyun.velog.data.vo.response.Response;
import kim.zhyun.velog.data.vo.response.ResponsePosts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class VelogServiceImpl implements VelogService {
    private final VelogClient client;
    private final OperationName operationName;
    private final Query query;

    private final PostRepository postRepository;

    private final String USERNAME = "zhyun";

    @Override
    public List<Post> saveAllPosts() {
        String cursor = null;

        while(true) {
            Request<RequestPosts> request = getRequestPosts(cursor);
            Response<ResponsePosts> response = client.getPosts(request);

            if (response.getData().getPosts().size() == 0)
                break;

            List<ResponsePosts> responsePosts = response.getData().getPosts();

            postRepository.saveAll(responsePosts.stream()
                    .map(PostsDto::to)
                    .toList());

            cursor = responsePosts.get(responsePosts.size() - 1).getId();
        }

        return postRepository.findAll();
    }

    private Request<RequestPosts> getRequestPosts(String cursor) {

        return Request.<RequestPosts>builder()
                .operationName(operationName.posts())
                .variables(RequestPosts.builder()
                        .username(USERNAME)
                        .cursor(cursor)
                        .limit(100).build())
                .query(query.posts()).build();
    }

}
