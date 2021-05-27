package mybatis.board.service.reply;

import mybatis.board.domain.reply.ReplyVO;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Map;

public interface ReplyService {

    public List<ReplyVO> readReply(long id) throws Exception;
    public void writeReply(ReplyVO vo) throws Exception;
    public void deleteReply(ReplyVO vo);
    public Map<String, String> validateHandling(Errors errors);
    public ReplyVO findById(long rno);
    void modifyReply(ReplyVO vo);
}
