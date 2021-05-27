package mybatis.board.service.reply;
import mybatis.board.domain.reply.ReplyVO;
import mybatis.board.mapper.ReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public void deleteReply(ReplyVO vo) {
        replyMapper.deleteReply(vo);
    }

    @Override
    public ReplyVO findById(long rno) {
        return replyMapper.findById(rno);
    }

    @Override
    public void modifyReply(ReplyVO vo) {
        replyMapper.modifyReply(vo);
    }


    @Override
    public Map<String, String> validateHandling(Errors errors) {

        Map<String, String> validatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }

        return validatorResult;
    }


}
