package guru.springframework.controllers;

import guru.springframework.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView badRequest(Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("errors/400Error");
        modelAndView.addObject("exception", exception);
        log.warn("{}", exception.getMessage(), exception);
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("errors/404Error");
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }
}
