package mybatis.board.service.user;

import mybatis.board.domain.reply.ReplyVO;
import mybatis.board.domain.user.SearchCriteria;
import mybatis.board.domain.user.UserVO;
import mybatis.board.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class UserServiceImpl implements UserService{

    private UserMapper userDao;

    @Autowired
    public UserServiceImpl(UserMapper userDao ) {
        this.userDao = userDao;
    }

   @Override
    public List<UserVO> getBoardList(SearchCriteria scri) throws Exception {

        return userDao.boardList(scri);
    }

    @Override
    public int listCount(SearchCriteria scri) throws Exception {
        return userDao.listCount(scri);
    }

    @Override
    public int insertBoard(UserVO userVO) {

        return userDao.insertBoard(userVO);
    }

    @Override
    public UserVO findById(Long id) {
        return userDao.findByItem(id);
    }

    @Override
    public int updateViewCnt(Long id) {
        return userDao.updateViewCnt(id);
    }

    @Override
    public void deleteById(Long id) {
        userDao.deleteById(id);
    }

    @Override
    public void modifyBoard(UserVO userVO) {

        userDao.modifyBoard(userVO);
    }

    /**
     *검증코드 제목, 작성자 ,내용 중  NULL 값이나 , "" 빈문자열 올시 error 메세지 반환
     */
    @Override
    public Map<String, String> validateHandling(Errors errors) {

        Map<String, String> validatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
            System.out.println(validKeyName);
        }

        return validatorResult;
    }


}
