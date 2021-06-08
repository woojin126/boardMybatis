package mybatis.board.controller;

import lombok.extern.slf4j.Slf4j;
import mybatis.board.domain.board.BoardVO;
import mybatis.board.domain.board.SearchCriteria;
import mybatis.board.mapper.BoardMapper;
import mybatis.board.utils.FileUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.io.FileInputStream;
import java.util.List;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class BoardControllerTest {
    /*https://eminentstar.github.io/2017/07/23/about-junit-and-test.html junit 단위테스트*/
    private final BoardMapper boardMapper;
    private final FileUtils fileUtils;
    private MockMvc mockMvc;




    @Autowired
    public BoardControllerTest(BoardMapper boardMapper, FileUtils fileUtils, MockMvc mockMvc) {
        this.boardMapper = boardMapper;
        this.fileUtils = fileUtils;
        this.mockMvc = mockMvc;

    }

    @Test
    public void 파일업로드테스트() throws Exception{

        MockMultipartFile file = new MockMultipartFile("file","test.txt","text/plain","hello file".getBytes());
        //인자값: 넘겨줄때 파일 이름, 원래 파일 이름, 파일 타입, 파일 데이터
        this.mockMvc.perform(multipart("/list").file(file))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void 파일업로드테스트2() throws Exception{
        String endpoint = "/post";
        FileInputStream fis = new FileInputStream("C:\\mp\\file\\스크린샷(102).png");
        MockMultipartFile multipartFile = new MockMultipartFile("file",fis);

        this.mockMvc.perform(MockMvcRequestBuilders.fileUpload(endpoint).file(multipartFile))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    void 데이터삽입() {
        BoardVO boardVO = new BoardVO("김우진", "날으는새", "휘리릭");

        boardMapper.insertBoard(boardVO);

        org.junit.jupiter.api.Assertions.assertTrue(boardMapper.insertBoard(boardVO));
    }

    /**
     * isSameAs  : 객체의 참조값이 같은지 비교
     * isEqualTo : 자바에서의 Equals와 동일
     */
    @Test
    void 데이터삭제() {
        BoardVO boardVO = new BoardVO("인간", "사람", "이기적인");

        boardMapper.deleteById(8);

        Assertions.assertThat(boardVO.getId()).isEqualTo(null);

    }

    @Test
    void 게시글업데이트() {

        //given
        BoardVO column = boardMapper.findByItem(7l);
        column.setContent("긍지");
        //when
        boardMapper.modifyBoard(column);
        //then
        Assertions.assertThat(column.getContent()).contains("긍지");


    }

    @Test
    void 페이징1페이지() throws Exception {

        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setPage(1);
        List<BoardVO> list = boardMapper.boardList(searchCriteria);

        for (BoardVO userList : list) {
            log.info("userAuthor={},userTitle={}", userList.getAuthor(), userList.getTitle());
        }
        Assertions.assertThat(list.get(0).getAuthor()).isEqualTo("김우진");


    }

    @Test
    void 검색기능작성자필터() throws Exception {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setSearchType("w");
        searchCriteria.setKeyword("우진");

        List<BoardVO> list2 = boardMapper.boardList(searchCriteria);

        Assertions.assertThat(list2.get(0).getAuthor()).isEqualTo("김우진");

    }

    @Test
    void 특정컬럼값() {
        long id = 5;

        BoardVO userInfo = boardMapper.findByItem(id);

        Assertions.assertThat(userInfo.getAuthor()).isEqualTo("김우진");

    }

    @Test
    public void testInsertSelectKey() throws Exception {

        BoardVO boardVO = new BoardVO("하이", "우진", "우우우우우");

        boardMapper.insertBoard(boardVO);

        log.debug("userID={}", boardVO.getId()); //selectKey 장점은 미리 셋팅을해서 결과를 확인가능하지만, 성능은 최악
    }

}