package kim.zhyun.velog.utils;

import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;

@Component
public class FileUtils {

    // url로 이미지 가져오기
    public byte[] patchImage(String strUrl) throws IOException {
        URL url = new URL(strUrl);
        InputStream in = new BufferedInputStream(url.openStream());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int n = 0;

        while (-1 != (n = in.read(buf))) {
            out.write(buf, 0, n);
        }

        out.close();
        in.close();
        return out.toByteArray();
    }

    // 폴더 생성
    public void makeDirectory(String path, String name) {
        File fileDir  = new File(path, name);

        if(!fileDir.isDirectory()){
            fileDir.mkdirs();
        }
    }

    // 원하는 경로에 이미지 다운로드
    public void download(String path, String name, byte[] img) throws IOException {
        File fileData = new File(path, name);

        if (!fileData.exists()) {
            FileOutputStream fos = new FileOutputStream(path + name);
            fos.write(img);
            fos.close();
        }
    }

}
