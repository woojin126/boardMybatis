package mybatis.board.controller;

import lombok.extern.slf4j.Slf4j;
import mybatis.board.domain.UserVO;
import mybatis.board.service.UserServiceImpl;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Slf4j
@Controller
public class UserController {

    private UserServiceImpl userServiceImpl;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }


    @GetMapping("/list")
    public String list(Model model){
        List<UserVO> boardList = userServiceImpl.getBoardList();
        model.addAttribute("boardList",boardList);
        return "board/list";
    }

    @GetMapping("/post")
    public String post(){
        return "board/post";
    }

    @PostMapping("/post")
    public String getPost(@ModelAttribute UserVO userVO){

        userServiceImpl.insertBoard(userVO);

        return "redirect:/list";
    }

    @GetMapping("/detailItem/{id}")
    public String editForm(@PathVariable Long id, Model model){
        log.info("what is id = {}",id);
        UserVO item = userServiceImpl.findById(id);

        model.addAttribute("item",item);

        return "board/detailItem";
    }


}
