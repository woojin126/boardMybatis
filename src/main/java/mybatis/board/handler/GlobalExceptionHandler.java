package mybatis.board.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
@Controller
public class GlobalExceptionHandler {

    @ExceptionHandler(value=NullPointerException.class)
    public ModelAndView handleNullPointException(NullPointerException e){
        log.debug("errorMessage",e.getMessage());
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception",e);
        mav.setViewName("exception/ExceptionPage");

        log.debug("exception={}",e);

        return mav;
    }
}
