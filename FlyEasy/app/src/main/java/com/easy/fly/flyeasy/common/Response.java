package com.easy.fly.flyeasy.common;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Response<T> {
    @NonNull
    public final Status status;

    @Nullable
    public final Throwable error;

    @Nullable
    public final T data;

    public Response(@NonNull Status status, @Nullable T data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static <T> Response<T> success(@Nullable T data) {
        return new Response<>(Status.SUCCESS, data, null);
    }

    public static <T> Response error(Throwable data) {
        return new Response<>(Status.ERROR,null, data);
    }

    public static <T> Response<T> loading() {
        return new Response<>(Status.LOADING, null, null);
    }
}
