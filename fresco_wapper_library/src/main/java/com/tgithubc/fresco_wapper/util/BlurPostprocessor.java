package com.tgithubc.fresco_wapper.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.commit451.nativestackblur.NativeStackBlur;
import com.facebook.cache.common.CacheKey;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.imagepipeline.request.BasePostprocessor;

/**
 * Created by tiancheng :)
 */

public class BlurPostprocessor extends BasePostprocessor {

    private static int DEFAULT_DOWN_SAMPLING = 1;

    private int mRadius;
    private int mSampling;

    public BlurPostprocessor(int radius) {
        this(radius, DEFAULT_DOWN_SAMPLING);
    }

    public BlurPostprocessor(int radius, int sampling) {
        this.mRadius = radius;
        this.mSampling = sampling;
    }

    @Override
    public void process(Bitmap dest, Bitmap source) {
        int width = source.getWidth();
        int height = source.getHeight();
        int scaledWidth = width / mSampling;
        int scaledHeight = height / mSampling;

        Bitmap blurredBitmap = Bitmap.createBitmap(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(blurredBitmap);
        canvas.scale(1 / (float) mSampling, 1 / (float) mSampling);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(source, 0, 0, paint);
        blurredBitmap = NativeStackBlur.process(source, mRadius);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(blurredBitmap, dest.getWidth(), dest.getHeight(), true);
        blurredBitmap.recycle();

        super.process(dest, scaledBitmap);
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public CacheKey getPostprocessorCacheKey() {
        return new SimpleCacheKey("mRadius=" + mRadius + ",mSampling=" + mSampling);
    }

}
