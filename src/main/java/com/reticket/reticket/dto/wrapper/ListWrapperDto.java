package com.reticket.reticket.dto.wrapper;

import java.util.ArrayList;
import java.util.List;

public class ListWrapperDto {

    private List<WrapperClass> wrapperList;

    public ListWrapperDto(List<WrapperClass> wrapperList) {
        this.wrapperList = wrapperList;
    }

    public List<WrapperClass> getWrapperList() {
        return wrapperList;
    }

    public void setWrapperList(List<WrapperClass> wrapperList) {
        this.wrapperList = wrapperList;
    }
}
