package com.tgithubc.fresco_wapper.supplier;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;

import com.facebook.common.internal.Supplier;
import com.facebook.common.util.ByteConstants;
import com.facebook.imagepipeline.cache.MemoryCacheParams;


/**
 * Created by tiancheng :)
 */

public class BitmapMemoryCacheSupplier implements Supplier<MemoryCacheParams> {

    private Context mContext;


    public BitmapMemoryCacheSupplier(Context context) {
        this.mContext = context;
    }

    @Override
    public MemoryCacheParams get() {
        int maxSize = (int) getMaxCacheSize();
        return new MemoryCacheParams(maxSize, Integer.MAX_VALUE, maxSize, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    private double getMaxCacheSize() {
        long max;
        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        int maxMemory = am.getMemoryClass();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && isLargeHeap(mContext) ) {
            maxMemory = am.getLargeMemoryClass();
        }
        if (maxMemory <= 32) {
            max = 6 * ByteConstants.MB;
        } else if (maxMemory <= 64) {
            max = 18 * ByteConstants.MB;
        } else {
            max = (maxMemory * ByteConstants.MB) / 8;
        }
        return max;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static boolean isLargeHeap(Context context) {
        return (context.getApplicationInfo().flags & ApplicationInfo.FLAG_LARGE_HEAP) != 0;
    }
}
