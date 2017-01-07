package com.tgithubc.fresco_wapper.load.impl;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.facebook.imagepipeline.image.ImageInfo;
import com.tgithubc.fresco_wapper.config.ImageLoadConfig;
import com.tgithubc.fresco_wapper.listener.IDisplayImageListener;
import com.tgithubc.fresco_wapper.listener.IDownloadImageListener;
import com.tgithubc.fresco_wapper.load.Loader;
import com.tgithubc.fresco_wapper.util.FrescoUtil;


/**
 * Created by tiancheng :)
 */
public abstract class BaseImageLoader<T extends ImageView> implements Loader<T> {

    @Override
    public void load(T view, String url) {
        load(view, FrescoUtil.parseUri(url), null, null);
    }

    @Override
    public void load(T view, String url, IDisplayImageListener<ImageInfo> listener) {
        load(view, FrescoUtil.parseUri(url), null, listener);
    }

    @Override
    public void load(T view, String url, ImageLoadConfig config) {
        load(view, FrescoUtil.parseUri(url), config, null);
    }

    @Override
    public void load(T view, String url, ImageLoadConfig config, IDisplayImageListener<ImageInfo> listener) {
        load(view, FrescoUtil.parseUri(url), config, listener);
    }

    @Override
    public void load(T view, int resId) {
        load(view, FrescoUtil.parseUriFromResId(resId), null, null);
    }

    @Override
    public void load(T view, int resId, ImageLoadConfig config) {
        load(view, FrescoUtil.parseUriFromResId(resId), config, null);
    }

    @Override
    public void load(String url, IDownloadImageListener<Bitmap> listeren) {
        load(FrescoUtil.parseUri(url), 0, 0, listeren);
    }

    @Override
    public void load(String url, int w, int h, IDownloadImageListener<Bitmap> listeren) {
        load(FrescoUtil.parseUri(url), w, h, listeren);
    }

}
