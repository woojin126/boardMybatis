package mybatis.board.service.user;

import lombok.extern.slf4j.Slf4j;
import mybatis.board.domain.reply.ReplyVO;
import mybatis.board.domain.user.SearchCriteria;
import mybatis.board.domain.user.UserVO;
import mybatis.board.mapper.UserMapper;
import mybatis.board.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class UserServiceImpl implements UserService{
    private FileUtils fileUtils;
    private UserMapper userDao;

    @Autowired
    public UserServiceImpl(UserMapper userDao,FileUtils fileUtils ) {
        this.userDao = userDao;
        this.fileUtils=fileUtils;
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
    public void insertBoard(UserVO userVO, MultipartHttpServletRequest mpRequest) throws Exception {

        userDao.insertBoard(userVO);

        List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(userVO, mpRequest);
        int size = list.size();
        for(int i=0; i<size; i++){
            log.debug("fileUtilsList={}",list.get(i));
           userDao.insertFile(list.get(i));
        }

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

    @Override
    public List<Map<String, Object>> selectFileList(long id) throws Exception {
        return userDao.selectFileList(id);
    }

    @Override
    public Map<String, Object> selectFileInfo(Map<String, Object> map) throws Exception {
        return userDao.selectFileInfo(map);
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
