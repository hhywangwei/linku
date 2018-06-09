package com.linku.server.api;

import com.linku.server.BaseException;
import com.linku.server.api.vo.ResultVo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * BaseException拦截处理
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
@ControllerAdvice(annotations = RestController.class)
public class BaseExceptionAdvice {

    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public ResultVo<String> handleException(BaseException e){
        return ResultVo.error(e.getCode(), e.getMessage());
    }
}
