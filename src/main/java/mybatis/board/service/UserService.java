package mybatis.board.service;

import mybatis.board.domain.Criteria;
import mybatis.board.domain.UserVO;

import java.util.List;
import java.util.Optional;

public interface UserService {


    public List<UserVO> getBoardList(Criteria cri) throws Exception;
    public int listCount() throws Exception;
    public void insertBoard(UserVO userVO);
    public UserVO findById(Long id);
    public int updateViewCnt(Long id);
}
