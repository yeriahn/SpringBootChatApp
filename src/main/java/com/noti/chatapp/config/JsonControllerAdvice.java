package com.noti.chatapp.config;

import com.noti.chatapp.dto.ResultCodeVo;
import com.noti.chatapp.dto.SvcErrorCode;
import com.noti.chatapp.exception.ChatRoomNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * 정상적이지 못한 각종 에러패턴에 대한 Advicer
 * @DATE 2021-03-30
 * @Author ahnyr
 * @Version 1.0
 */
@Slf4j
@RestControllerAdvice
public class JsonControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ChatRoomNotFoundException.class)
    public ResponseEntity<Object> chatRoomNotFoundException(HttpServletRequest req, ChatRoomNotFoundException e) {
        ResultCodeVo resultCodeVo = new ResultCodeVo();
        resultCodeVo.setCmmResultCode(SvcErrorCode.NOTFOUND_ROOMID);
        return new ResponseEntity<>(resultCodeVo, HttpStatus.OK);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> nullPointerException(HttpServletRequest req, NullPointerException e) {
        ResultCodeVo resultCodeVo = new ResultCodeVo();
        resultCodeVo.setCmmResultCode(SvcErrorCode.INVALID_BODY_VALUE);
        return new ResponseEntity<>(resultCodeVo, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> defaultErrorHandler(HttpServletRequest req, Exception e) {
        ResultCodeVo resultCodeVo = new ResultCodeVo();
        resultCodeVo.setCmmResultCode(SvcErrorCode.RT_FAIL);
        return new ResponseEntity<>(resultCodeVo, HttpStatus.OK);
    }
}
