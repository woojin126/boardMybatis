package mybatis.board.domain.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserVO {

    private Long userNo;
    private String userId;
    private String password;
    private String name;

    private String roleName;
}
