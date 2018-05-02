package com.easy.fly.flyeasy.db.models;

import java.util.Observable;

public class CombineModel<T1,T2> extends Observable {

    private T1 dataT1;

    private T2 dataT2;

    public CombineModel(T1 dataT1,T2 dataT2){
        this.setDataT1(dataT1);
        this.setDataT2(dataT2);
    }

    public T1 getDataT1() {
        return dataT1;
    }

    public void setDataT1(T1 dataT1) {
        this.dataT1 = dataT1;
    }

    public T2 getDataT2() {
        return dataT2;
    }

    public void setDataT2(T2 dataT2) {
        this.dataT2 = dataT2;
    }
}
