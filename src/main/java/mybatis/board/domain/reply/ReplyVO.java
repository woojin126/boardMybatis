package mybatis.board.domain.reply;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReplyVO {

    private int id;
    private int rno;
    private String content;
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
