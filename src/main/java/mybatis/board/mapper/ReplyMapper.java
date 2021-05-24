package mybatis.board.mapper;

import mybatis.board.domain.reply.ReplyVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyMapper {
    public List<ReplyVO> readReply(long id) throws Exception;

    public void writeReply(ReplyVO vo) throws Exception;
}
