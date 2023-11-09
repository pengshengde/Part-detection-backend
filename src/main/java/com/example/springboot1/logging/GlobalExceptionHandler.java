package com.example.springboot1.logging;



import com.example.springboot1.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static com.example.springboot1.common.Constants.CODE_5000;


@ControllerAdvice
@Component
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result globalExceptionHandle(Exception e) {
        log.error("===========全局统一异常处理============");
        log.error(getExceptionInfo(e));
        return new Result(CODE_5000, "error", e.getMessage());
    }

    private static String getExceptionInfo(Exception ex) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(out);
        ex.printStackTrace(printStream);
        String rs = new String(out.toByteArray());
        try {
            printStream.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
}
