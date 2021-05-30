package mybatis.board.service.user;


import mybatis.board.domain.reply.ReplyVO;
import mybatis.board.domain.user.SearchCriteria;
import mybatis.board.domain.user.UserVO;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UserService {


    public List<UserVO> getBoardList(SearchCriteria scri) throws Exception;
    public int listCount(SearchCriteria scri) throws Exception;
    public void insertBoard(UserVO userVO, MultipartHttpServletRequest mpRequest) throws Exception;
    public UserVO findById(Long id);
    public int updateViewCnt(Long id);
    public void deleteById(Long id);
    public void modifyBoard(UserVO userVO);
    public List<Map<String,Object>> selectFileList(long id)throws Exception;
    Map<String,Object> selectFileInfo(Map<String,Object> map)throws Exception;
    /**
     *검증코드 제목, 작성자 ,내용 중  NULL 값이나 , "" 빈문자열 올시 error 메세지 반환
     */
    public Map<String, String> validateHandling(Errors errors);

}
