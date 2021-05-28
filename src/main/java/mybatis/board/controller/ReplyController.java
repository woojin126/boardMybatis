package mybatis.board.controller;

import lombok.extern.slf4j.Slf4j;
import mybatis.board.domain.reply.ReplyVO;
import mybatis.board.service.reply.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
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
    public String deleteReply(@ModelAttribute ReplyVO vo,RedirectAttributes rttrs,@RequestParam long id){
        log.debug("deleteReplyByReplyVO={}",vo);
        replyService.deleteReply(vo);

        rttrs.addAttribute("id",id);

       return "redirect:/detailItem";

    }

    @PostMapping("/replyWrite")
    public String replyWrite(@Valid ReplyVO vo, Errors errors, RedirectAttributes rttr,Model model)throws Exception{
        log.debug("replyWriterContent={}",vo);
        if (valiationForm(vo, errors,model, rttr, "replyVO")) return "redirect:/detailItem";
        replyService.writeReply(vo);
        rttr.addAttribute("id",vo.getId());
        return "redirect:/detailItem";

    }

    @GetMapping("/modifyReply")
    public String modifyReplyGet(@RequestParam long rno, Model model){

        ReplyVO replyColumn = replyService.findById(rno);

        model.addAttribute("replyColumn",replyColumn);

        return "board/modifyComment";
    }

    @PostMapping("/modifyReply")
    public String modifyReplyPost(@ModelAttribute @Valid ReplyVO vo,Errors errors, RedirectAttributes rttr,Model model) throws Exception{

        if (valiationForm(vo, errors,model, rttr, "replyColumn")) return "board/modifyComment";

        replyService.modifyReply(vo);
        rttr.addAttribute("id",vo.getId());
        return "redirect:/detailItem";
    }

    private boolean valiationForm(@ModelAttribute @Valid ReplyVO vo, Errors errors,Model model ,RedirectAttributes rttr, String voName) {
        if (errors.hasErrors()) {

            Map<String, String> validatorResult = replyService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                log.debug("validationFormByErrorKey={}",key);
                rttr.addAttribute(key, validatorResult.get(key));
                model.addAttribute(key,validatorResult.get(key));
            }
            rttr.addAttribute("id",vo.getId()); //error 내용을들 가지고 다시 detailItem 페이지를 열기위해 같이 id값 보냄
            model.addAttribute(voName, vo);
            return true;
        }
        return false;
    }
}
