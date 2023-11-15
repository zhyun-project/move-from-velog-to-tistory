package kim.zhyun.velog.data.vo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@ToString
@Getter @Builder
@JsonInclude(NON_NULL)
public class Request<T> {

    private String operationName;
    private T variables;
    private String query;

}
