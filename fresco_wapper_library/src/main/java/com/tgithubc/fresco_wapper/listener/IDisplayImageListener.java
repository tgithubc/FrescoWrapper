package com.tgithubc.fresco_wapper.listener;

import android.graphics.drawable.Animatable;

/**
 * Created by tiancheng :)
 */
public interface IDisplayImageListener<T> {

    void onSuccess(T result, Animatable animatable);

    void onFailure(Throwable throwable);
}
