package mybatis.board.mapper;

import mybatis.board.domain.Criteria;
import mybatis.board.domain.UserVO;
import org.apache.ibatis.annotations.Mapper;



import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {


    public List<UserVO> boardList(Criteria cri) throws Exception;
    public int listCount() throws Exception;
    public void insertBoard(UserVO userVO);
    public UserVO findByItem(Long id);
    public int updateViewCnt(Long id);

}
