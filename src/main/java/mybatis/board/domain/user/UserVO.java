package mybatis.board.domain.user;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;



/**
 * @valid 유효성 검증을위해 @NotEmpty @NotBlank 설정 
 * 공백문자나 , NULL 오면 Errors에 들어감
 */

@Setter @Getter
public class UserVO {


    private Long id;
    @NotBlank(message = "작성자 입력하세요")
    private String author;
    @NotBlank(message = "제목 입력하세요")
    private String title;
    @NotBlank(message = "내용 입력해주세요")
    private String content;
    private String regdate;
    private int recnt;



    @Override
    public String toString() {
        return "UserVO{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", regdate='" + regdate + '\'' +
                ", recnt=" + recnt +
                '}';
    }



}

