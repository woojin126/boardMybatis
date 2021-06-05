package mybatis.board.mapper;


import mybatis.board.domain.board.BoardVO;
import mybatis.board.domain.board.SearchCriteria;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;
import java.util.Map;

@Mapper
public interface BoardMapper {


    List<BoardVO> boardList(SearchCriteria scri) throws Exception;
    int listCount(SearchCriteria scri) throws Exception;
    boolean insertBoard(BoardVO boardVO);
    BoardVO findByItem(Long id);
    int updateViewCnt(Long id);
    void deleteById(long id);
    void modifyBoard(BoardVO boardVO);
    void insertFile(Map<String, Object> map) throws Exception;
    List<Map<String,Object>> selectFileList(long id) throws Exception;
    Map<String,Object> selectFileInfo(Map<String,Object> map)throws Exception;
    void updateFile(Map<String,Object> map) throws Exception;
}
