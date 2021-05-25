package mybatis.board.controller;

import lombok.extern.slf4j.Slf4j;
import mybatis.board.domain.reply.ReplyVO;
import mybatis.board.service.reply.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;
import java.util.Map;

@Slf4j
@Controller
public class ReplyController {

    private final ReplyService replyService;


    @Autowired
    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("/deleteReply")
    public String deleteReply(@PathVariable int rno, Model model){

        replyService.deleteReply(rno);


        return "redirect:/detailItem/{rno}";

    }

    @PostMapping("/replyWrite")
    public String replyWrite(@Valid ReplyVO vo, Errors errors, Model model, RedirectAttributes rttr)throws Exception{

        if (valiationForm(vo, errors, rttr, "replyVO")) return "redirect:/detailItem/{id}";
        replyService.writeReply(vo);
        rttr.addAttribute("replyList",vo.getId());

        return "redirect:/detailItem/{replyList}";

    }

    private boolean valiationForm(@ModelAttribute @Valid ReplyVO vo, Errors errors, RedirectAttributes rttr, String updateLine) {
        if (errors.hasErrors()) {
           // rttr.addAttribute(updateLine, vo);
            Map<String, String> validatorResult = replyService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                rttr.addAttribute(key, validatorResult.get(key));
            }
            rttr.addAttribute("id",vo.getId());
            return true;
        }
        return false;
    }
}
