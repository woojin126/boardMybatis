package mybatis.board.mapper;

import lombok.extern.slf4j.Slf4j;
import mybatis.board.domain.Criteria;
import mybatis.board.domain.UserVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;


@Slf4j
@SpringBootTest
class UserMapperTest {


    private final mybatis.board.mapper.UserMapper UserMapper;

    @Autowired
    public UserMapperTest(UserMapper userMapper) {
        this.UserMapper = userMapper;
    }


    @Test
    public void testPaging() throws Exception {
        Criteria criteria = new Criteria();
        List<UserVO> list = UserMapper.boardList(criteria);
        list.forEach(e ->log.info(String.valueOf(e)));

    }

}