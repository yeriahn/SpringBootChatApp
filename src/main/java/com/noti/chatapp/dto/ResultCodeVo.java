package com.noti.chatapp.dto;

import lombok.Data;
import lombok.ToString;

/**
 *
 * @DATE 2020-03-30
 * @Author ahnyr
 * @Version 1.0
 */

@Data
@ToString
public class ResultCodeVo {
    private String resultCd;
    private String resultMessage;

    public void setCmmResultCode(SvcErrorCode svcErrorCode) {
        this.resultCd = svcErrorCode.getCode();
        this.resultMessage = svcErrorCode.getReasonPhrase();
    }

    public void setCmmResultCode(SvcErrorCode svcErrorCode, String subMessage) {
        this.resultCd = svcErrorCode.getCode();
        this.resultMessage = svcErrorCode.getReasonPhrase() + "(" + subMessage + ")";
    }
}
