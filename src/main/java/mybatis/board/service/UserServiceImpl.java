package mybatis.board.service;

import mybatis.board.domain.UserVO;
import mybatis.board.mapper.UserMapper;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private UserMapper userDao;

    @Autowired
    public UserServiceImpl(UserMapper userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<UserVO> getBoardList() {
        return userDao.boardList();
    }

    @Override
    public void insertBoard(UserVO userVO) {
        userDao.insertBoard(userVO);
    }



 /*   @Override
    public void insertBoard(UserVO userVO) {
        userDao.insertBoard(userVO);
    }*/

    @Override
    public UserVO findById(Long id) {
        return userDao.findByItem(id);
    }
}
