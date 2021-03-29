package com.noti.chatapp.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageUtil {
    public static Pageable convertToZeroBasePage(Pageable pageable){
        return pageable.getPageNumber()<=0 ? PageRequest.of(0, pageable.getPageSize()) : PageRequest.of(pageable.getPageNumber()-1, pageable.getPageSize());
    }

    public static Pageable convertToZeroBasePageWithSort(Pageable pageable){
        Pageable zeroBasePageable = convertToZeroBasePage(pageable);
        return PageRequest.of(zeroBasePageable.getPageNumber(), zeroBasePageable.getPageSize(), pageable.getSort());
    }
}
