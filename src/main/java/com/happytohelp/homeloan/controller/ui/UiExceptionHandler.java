package com.happytohelp.homeloan.controller.ui;


/*
 * import com.happytohelp.homeloan.exception.ApiException; import
 * jakarta.servlet.http.HttpServletRequest; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.ui.Model; import
 * org.springframework.web.bind.annotation.ControllerAdvice; import
 * org.springframework.web.bind.annotation.ExceptionHandler;
 * 
 * @ControllerAdvice(annotations = Controller.class) public class
 * UiExceptionHandler {
 * 
 * @ExceptionHandler(ApiException.class) public String handleApi(ApiException
 * ex, HttpServletRequest req, Model model) { model.addAttribute("message",
 * ex.getMessage()); model.addAttribute("path", req.getRequestURI()); return
 * "ui/error"; }
 * 
 * @ExceptionHandler(Exception.class) public String handleAny(Exception ex,
 * HttpServletRequest req, Model model) { model.addAttribute("message",
 * "Something went wrong: " + ex.getMessage()); model.addAttribute("path",
 * req.getRequestURI()); return "ui/error"; } }
 */


import com.happytohelp.homeloan.exception.ApiException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(annotations = Controller.class)
public class UiExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(UiExceptionHandler.class);

    @ExceptionHandler(ApiException.class)
    public String handleApi(ApiException ex, HttpServletRequest req, Model model) {
        log.error("UI ApiException at {}", req.getRequestURI(), ex);
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("path", req.getRequestURI());
        return "ui/error";
    }

    @ExceptionHandler(Exception.class)
    public String handleAny(Exception ex, HttpServletRequest req, Model model) {
        log.error("UI Exception at {}", req.getRequestURI(), ex);
        model.addAttribute("message", "Something went wrong: " + ex.getMessage());
        model.addAttribute("path", req.getRequestURI());
        return "ui/error";
    }
}