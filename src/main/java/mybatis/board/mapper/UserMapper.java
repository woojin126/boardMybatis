package mybatis.board.mapper;


import mybatis.board.domain.reply.ReplyVO;
import mybatis.board.domain.user.SearchCriteria;
import mybatis.board.domain.user.UserVO;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {


    List<UserVO> boardList(SearchCriteria scri) throws Exception;
    int listCount(SearchCriteria scri) throws Exception;
    void insertBoard(UserVO userVO);
    UserVO findByItem(Long id);
    int updateViewCnt(Long id);
    void deleteById(long id);
    void modifyBoard(UserVO userVO);
    void insertFile(Map<String, Object> map) throws Exception;
    List<Map<String,Object>> selectFileList(long id) throws Exception;
    Map<String,Object> selectFileInfo(Map<String,Object> map)throws Exception;
}
