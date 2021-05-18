package mybatis.board.controller;

import lombok.extern.slf4j.Slf4j;
import mybatis.board.domain.UserVO;
import mybatis.board.mapper.UserMapper;
import mybatis.board.service.UserServiceImpl;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Controller;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class UserControllerTest {

    private final UserMapper userMapper;

    @Autowired
    public UserControllerTest(UserMapper userMapper){
        this.userMapper = userMapper;
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
        testItem.setTitle("우진은 수정당했다");
        userMapper.modifyBoard(testItem);

        //then

        Assertions.assertThat(testItem.getTitle()).isEqualTo("우진은 수정당했다");

    }


}