package com.reticket.reticket.dto.wrapper;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class ListWrapperDto {

    private List<WrapperDto> wrapperList = new ArrayList<>();

}
