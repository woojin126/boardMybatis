package mybatis.board.service;

import mybatis.board.domain.UserVO;

import java.util.List;
import java.util.Optional;

public interface UserService {


    public List<UserVO> getBoardList();
    public void insertBoard(UserVO userVO);
    public UserVO findById(Long id);
}
