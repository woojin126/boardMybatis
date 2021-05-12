package mybatis.board.service;

import mybatis.board.domain.Criteria;
import mybatis.board.domain.UserVO;
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
    public UserServiceImpl(UserMapper userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<UserVO> getBoardList(Criteria cri) throws Exception {
        return userDao.boardList(cri);
    }

    @Override
    public int listCount() throws Exception {
        return userDao.listCount();
    }


    @Override
    public void insertBoard(UserVO userVO) {

        userDao.insertBoard(userVO);
    }

    @Override
    public UserVO findById(Long id) {
        return userDao.findByItem(id);
    }

    @Override
    public int updateViewCnt(Long id) {
        return userDao.updateViewCnt(id);
    }


    /**
     *검증코드 제목, 작성자 ,내용 중  NULL 값이나 , "" 빈문자열 올시 error 메세지 반환
     */
    public Map<String, String> validateHandling(Errors errors) {
        Map<String,String> validatorResult = new HashMap<>();

        for(FieldError error : errors.getFieldErrors()){
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName,error.getDefaultMessage());
        }

        return validatorResult;
    }
}
