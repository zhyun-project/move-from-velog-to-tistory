package kim.zhyun.tistory.data.vo.request;

import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestPostWrite {

    private String access_token;

    private String blogName;                    //  Blog Name (필수)
    private String title;                       //  글 제목 (필수)
    private String content;                     //  글 내용
    private int visibility;                     //  발행상태 (0: 비공개 - 기본값, 1: 보호, 3: 발행)
    private String category;                    //  카테고리 아이디 (기본값: 0)
    private String published;                   //  발행시간 (TIMESTAMP 이며 미래의 시간을 넣을 경우 예약. 기본값: 현재시간)
    private String slogan;                      //  문자 주소 urlSlug
    private String tag;                         //  태그 (',' 로 구분)
    private int acceptComment1;                   //  댓글 허용 (0, 1 - 기본값)
//    private String password;                    //  보호글 비밀번호

}
