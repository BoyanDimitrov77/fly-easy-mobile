package com.easy.fly.flyeasy.db.models;

public class BindingModel <T> {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
