package life.yogaguo.community.advice;

import com.alibaba.fastjson.JSON;
import life.yogaguo.community.dto.ResultDTO;
import life.yogaguo.community.exception.CustomizeErrorCodde;
import life.yogaguo.community.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@RestControllerAdvice
@ControllerAdvice
public class CustomizeExceptionHandler {
    /**
     *
     * @param e
     * @param model
     * @param request
     * @return
     */
    @ExceptionHandler(Exception.class)
    ModelAndView handler(Throwable e, Model model, HttpServletRequest request, HttpServletResponse response) {
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            //返回Json
            ResultDTO resultDTO ;
            if (e instanceof CustomizeException) {
               resultDTO = ResultDTO.errorOf((CustomizeException)e);
            } else {
                 resultDTO = ResultDTO.errorOf(CustomizeErrorCodde.SYSTEM_ERROR);
            }
            try {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException e1) {
            }
            return null;
        } else {
            //错误页面跳转
            if (e instanceof CustomizeException) {
                model.addAttribute("message", e.getMessage());
            } else {
                model.addAttribute("message", CustomizeErrorCodde.SYSTEM_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }
}


