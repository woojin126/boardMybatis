package mybatis.board.service.user;

import lombok.extern.slf4j.Slf4j;
import mybatis.board.domain.user.UserPrincipalVO;
import mybatis.board.domain.user.UserVO;
import mybatis.board.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * DB로부터 회원정보를 가져와 있는 회원인지 아닌지 체크여부를 하는 중요한 메소드
 */
@Slf4j
@Service
public class UserServiceImpl implements UserDetailsService {
        /*https://gaemi606.tistory.com/entry/Spring-%EC%8A%A4%ED%94%84%EB%A7%81-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0Spring-Security-%EC%A0%81%EC%9A%A9%ED%95%98%EA%B8%B0-3-%EB%93%9C%EB%94%94%EC%96%B4-DB%EC%97%B0%EB%8F%99-%EB%A1%9C%EA%B7%B8%EC%9D%B8?category=745027 xml방식*/
    /*https://hyunsangwon93.tistory.com/27?category=746259*/
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserMapper userMapper,BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.userMapper = userMapper;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
/*사용자 아이디, 암호화된 패스워드, 권한 등) 넘겨줌*/

        ArrayList<UserVO> userAuthes  = userMapper.findByUserId(userId);
        userAuthes.forEach(System.out::println);
        if(userAuthes.size() == 0){
            throw new UsernameNotFoundException("User "+userId+" 찾을수 없다");
        }
        return new UserPrincipalVO(userAuthes);

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public String InsertUser(UserVO userVO){
        userVO.setPassword(bCryptPasswordEncoder.encode(userVO.getPassword()));
        long flag = userMapper.userSave(userVO);
        if(flag > 0){

            long userNo = userMapper.findUserNo(userVO.getUserId());
            long roleNo = userMapper.findRoleNo(userVO.getRoleName());

            userMapper.userRoleSave(userNo,roleNo);

            return "success";
        }
        return "fail";
    }


}
