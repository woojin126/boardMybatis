package mybatis.board.mapper;

import mybatis.board.domain.UserVO;
import org.apache.ibatis.annotations.Mapper;



import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {


    public List<UserVO> boardList();
    public void insertBoard(UserVO userVO);
    public UserVO findByItem(Long id);

}
