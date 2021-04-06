package com.noti.chatapp.config;

import com.noti.chatapp.dto.ResultCodeVo;
import com.noti.chatapp.dto.SvcErrorCode;
import com.noti.chatapp.exception.ChatRoomNotFoundException;
import com.noti.chatapp.exception.ParticipantCountException;
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
        ResultCodeVo resultCodeVo = new ResultCodeVo();
        resultCodeVo.setCmmResultCode(SvcErrorCode.NOTFOUND_ROOMID);
        model.addAttribute("resultCodeVo", resultCodeVo);
        return "/error/room_failed";
    }

    @ExceptionHandler(ParticipantCountException.class)
    public String participantCountException(Model model, HttpServletRequest req) {
        ResultCodeVo resultCodeVo = new ResultCodeVo();
        resultCodeVo.setCmmResultCode(SvcErrorCode.PARTICIPANT_EXCESS_COUNT);
        model.addAttribute("resultCodeVo", resultCodeVo);
        return "/error/participant_join_failed";
    }
}
