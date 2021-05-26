package mybatis.board.domain.reply;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class ReplyVO {

    private Long id;
    private Long rno;
    @NotBlank
    private String content;
    @NotBlank
    private String author;
    private String regdate;


    @Override
    public String toString() {
        return "ReplyVO{" +
                "id=" + id +
                ", rno=" + rno +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", regdate='" + regdate + '\'' +
                '}';
    }
}
