package com.reticket.reticket.dto.wrapper;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ListWrapper<T> {

    private final List<T> wrapperList = new ArrayList<>();

    public void addItem(T t) {
        this.wrapperList.add(t);
    }

    public void addAll(List<T> t) {
        this.wrapperList.addAll(t);
    }
}
