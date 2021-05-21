package mybatis.board.mapper;


import mybatis.board.domain.Criteria;
import mybatis.board.domain.SearchCriteria;
import mybatis.board.domain.UserVO;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

@Mapper
public interface UserMapper {


    public List<UserVO> boardList(SearchCriteria scri) throws Exception;
    public int listCount(SearchCriteria scri) throws Exception;
    public void insertBoard(UserVO userVO);
    public UserVO findByItem(Long id);
    public int updateViewCnt(Long id);
    public void deleteById(long id);
    public void modifyBoard(UserVO userVO);

}
