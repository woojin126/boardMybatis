package mybatis.board.controller;

import lombok.extern.slf4j.Slf4j;
import mybatis.board.domain.reply.ReplyVO;
import mybatis.board.mapper.ReplyMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class ReplyControllerTest {

    private final ReplyMapper replyMapper;

    @Autowired
    public ReplyControllerTest(ReplyMapper replyMapper){
        this.replyMapper = replyMapper;
    }

    @Test
    public void 댓글작성테스트() throws Exception {
        Long id = 5l; //게시글 번호
        Long rno = 3L;

        ReplyVO replyVO = new ReplyVO(id, rno, "해위", "우진");//게시글 댓글 정보

        replyMapper.writeReply(replyVO);


        Assertions.assertTrue(replyMapper.writeReply(replyVO));
    }

    @Test
    public void 댓글작성삭제(){

        ReplyVO replyVO = new ReplyVO(5L, 3L, null, null);

        replyMapper.deleteReply(replyVO);

        Assertions.assertNull(replyMapper.findById(3L));

    }

    @Test
    public void 댓글컬럽조회(){

        Long rno =4L;

        ReplyVO item = replyMapper.findById(rno);

        org.assertj.core.api.Assertions.assertThat(item.getAuthor()).isEqualTo("우진");
    }

    @Test
    public void 댓글컬럼수정(){
        Long id =5L;
        Long rno=4L;
        ReplyVO replyVO = new ReplyVO(5L,4L,"안녕하세요","영훈");
        replyMapper.modifyReply(replyVO);

        org.assertj.core.api.Assertions.assertThat(replyVO.getContent()).isEqualTo("안녕하세요");


    }


}