package mybatis.board.domain.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class UserPrincipalVO implements UserDetails {

    private ArrayList<UserVO> userVO;

    public UserPrincipalVO(ArrayList<UserVO> userAuthes){
        this.userVO = userAuthes;
    }

    @Override  /*이 메소드를 통해 한 계정에 권한을 몇개 가졌는지 확인이 가능*/
    public Collection<? extends GrantedAuthority> getAuthorities() { //유저가 갖고 있는 권한 목록

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        for(int x=0; x<userVO.size(); x++){
            authorities.add(new SimpleGrantedAuthority(userVO.get(x).getRoleName()));
        }

        return authorities;
    }

    @Override
    public String getPassword() {//유저 비밀번호
        return userVO.get(0).getPassword();
    }

    @Override
    public String getUsername() {// 유저 이름 혹은 아이디
        return userVO.get(0).getName();
    }

    @Override
    public boolean isAccountNonExpired() {// 유저 아이디가 만료 되었는지
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // 유저 아이디가 Lock 걸렸는지
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { //비밀번호가 만료 되었는지
        return true;
    }

    @Override
    public boolean isEnabled() { // 계정이 활성화 되었는지
        return true;
    }
}
