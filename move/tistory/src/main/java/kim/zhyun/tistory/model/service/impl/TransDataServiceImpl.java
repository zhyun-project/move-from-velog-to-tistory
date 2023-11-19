package kim.zhyun.tistory.model.service.impl;

import feign.FeignException;
import kim.zhyun.tistory.client.TistoryClient;
import kim.zhyun.tistory.data.vo.PhotoVo;
import kim.zhyun.tistory.data.vo.Response;
import kim.zhyun.tistory.data.vo.TistoryConnectVo;
import kim.zhyun.tistory.data.vo.response.PhotoFromTistory;
import kim.zhyun.tistory.model.repository.PhotoRepository;
import kim.zhyun.tistory.model.repository.PostRepository;
import kim.zhyun.tistory.model.service.TransDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.data.domain.Sort.Order.asc;

@Slf4j
@RequiredArgsConstructor
@Transactional(noRollbackFor = { FeignException.class })
@Service
public class TransDataServiceImpl implements TransDataService {
    private final TistoryClient tistoryClient;

    private final PhotoRepository photoRepository;
    private final PostRepository postRepository;

    private final TistoryConnectVo tistoryConnect;

    @Override
    public void transformContentImgKeywordToReplacer() {
        String prefix = "<img src=\"";
        String suffix = "\" alt=\"\" style=\"max-width: 100%;\">";

        postRepository
                .findTop15ByUploadYnIsFalseAndReplacerContentIsNull(Sort.by(asc("published")))
                .forEach(post -> {
                    String htmlContent = post.getHtmlContent();
                    String accessToken = post.getBlogName().equals(tistoryConnect.getBlogNameDev())
                            ? tistoryConnect.getAccessTokenDev()
                            : tistoryConnect.getAccessTokenLife();

                    if (post.getPhotos().size() > 0) {
                        Set<PhotoVo> photoSet = new HashSet<>();
                        post.getPhotos()
                            .forEach(photo -> {
                                Response<PhotoFromTistory> response = null;
                                try {
                                    response = clientMappedFile(accessToken, post.getBlogName(), photo.getImgLocalPath());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }

                                String replacer = response.getTistory().getReplacer();
                                photo.setReplacer(replacer);

                                photoRepository.saveAndFlush(photo);

                                photoSet.add(PhotoVo.of(
                                        prefix + photo.getKeyword() + suffix,
                                        replacer));
                            });

                        for (PhotoVo info : photoSet) {
                            htmlContent = htmlContent
                                    .replace(info.getHtmlImgSource(), info.getTistoryReplacer());
                        }
                    }

                    post.setReplacerContent(htmlContent);
                });
    }

    private Response<PhotoFromTistory> clientMappedFile(String accessToken,
                                                        String blogName,
                                                        String pathname) throws IOException {
        File file = new File(pathname);
        FileItem fileItem = new DiskFileItem("file", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());

        try {
            InputStream input = new FileInputStream(file);
            OutputStream os = fileItem.getOutputStream();
            IOUtils.copy(input, os);
        } catch (IOException ex) {
            log.info(ex.getMessage());
        }

        MultipartFile mFile = new CommonsMultipartFile(fileItem);
        return tistoryClient.fileUpload(accessToken, tistoryConnect.getOutput(), blogName, mFile);
    }

}
