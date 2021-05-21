package mybatis.board.service;


import mybatis.board.domain.Criteria;
import mybatis.board.domain.SearchCriteria;
import mybatis.board.domain.UserVO;



import java.util.List;

public interface UserService {


    public List<UserVO> getBoardList(SearchCriteria scri) throws Exception;
    public int listCount(SearchCriteria scri) throws Exception;
    public void insertBoard(UserVO userVO);
    public UserVO findById(Long id);
    public int updateViewCnt(Long id);
    public void deleteById(Long id);
    public void modifyBoard(UserVO userVO);
}
