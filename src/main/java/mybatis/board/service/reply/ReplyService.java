package mybatis.board.service.reply;

import mybatis.board.domain.reply.ReplyVO;

import java.util.List;

public interface ReplyService {

    public List<ReplyVO> readReply(long id) throws Exception;
    public void writeReply(ReplyVO vo) throws Exception;
}
