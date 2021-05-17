package mybatis.board.controller;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import mybatis.board.domain.Criteria;
import mybatis.board.domain.PageMaker;
import mybatis.board.domain.UserVO;
import mybatis.board.service.UserServiceImpl;
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

    private UserServiceImpl userServiceImpl;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/list")
    public String list(Model model , Criteria cri) throws Exception
    {
        List<UserVO> boardList = userServiceImpl.getBoardList(cri);
        model.addAttribute("boardList",boardList);

        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(cri);
        pageMaker.setTotalCount(userServiceImpl.listCount());

        model.addAttribute("pageMaker",pageMaker);
        return "board/list";
    }

    @GetMapping("/post")
    public String post(){
        return "board/post";
    }

    @PostMapping("/post")
    public String getPost(@ModelAttribute @Valid UserVO userVO, Errors errors, Model model){

        if(errors.hasErrors())
        {
            model.addAttribute("userVO",userVO);
            Map<String,String> validatorResult = userServiceImpl.validateHandling(errors);
            for(String key: validatorResult.keySet())
            {
                model.addAttribute(key,validatorResult.get(key));
            }

            return "board/post";
        }

        userServiceImpl.insertBoard(userVO);
        return "redirect:/list";
    }

    /**
     *게시글 새로고침시 조회수 무한증가 해결을 위해 cookie 사용
     * 
     */
    @GetMapping("/detailItem/{id}")
    public String editForm(@PathVariable Long id, Model model, HttpServletRequest request, HttpServletResponse response)
    {

        UserVO item = userServiceImpl.findById(id);

        Cookie[] cookies = request.getCookies();
        Cookie viewCookie = null;

        if(cookies !=null && cookies.length>0)
        {
            for(int i=0;i<cookies.length;i++)
            {
                if(cookies[i].getName().equals("cookie"+id))
                {
                    System.out.println("기존에 있는 쿠키");
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
                    System.out.println("쿠키가 없는 친구 었네?");

                    Cookie newCookie = new Cookie("cookie"+id,"|"+id+"|");

                    response.addCookie(newCookie);

                    int result = userServiceImpl.updateViewCnt(id);

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

                return "board/detailItem";
        } else
        {
            return "board/detailItemError";
        }

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        userServiceImpl.deleteById(id);

        return "redirect:/list";
    }
}
