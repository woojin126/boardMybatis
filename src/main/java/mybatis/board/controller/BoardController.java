package mybatis.board.controller;
import lombok.extern.slf4j.Slf4j;
import mybatis.board.domain.board.BoardVO;
import mybatis.board.domain.reply.ReplyVO;
import mybatis.board.domain.board.PageMaker;
import mybatis.board.domain.board.SearchCriteria;
import mybatis.board.service.reply.ReplyService;
import mybatis.board.service.board.BoardService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
/*https://melonpeach.tistory.com/51?category=806570 파일업로드 */

@Slf4j
@Controller
public class BoardController {

    private final BoardService boardService;
    private final ReplyService replyService;


    @Autowired
    public BoardController(BoardService boardService, ReplyService replyService) {
        this.boardService = boardService;
        this.replyService = replyService;
    }




    @GetMapping("/list")
    public String list(Model model , @ModelAttribute("scri") SearchCriteria scri) throws Exception
    {
        List<BoardVO> boardList = boardService.getBoardList(scri);
        model.addAttribute("boardList",boardList);

        PageMaker pageMaker = new PageMaker();
        pageMaker.setSearchCriteria(scri);
        pageMaker.setTotalCount(boardService.listCount(scri));

        model.addAttribute("pageMaker",pageMaker);
        return "board/list";
    }

    @GetMapping("/post")
    public String post(){
        return "board/post";
    }

    @PostMapping("/post")
    public String getPost(@ModelAttribute @Valid BoardVO boardVO, Errors errors, Model model, String[] files, String[] fileNames, MultipartHttpServletRequest mpReqeust) throws Exception {

        log.debug("UserVoId={}", boardVO.getId());
        log.debug("UserVoAuthor={}", boardVO.getAuthor());

        if (valiationForm(boardVO, errors, model, "userVO")) return "board/post";

        boardService.insertBoard(boardVO,files,fileNames,mpReqeust);



        return "redirect:/list";
    }

    /**
     *게시글 새로고침시 조회수 무한증가 해결을 위해 cookie 사용
     */
    @GetMapping("/detailItem")
    public String editForm(@RequestParam Long id, @RequestParam(value = "valid_author", required = false) String valid_author
            , @RequestParam(value = "valid_content", required = false) String valid_content
            , Model model, HttpServletRequest request, HttpServletResponse response
    ) throws Exception {

        if(id == null){
            throw new NullPointerException("해당하는 값이없습니다");
        }
        BoardVO item = boardService.findById(id);
        List<ReplyVO> replyList = replyService.readReply(id);
        List<Map<String, Object>> fileList = boardService.selectFileList(id);
        Cookie[] cookies = request.getCookies();
        Cookie viewCookie = null;

        if (cookies != null && cookies.length > 0) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals("cookie" + id)) {
                    //System.out.println("기존에 있는 쿠키");
                    viewCookie = cookies[i];
                }
            }
        }

        if (item != null) {
            model.addAttribute("item", item);
            if (viewCookie == null) {
                log.debug("don't have cookie={}", (Object) null);

                Cookie newCookie = new Cookie("cookie" + id, "|" + id + "|");

                response.addCookie(newCookie);

                int result = boardService.updateViewCnt(id);

                if (result > 0) {
                    System.out.println("조회수 증가");
                } else {
                    System.out.println("조회 에러");
                }
            } else {
                System.out.println("이미 만들어져있는 쿠키가 있네요");
                String value = viewCookie.getValue();
                log.debug("CookieValue={}", value);
            }

            model.addAttribute("valid_content", valid_content);
            model.addAttribute("valid_author", valid_author);
            model.addAttribute("replyList", replyList);
            model.addAttribute("file", fileList);
            return "board/detailItem";
        } else {
            return "board/detailItemError";
        }

    }

    @GetMapping("/fileDown")
    public void fileDown(@RequestParam Map<String, Object> map, HttpServletResponse response) throws Exception {
        Map<String, Object> resultMap = boardService.selectFileInfo(map);
        for (String s : resultMap.keySet()) {
            System.out.println("files" + s);
        }
        String storeFileName = (String) resultMap.get("STORED_FILE_NAME");
        String originalFileName = (String) resultMap.get("ORG_FILE_NAME");
        System.out.println("storeFileName==========" + storeFileName);
        byte fileByte[] = FileUtils.readFileToByteArray(new File("C:\\mp\\file\\" + storeFileName));
        response.setContentType("application/octet-stream");
        response.setContentLength(fileByte.length);
        response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(originalFileName, "UTF-8") + "\";");
        response.getOutputStream().write(fileByte);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long itemId) {
        log.debug("deleteById={}", itemId);

        if(itemId == null){
            throw new NullPointerException("해당하는 값이 없습니다");
        }
        boardService.deleteById(itemId);

        return "redirect:/list";
    }

    @GetMapping("/modify")
    public String modify(@RequestParam Long id, Model model) throws Exception {
        log.debug("modifyById={}", id);


        if(id == null){
            throw new NullPointerException("해당하는 값이 없습니다");
        }
        BoardVO updateLine = boardService.findById(id);
        model.addAttribute("updateLine", updateLine);

        List<Map<String, Object>> fileList = boardService.selectFileList(id);
        model.addAttribute("file", fileList);

        return "board/modify";
    }

    @PostMapping("/modify")
    public String modifySet(@ModelAttribute @Valid BoardVO boardVO,
                            Errors errors,
                            @RequestParam(value="fileNoDel[]") String[] files,
                            @RequestParam(value="fileNameDel[]") String[] fileNames,
                            MultipartHttpServletRequest mpRequest,
                            Model model) throws Exception {

        if (valiationForm(boardVO, errors, model, "updateLine")) return "board/modify";
        log.debug("filesMan={}",files);
        log.debug("fileNamesMan={}",fileNames);
        boardService.modifyBoard(boardVO,files,fileNames,mpRequest);

        return "redirect:/list";

    }




    private boolean valiationForm(@ModelAttribute @Valid Object vo, Errors errors, Model model, String updateLine) {
        if (errors.hasErrors()) {
            model.addAttribute(updateLine, vo);
            Map<String, String> validatorResult = boardService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                log.debug("modifyFormErrorKey={}",key);
                model.addAttribute(key, validatorResult.get(key));
            }

            return true;
        }
        return false;
    }
}
