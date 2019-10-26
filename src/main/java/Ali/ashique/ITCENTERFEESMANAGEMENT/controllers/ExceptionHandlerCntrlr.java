package Ali.ashique.ITCENTERFEESMANAGEMENT.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ControllerAdvice()
public class ExceptionHandlerCntrlr {
    @ExceptionHandler({NoSuchElementException.class})
    public String errorPage(Exception e, Model model) {
        model.addAttribute("e", e);
        return "404";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({NumberFormatException.class})
    public String error400Page(Model model, Exception e) {
        model.addAttribute("e", e);
        return "400";
    }
}
