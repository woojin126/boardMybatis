package mybatis.board.domain.board;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;



/**
 * @valid 유효성 검증을위해 @NotEmpty @NotBlank 설정 
 * 공백문자나 , NULL 오면 Errors에 들어감
 */

@Setter @Getter
public class BoardVO {


    private Long id;
    @NotBlank(message = "작성자 입력하세요")
    private String author;
    @NotBlank(message = "제목 입력하세요")
    private String title;
    private String image;
    @NotBlank(message = "내용 입력해주세요")
    private String content;
    private String regdate;
    private int recnt;

    public BoardVO(@NotBlank(message = "작성자 입력하세요") String author, @NotBlank(message = "제목 입력하세요") String title, @NotBlank(message = "내용 입력해주세요") String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }


    @Override
    public String toString() {
        return "BoardVO{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", content='" + content + '\'' +
                ", regdate='" + regdate + '\'' +
                ", recnt=" + recnt +
                '}';
    }
}

