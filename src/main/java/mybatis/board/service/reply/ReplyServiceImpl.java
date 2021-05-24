package mybatis.board.service.reply;

import mybatis.board.domain.reply.ReplyVO;
import mybatis.board.mapper.ReplyMapper;
import mybatis.board.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService{

    private ReplyMapper replyMapper;

    @Autowired
    public ReplyServiceImpl(ReplyMapper replyMapper) {
        this.replyMapper = replyMapper;
    }

    @Override
    public List<ReplyVO> readReply(long id) throws Exception {
        return replyMapper.readReply(id);
    }

    @Override
    public void writeReply(ReplyVO vo) throws Exception {
        replyMapper.writeReply(vo);
    }
}
