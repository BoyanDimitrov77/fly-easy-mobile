package com.easy.fly.flyeasy.common;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;

import java.util.Map;

public class HeaderAtuhenticationGlide {

    private  static LazyHeaders auth =null;

    private static LazyHeaders getInstance(String accesTokenGD){
        if(auth == null || !auth.getHeaders().containsValue("Bearer "+accesTokenGD)){
            auth = new LazyHeaders.Builder() // can be cached in a field and reused
                    .addHeader("Authorization", "Bearer "+accesTokenGD)
                    .build();
        }
        return auth;
    }

    public static GlideUrl loadUrl(String url,String accesTokenGD ){
       return new GlideUrl("https://"+url,getInstance(accesTokenGD));
    }

}
