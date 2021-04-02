package com.noti.chatapp.config;

import com.noti.chatapp.dto.ResultCodeVo;
import com.noti.chatapp.dto.SvcErrorCode;
import com.noti.chatapp.exception.ChatRoomNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class GlobalControllerAdvice{

    @ExceptionHandler(ChatRoomNotFoundException.class)
    public String chatRoomNotFoundException(Model model, HttpServletRequest req) {
        log.info("GlobalControllerAdvice======");
        ResultCodeVo resultCodeVo = new ResultCodeVo();
        resultCodeVo.setCmmResultCode(SvcErrorCode.NOTFOUND_ROOMID);
        log.info("resultCodeVo : {}", resultCodeVo.toString());
        model.addAttribute("resultCodeVo", resultCodeVo);
        return "/error/room_failed";
    }
}
