package com.shiv.electronic.store.helpers;

import com.shiv.electronic.store.custome.PageableResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;

public class Helper {

    public static <U,V>PageableResponse<V> getPageableResponse(Page<U> page, Class<V> type){


        List<U> users = page.getContent();
        List<V> userDtoList = users.stream()
                .map(obj -> new ModelMapper().map(obj,type)).toList();

        PageableResponse<V> response
                =new PageableResponse<>();

        response.setContent(userDtoList);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalPages(page.getTotalPages());
        response.setTotalElements(page.getTotalElements());
        response.setLastPage(page.isLast());

        return response;
    }
}
