package com.tgithubc.fresco_wapper.listener;

/**
 * Created by tiancheng :)
 */
public interface IDownloadImageListener<T> {

    void onSuccess(T result);

    void onFailure(Throwable throwable);

    void onProgress(float progress);
}
