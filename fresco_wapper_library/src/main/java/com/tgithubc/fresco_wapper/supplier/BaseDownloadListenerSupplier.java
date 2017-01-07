package com.tgithubc.fresco_wapper.supplier;

import android.graphics.Bitmap;

import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.imagepipeline.image.CloseableBitmap;
import com.facebook.imagepipeline.image.CloseableImage;
import com.tgithubc.fresco_wapper.listener.IDownloadImageListener;


/**
 * Created by tiancheng :)
 */

public class BaseDownloadListenerSupplier extends BaseDataSubscriber<CloseableReference<CloseableImage>> {

    private IDownloadImageListener<Bitmap> listener;

    public static BaseDownloadListenerSupplier newInstance(IDownloadImageListener<Bitmap> listener) {
        return new BaseDownloadListenerSupplier(listener);
    }

    private BaseDownloadListenerSupplier(IDownloadImageListener<Bitmap> listener) {
        this.listener = listener;
    }


    @Override
    protected void onNewResultImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
        if (!dataSource.isFinished()) {
            return;
        }

        CloseableReference<CloseableImage> closeableImageRef = dataSource.getResult();
        Bitmap bitmap = null;
        if (closeableImageRef != null &&
                closeableImageRef.get() instanceof CloseableBitmap) {
            bitmap = ((CloseableBitmap) closeableImageRef.get()).getUnderlyingBitmap();
        }

        try {
            if (bitmap != null && !bitmap.isRecycled()) {
                Bitmap copy = bitmap.copy(bitmap.getConfig() == null ? Bitmap.Config.ARGB_8888
                        : bitmap.getConfig(), bitmap.isMutable());
                if (listener != null) {
                    listener.onSuccess(copy);
                }
            }
        } finally {
            CloseableReference.closeSafely(closeableImageRef);
        }
    }

    @Override
    protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
        if (listener == null) {
            return;
        }
        Throwable throwable = null;
        if (dataSource != null) {
            throwable = dataSource.getFailureCause();
        }
        listener.onFailure(throwable);
    }


    @Override
    public void onProgressUpdate(DataSource<CloseableReference<CloseableImage>> dataSource) {
        super.onProgressUpdate(dataSource);
        if (listener == null || dataSource == null) {
            return;
        }
        listener.onProgress(dataSource.getProgress());
    }

}