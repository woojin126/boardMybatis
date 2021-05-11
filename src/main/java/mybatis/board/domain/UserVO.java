package mybatis.board.domain;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;


@Getter @Setter
public class UserVO {

    private Long id;
    private String author;
    private String title;
    private String content;
    private String regdate;


    @Override
    public String toString() {
        return "UserVO{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", regDate='" + regdate + '\'' +
                '}';
    }
}

