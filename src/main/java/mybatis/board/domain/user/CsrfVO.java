package mybatis.board.domain.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CsrfVO {

	private String name;
	private String age;
	private String email;
}