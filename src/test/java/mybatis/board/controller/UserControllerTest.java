package mybatis.board.controller;

import lombok.extern.slf4j.Slf4j;
import mybatis.board.domain.user.SearchCriteria;
import mybatis.board.domain.user.UserVO;
import mybatis.board.mapper.UserMapper;
import mybatis.board.utils.FileUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootTest
class UserControllerTest {

    private final UserMapper userMapper;
    private final FileUtils fileUtils;

    @Autowired
    public UserControllerTest(UserMapper userMapper,FileUtils fileUtils){
        this.userMapper = userMapper;
        this.fileUtils = fileUtils;
    }


    @Test
    void 데이터삽입(){
        UserVO userVO = new UserVO();
        userVO.setContent("김좌진");
        userVO.setAuthor("김좌진");
        userVO.setTitle("김좌진");

        userMapper.insertBoard(userVO);

       // Assertions.assertThat(i).isEqualTo(1);
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

    @Test
    public void testInsertSelectKey() throws Exception {

        UserVO userVO = new UserVO();
        userVO.setTitle("하이");
        userVO.setAuthor("우진");
        userVO.setContent("우우우우우우");


        userMapper.insertBoard(userVO);
        log.debug("userID={}",userVO.getId()); //selectKey 장점은 미리 셋팅을해서 결과를 확인가능하지만, 성능은 최악


    }



}