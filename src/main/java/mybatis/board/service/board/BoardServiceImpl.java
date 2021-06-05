package mybatis.board.service.board;

import lombok.extern.slf4j.Slf4j;
import mybatis.board.domain.board.BoardVO;
import mybatis.board.domain.board.SearchCriteria;
import mybatis.board.mapper.BoardMapper;
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
public class BoardServiceImpl implements BoardService {
    private FileUtils fileUtils;
    private BoardMapper boardMapper;

    @Autowired
    public BoardServiceImpl(BoardMapper boardMapper, FileUtils fileUtils ) {
        this.boardMapper = boardMapper;
        this.fileUtils=fileUtils;
    }

   @Override
    public List<BoardVO> getBoardList(SearchCriteria scri) throws Exception {

        return boardMapper.boardList(scri);
    }

    @Override
    public int listCount(SearchCriteria scri) throws Exception {
        return boardMapper.listCount(scri);
    }

    @Override
    public void insertBoard(BoardVO boardVO, String[] files, String[] fileNames, MultipartHttpServletRequest mpRequest) throws Exception {

        boardMapper.insertBoard(boardVO);

        List<Map<String, Object>> list = fileUtils.parseInsertFileInfo(boardVO, files, fileNames, mpRequest);
        int size = list.size();
        for (int i = 0; i < size; i++) {
            log.debug("fileUtilsList={}", list.get(i));
            boardMapper.insertFile(list.get(i));
        }

    }



    @Override
    public BoardVO findById(Long id) {
        return boardMapper.findByItem(id);
    }

    @Override
    public int updateViewCnt(Long id) {
        return boardMapper.updateViewCnt(id);
    }

    @Override
    public void deleteById(Long id) {
        boardMapper.deleteById(id);
    }

    @Override
    public void modifyBoard(BoardVO boardVO, String[] files, String[] fileNames, MultipartHttpServletRequest mpRequest) throws Exception {
        boardMapper.modifyBoard(boardVO);

        List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(boardVO,files,fileNames,mpRequest);
        Map<String,Object> tempMap = null;
        int size = list.size();
        for(int i=0; i<size;i++)
        {
            tempMap = list.get(i);
            log.debug("list.get={}",list.get(i));
            if(tempMap.get("IS_NEW").equals("Y"))
            {
                boardMapper.insertFile(tempMap);
            }
            else
            {
                boardMapper.updateFile(tempMap);
            }

        }



    }

    @Override
    public List<Map<String, Object>> selectFileList(long id) throws Exception {
        return boardMapper.selectFileList(id);
    }

    @Override
    public Map<String, Object> selectFileInfo(Map<String, Object> map) throws Exception {
        return boardMapper.selectFileInfo(map);
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
