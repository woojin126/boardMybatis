package mybatis.board.controller;
import lombok.extern.slf4j.Slf4j;
import mybatis.board.domain.reply.ReplyVO;
import mybatis.board.domain.user.PageMaker;
import mybatis.board.domain.user.SearchCriteria;
import mybatis.board.domain.user.UserVO;
import mybatis.board.service.reply.ReplyService;
import mybatis.board.service.user.UserService;
import mybatis.board.service.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;


@Slf4j
@Controller
public class UserController {

    private final UserService userService;
    private final ReplyService replyService;


    @Autowired
    public UserController(UserService userService,ReplyService replyService) {
        this.userService = userService;
        this.replyService = replyService;
    }




    @GetMapping("/list")
    public String list(Model model , @ModelAttribute("scri") SearchCriteria scri) throws Exception
    {
        List<UserVO> boardList = userService.getBoardList(scri);
        model.addAttribute("boardList",boardList);

        PageMaker pageMaker = new PageMaker();
        pageMaker.setSearchCriteria(scri);
        pageMaker.setTotalCount(userService.listCount(scri));

        model.addAttribute("pageMaker",pageMaker);
        return "board/list";
    }

    @GetMapping("/post")
    public String post(){
        return "board/post";
    }

    @PostMapping("/post")
    public String getPost(@ModelAttribute @Valid UserVO userVO, Errors errors, Model model){

        if (valiationForm(userVO, errors, model, "userVO")) return "board/post";

        userService.insertBoard(userVO);
        return "redirect:/list";
    }

    /**
     *게시글 새로고침시 조회수 무한증가 해결을 위해 cookie 사용
     */
    @GetMapping("/detailItem/{id}")
    public String editForm(@PathVariable Long id, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("id:"+id);
        UserVO item = userService.findById(id);
        List<ReplyVO> replyList = replyService.readReply(id);
        for(ReplyVO list : replyList){
            System.out.println("작성자"+list.getAuthor());
            System.out.println("내용"+list.getContent());
        }
        Cookie[] cookies = request.getCookies();
        Cookie viewCookie = null;

        if(cookies !=null && cookies.length>0)
        {
            for(int i=0;i<cookies.length;i++)
            {
                if(cookies[i].getName().equals("cookie"+id))
                {
                    //System.out.println("기존에 있는 쿠키");
                    viewCookie = cookies[i];
                }
            }
        }

        if(item != null)
        {
                model.addAttribute("item",item);
                log.info("item={}",item.getId());
                if(viewCookie == null)
                {
                    //System.out.println("쿠키가 없는 친구 었네?");

                    Cookie newCookie = new Cookie("cookie"+id,"|"+id+"|");

                    response.addCookie(newCookie);

                    int result = userService.updateViewCnt(id);

                    if(result > 0)
                    {
                        System.out.println("조회수 증가");
                    }
                    else
                    {
                        System.out.println("조회 에러");
                    }
                }
                else
                {
                    System.out.println("이미 만들어져있는 쿠키가 있네요");
                    String value = viewCookie.getValue();
                    log.info("CookieValue={}",value);
                }
               model.addAttribute("replyList",replyList);
                return "board/detailItem";
        } else
        {
            return "board/detailItemError";
        }

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        userService.deleteById(id);

        return "redirect:/list";
    }

    @GetMapping("/modify/{id}")
    public String modify(@PathVariable long id,Model model){

        UserVO updateLine = userService.findById(id);
        model.addAttribute("updateLine",updateLine);


        return "board/modify";
    }

    @PostMapping("/modify")
    public String modifySet(@ModelAttribute @Valid UserVO userVO, Errors errors,Model model){

        if (valiationForm(userVO, errors, model, "updateLine")) return "board/modify";

        userService.modifyBoard(userVO);

        return "redirect:/list";

    }

    private boolean valiationForm(@ModelAttribute @Valid UserVO userVO, Errors errors, Model model, String updateLine) {
        if (errors.hasErrors()) {
            model.addAttribute(updateLine, userVO);
            Map<String, String> validatorResult = userService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            return true;
        }
        return false;
    }
}
