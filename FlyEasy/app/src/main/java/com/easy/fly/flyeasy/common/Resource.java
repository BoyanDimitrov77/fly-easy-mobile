package com.easy.fly.flyeasy.common;


import android.support.annotation.NonNull;

import io.reactivex.annotations.Nullable;

/**
 * Created by boyan.dimitrov on 18.3.2018 г..
 */

public class Resource<T> {
    private static final String SUCCESS ="SUCCESS";
    private static final String ERROR = "ERROR";
    private static final String LOADING = "LOADING";
    @NonNull  public final String status;
    @Nullable public final T data;
    @Nullable public final String message;
    private Resource(@NonNull String status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(SUCCESS, data, null);
    }

    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(ERROR, data, msg);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(LOADING, data, null);
    }
}
