package mybatis.board.mapper;

import mybatis.board.domain.reply.ReplyVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyMapper {
    List<ReplyVO> readReply(long id) throws Exception;

    boolean writeReply(ReplyVO vo) throws Exception;

    void deleteReply(ReplyVO vo);

    ReplyVO findById(long rno);

    int modifyReply(ReplyVO vo);
}
