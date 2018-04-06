package com.evozon.training.controller.errorhandling;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class GlobalMethodHandlerExeptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception exception) {
        if (exception instanceof MaxUploadSizeExceededException) {
            ModelAndView modelAndView = new ModelAndView("picture-size-exception");
            modelAndView.getModel().put("pictureException", "Picture size exceeds limit of 2 MB!");
            return modelAndView;
        }
        return null;
    }
}
