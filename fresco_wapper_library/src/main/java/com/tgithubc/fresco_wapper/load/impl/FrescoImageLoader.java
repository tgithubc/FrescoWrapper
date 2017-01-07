package com.tgithubc.fresco_wapper.load.impl;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.facebook.binaryresource.BinaryResource;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.tgithubc.fresco_wapper.config.FrescoConstant;
import com.tgithubc.fresco_wapper.config.ImageLoadConfig;
import com.tgithubc.fresco_wapper.config.ImageViewSize;
import com.tgithubc.fresco_wapper.listener.IDisplayImageListener;
import com.tgithubc.fresco_wapper.listener.IDownloadImageListener;
import com.tgithubc.fresco_wapper.supplier.BaseDisplayListenerSupplier;
import com.tgithubc.fresco_wapper.supplier.BaseDownloadListenerSupplier;
import com.tgithubc.fresco_wapper.supplier.BitmapMemoryCacheSupplier;
import com.tgithubc.fresco_wapper.supplier.HttpUrlConnectionSupplier;
import com.tgithubc.fresco_wapper.util.FrescoUtil;

import java.io.File;




/**
 * Created by tiancheng :)
 */
public class FrescoImageLoader extends BaseImageLoader<SimpleDraweeView> {

    private ImageLoadConfig mDefaultConfig;

    private static class SingletonHolder {
        private static final FrescoImageLoader INSTANCE = new FrescoImageLoader();
    }

    public static FrescoImageLoader getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private FrescoImageLoader() {
    }

    public void initialize(Context context) {

        if (context == null) {
            throw new RuntimeException("context is null");
        }

        context = !(context instanceof Application) ? context.getApplicationContext() : context;

        mDefaultConfig = new ImageLoadConfig.Builder(context).create();

        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(context)
                .setBaseDirectoryName(FrescoConstant.DEFAULT_DISK_CACHE_DIR_NAME)
                .setBaseDirectoryPath(context.getCacheDir())
                .setMaxCacheSize(FrescoConstant.DEFAULT_MAX_DISK_CACHE_SIZE)
                .setMaxCacheSizeOnLowDiskSpace(FrescoConstant.DEFAULT_LOW_SPACE_DISK_CACHE_SIZE)
                .setMaxCacheSizeOnVeryLowDiskSpace(FrescoConstant.DEFAULT_VERY_LOW_SPACE_DISK_CACHE_SIZE)
                .build();

        ImagePipelineConfig pipelineConfig = ImagePipelineConfig.newBuilder(context)
                .setBitmapMemoryCacheParamsSupplier(new BitmapMemoryCacheSupplier(context))
                .setNetworkFetcher(new HttpUrlConnectionSupplier())
                .setBitmapsConfig(FrescoConstant.DEFAULT_BITMAP_CONFIG)
                .setDownsampleEnabled(true)
                .setResizeAndRotateEnabledForNetwork(true)
                .setMainDiskCacheConfig(diskCacheConfig)
                .setResizeAndRotateEnabledForNetwork(true)
                .build();

        Fresco.initialize(context, pipelineConfig);
    }

    @Override
    public void load(SimpleDraweeView view, Uri uri, ImageLoadConfig config, IDisplayImageListener<ImageInfo> listener) {
        try {
            FrescoUtil.checkViewNotNull(view);
            FrescoUtil.checkUriIsLegal(uri);
        } catch (Exception e) {
            uri = Uri.parse("asset://exception.png");
            e.printStackTrace();
        }
        display(view, uri, listener, config);
    }

    @Override
    public void load(Uri uri, int w, int h, IDownloadImageListener<Bitmap> listener) {
        try {
            FrescoUtil.checkUriIsLegal(uri);
        } catch (Exception e) {
            uri = Uri.parse("asset://exception.png");
            e.printStackTrace();
        }
        download(uri, listener, w, h);
    }

    /**
     * 直接显示
     *
     * @param view
     * @param uri
     * @param listener
     * @param config
     */
    private void display(SimpleDraweeView view, Uri uri, IDisplayImageListener<ImageInfo> listener,
                         ImageLoadConfig config) {

        if (config == null) {
            config = mDefaultConfig;
        }
        GenericDraweeHierarchyBuilder hierarchyBuilder = new GenericDraweeHierarchyBuilder(null);
        hierarchyBuilder.setFadeDuration(config.fadeDuration);
        hierarchyBuilder.setRoundingParams(config.roundingParams);
        hierarchyBuilder.setActualImageScaleType(config.scaleType == null ? FrescoConstant.DEFAULT_SCALE_TYPE : config.scaleType);

        if (config.loadingDrawable != null) {
            ScalingUtils.ScaleType type = getScaleType(config.loadingDrawableScaleType, config);
            hierarchyBuilder.setPlaceholderImage(config.loadingDrawable, type);
        }
        if (config.failureDrawable != null) {
            ScalingUtils.ScaleType type = getScaleType(config.failureDrawableScaleType, config);
            hierarchyBuilder.setFailureImage(config.failureDrawable, type);
        }
        if (config.pressedDrawable != null) {
            hierarchyBuilder.setPressedStateOverlay(config.pressedDrawable);
        }
        if (config.retryDrawable != null) {
            hierarchyBuilder.setRetryImage(config.retryDrawable);
        }
        if (config.overlayDrawable != null) {
            hierarchyBuilder.setOverlay(config.overlayDrawable);
        }
        if (config.progressDrawable != null) {
            hierarchyBuilder.setProgressBarImage(config.progressDrawable);
        }

        if (config.aspectRatio > 0) {
            view.setAspectRatio(config.aspectRatio);
        }

        GenericDraweeHierarchy hierarchy = hierarchyBuilder.build();

        ImageRequestBuilder requestBuilder = ImageRequestBuilder.newBuilderWithSource(uri);
        requestBuilder.setAutoRotateEnabled(config.autoRotate);

        if (config.postprocessor != null) {
            requestBuilder.setPostprocessor(config.postprocessor);
        }

        //************************************按需压缩解码图片尺寸*******************************************
        int resizeWidth;
        int resizeHeight;
        if (config.resizeWidth > 0 && config.resizeHeight > 0) {
            resizeWidth = config.resizeWidth;
            resizeHeight = config.resizeHeight;
        } else {
            ImageViewSize size = FrescoUtil.getImageViewSize(view);
            resizeWidth = size.width;
            resizeHeight = size.height;
            Log.e("ImageViewSize", "ImageViewSize size.width :" + size.width + ",size.height :" + size.height);
        }
        if (resizeWidth > 0 && resizeHeight > 0) {
            requestBuilder.setResizeOptions(new ResizeOptions(resizeWidth, resizeHeight));
        }
        requestBuilder.setImageDecodeOptions(ImageDecodeOptions.newBuilder().setDecodePreviewFrame(true).build());
        //************************************************************************************************

        ImageRequest imageRequest = requestBuilder.build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setControllerListener(BaseDisplayListenerSupplier.newInstance(listener))
                .setOldController(view.getController())
                .setAutoPlayAnimations(config.autoPlayAnimations)
                .setTapToRetryEnabled(config.tapToRetry)
                .setImageRequest(imageRequest)
                .build();

        view.setHierarchy(hierarchy);
        view.setController(controller);
    }

    private ScalingUtils.ScaleType getScaleType(ScalingUtils.ScaleType defaultType, ImageLoadConfig config) {
        ScalingUtils.ScaleType type;
        if (defaultType == null) {
            type = config.scaleType;
            if (type == null) {
                type = FrescoConstant.DEFAULT_SCALE_TYPE;
            }
        } else {
            type = defaultType;
        }
        return type;
    }

    /**
     * 下载bitmap
     *
     * @param uri
     * @param listerer
     * @param w
     * @param h
     */
    private void download(final Uri uri, final IDownloadImageListener<Bitmap> listerer, int w, int h) {

        ImageRequestBuilder requestBuilder = ImageRequestBuilder.newBuilderWithSource(uri);
        if (w > 0 && h > 0) {
            requestBuilder.setResizeOptions(new ResizeOptions(w, h));
        }
        ImageRequest imageRequest = requestBuilder.build();

        DataSource<CloseableReference<CloseableImage>> dataSource = Fresco.getImagePipeline().fetchDecodedImage(imageRequest, null);
        dataSource.subscribe(BaseDownloadListenerSupplier.newInstance(listerer),
                UiThreadImmediateExecutorService.getInstance());
    }

    public void shutDown() {
        Fresco.shutDown();
        ImagePipelineFactory.shutDown();
    }

    /**
     * 在listview快速滑动时
     */
    public void pause() {
        //Fresco.getImagePipeline().pause();
    }

    /**
     * 当滑动停止时
     */
    public void resume() {
        //Fresco.getImagePipeline().resume();
    }

    public boolean isInDiskStorageCache(Uri uri) {
        if (uri == null) {
            return false;
        }
        ImageRequest imageRequest = ImageRequest.fromUri(uri);
        CacheKey cacheKey = DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(imageRequest, null);
        return ImagePipelineFactory.getInstance().getMainDiskStorageCache().hasKey(cacheKey);
    }

    /**
     * 通过uri拿到本地sd卡上的cnt图片文件路径
     *
     * @param uri
     * @return
     */
    public String getDiskStorageCachePath(Uri uri) {
        return getDiskStorageCache(uri) == null ? null : getDiskStorageCache(uri).getAbsolutePath();
    }


    /**
     * 通过uri拿到本地sd卡上的cnt图片文件
     *
     * @param uri
     * @return
     */
    public File getDiskStorageCache(Uri uri) {
        if (!isInDiskStorageCache(uri)) {
            return null;
        }
        ImageRequest imageRequest = ImageRequest.fromUri(uri);
        CacheKey cacheKey = DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(imageRequest, null);
        BinaryResource resource = ImagePipelineFactory.getInstance().getMainDiskStorageCache().getResource(cacheKey);
        return ((FileBinaryResource) resource).getFile();
    }

}
