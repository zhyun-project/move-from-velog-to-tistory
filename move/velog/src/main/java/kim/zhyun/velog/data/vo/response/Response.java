package kim.zhyun.velog.data.vo.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter @Setter
public class Response<T> {

    private Data<T> data;

    @ToString
    @Getter @Setter
    public static class Data<T> {
        private List<T> posts;
    }

}
