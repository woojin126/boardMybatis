package mybatis.board.mapper;

import mybatis.board.domain.board.BoardVO;
import mybatis.board.domain.user.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface UserMapper {
    //유저 정보
    ArrayList<UserVO> findByUserId(@Param("userId") String userId);

    //유저 저장
    int userSave(UserVO boardVO);

    //유저 권한 저장
    int userRoleSave(@Param("userNo") Long userNo,@Param("roleNo") Long roleNo);

    //유저 FK번호 알아내기
    int findUserNo(@Param("userId") String userId);

    //권한 FK번호 알아내기
    int findRoleNo(@Param("roleName") String roleName);
}
