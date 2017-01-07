package com.tgithubc.fresco_wapper.load;

import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.facebook.imagepipeline.image.ImageInfo;
import com.tgithubc.fresco_wapper.config.ImageLoadConfig;
import com.tgithubc.fresco_wapper.listener.IDisplayImageListener;
import com.tgithubc.fresco_wapper.listener.IDownloadImageListener;


/**
 * Created by tiancheng :)
 */
public interface Loader<T extends ImageView> {

    void load(T view, Uri uri, ImageLoadConfig config, IDisplayImageListener<ImageInfo> listener);

    void load(T view, String url);

    void load(T view, String url, ImageLoadConfig config);

    void load(T view, String url, IDisplayImageListener<ImageInfo> listener);

    void load(T view, String url, ImageLoadConfig config, IDisplayImageListener<ImageInfo> listener);

    void load(T view, int resId);

    void load(T view, int resId, ImageLoadConfig config);

    void load(String url, IDownloadImageListener<Bitmap> listener);

    void load(String url, int w, int h, IDownloadImageListener<Bitmap> listeren);

    void load(Uri uri, int w, int h, IDownloadImageListener<Bitmap> listener);
}
