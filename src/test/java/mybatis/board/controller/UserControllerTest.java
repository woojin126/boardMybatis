package mybatis.board.controller;

import lombok.extern.slf4j.Slf4j;
import mybatis.board.domain.user.SearchCriteria;
import mybatis.board.domain.user.UserVO;
import mybatis.board.mapper.UserMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class UserControllerTest {

    private final UserMapper userMapper;

    @Autowired
    public UserControllerTest(UserMapper userMapper){
        this.userMapper = userMapper;
    }


    @Test
    void 데이터삽입(){
        UserVO userVO = new UserVO();
        userVO.setContent("김좌진");
        userVO.setAuthor("김좌진");
        userVO.setTitle("김좌진");

        int i = userMapper.insertBoard(userVO);

        Assertions.assertThat(i).isEqualTo(1);
    }
    @Test
    void deleteTest(){
        userMapper.deleteById(236);
        UserVO userVO = new UserVO();

        Assertions.assertThat(userVO.getId()).isSameAs(null);
    }

    @Test
    void update(){

        //given
        UserVO userVO = new UserVO();
        UserVO testItem = userMapper.findByItem(233L);


        //when
        testItem.setTitle("수정당했다");
        userMapper.modifyBoard(testItem);

        //then

        Assertions.assertThat(testItem.getTitle()).isEqualTo("수정당했다");

    }

    @Test
    void 페이징3페이지() throws Exception {

        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setPage(3);
        List<UserVO> list = userMapper.boardList(searchCriteria);

        for(UserVO userList : list){
            log.info("userAuthor={},userTitle={}",userList.getAuthor(),userList.getTitle());
        }
    }

    @Test
    void 검색기능() throws Exception {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setSearchType("t");
        searchCriteria.setKeyword("우진");

        List<UserVO> list2 = userMapper.boardList(searchCriteria);

        list2.forEach(System.out::println);

    }

    @Test
    void 특정컬럼값(){
        long id =212;

        UserVO userInfo = userMapper.findByItem(id);

        Assertions.assertThat(userInfo.getAuthor()).isEqualTo("하이");

    }



}