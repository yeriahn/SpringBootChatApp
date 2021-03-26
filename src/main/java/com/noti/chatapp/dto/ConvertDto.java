package com.noti.chatapp.dto;

import com.noti.chatapp.dto.chat.ChatParticipantDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ConvertDto {
    List<ChatParticipantDto> ChatParticipantDto;
}
