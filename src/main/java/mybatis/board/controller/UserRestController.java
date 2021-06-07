package mybatis.board.controller;

import mybatis.board.domain.user.UserVO;
import mybatis.board.service.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public UserRestController(UserServiceImpl userServiceImpl){
        this.userServiceImpl=userServiceImpl;
    }

    @PostMapping("/user/save")
    public String saveUserInfo(@RequestBody UserVO userVO){
        return userServiceImpl.InsertUser(userVO);
    }


}
