package mybatis.board.service.board;



import mybatis.board.domain.board.BoardVO;
import mybatis.board.domain.board.SearchCriteria;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.List;
import java.util.Map;

public interface BoardService {


    List<BoardVO> getBoardList(SearchCriteria scri) throws Exception;

    int listCount(SearchCriteria scri) throws Exception;

    void insertBoard(BoardVO boardVO, String[] files, String[] fileNames, MultipartHttpServletRequest mpRequest) throws Exception;

    BoardVO findById(Long id);

    int updateViewCnt(Long id);

    void deleteById(Long id);

    void modifyBoard(BoardVO boardVO, String[] files, String[] fileNames, MultipartHttpServletRequest mpRequest) throws Exception;

    List<Map<String, Object>> selectFileList(long id) throws Exception;

    Map<String, Object> selectFileInfo(Map<String, Object> map) throws Exception;

    /**
     * 검증코드 제목, 작성자 ,내용 중  NULL 값이나 , "" 빈문자열 올시 error 메세지 반환
     */
    Map<String, String> validateHandling(Errors errors);

}
