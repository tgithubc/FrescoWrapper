package com.tgithubc.fresco_wapper.supplier;

import android.graphics.drawable.Animatable;

import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;
import com.tgithubc.fresco_wapper.listener.IDisplayImageListener;


/**
 * Created by tiancheng :)
 */

public class BaseDisplayListenerSupplier extends BaseControllerListener<ImageInfo> {

    private IDisplayImageListener<ImageInfo> listener;

    public static BaseDisplayListenerSupplier newInstance(IDisplayImageListener<ImageInfo> listener) {
        return new BaseDisplayListenerSupplier(listener);
    }

    private BaseDisplayListenerSupplier(IDisplayImageListener<ImageInfo> listener) {
        this.listener = listener;
    }

    @Override
    public void onFailure(String id, Throwable throwable) {
        super.onFailure(id, throwable);
        if (listener == null) {
            return;
        }
        listener.onFailure(throwable);
    }

    @Override
    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
        super.onFinalImageSet(id, imageInfo, animatable);
        if (listener == null || imageInfo == null) {
            return;
        }
        listener.onSuccess(imageInfo, animatable);
    }

}