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

    public ReplyVO(Long id, Long rno, @NotBlank String content, @NotBlank String author) {
        this.id = id;
        this.rno=rno;
        this.content = content;
        this.author = author;
    }

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
