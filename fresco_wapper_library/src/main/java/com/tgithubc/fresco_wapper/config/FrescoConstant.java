package com.tgithubc.fresco_wapper.config;

import android.graphics.Bitmap;

import com.facebook.drawee.drawable.ScalingUtils;

/**
 * Created by tiancheng :)
 */
public class FrescoConstant {

    public static final int MB = 1024 << 10;
    public static final int DEFAULT_FADEDURATION = 200;

    public static final int DEFAULT_MAX_DISK_CACHE_SIZE = 250 * MB;
    public static final int DEFAULT_LOW_SPACE_DISK_CACHE_SIZE = 50 * MB;
    public static final int DEFAULT_VERY_LOW_SPACE_DISK_CACHE_SIZE = 25 * MB;

    public static final boolean DEFAULT_AUTO_ROTATE = false;
    public static final boolean DEFAULT_AUTO_PLAY_ANIMATIONS = true;
    public static final boolean DEFAULT_TAP_TO_RETRY = false;

    public static final int DEFAULT_MIN_MEMORY_CACHE_SIZE = 4 * MB;
    public static final int DEFAULT_MAX_MEMORY_CACHE_SIZE = 32 * MB;

    public static final Bitmap.Config DEFAULT_BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    /**
     fitXY: 不保存宽高比，填充满显示边界
     fitStart: 保持宽高比，缩小或者放大，使得图片完全显示在显示边界内, 但不居中，和显示边界左上对齐
     fitCenter: 保持宽高比，缩小或者放大，使得图片完全显示在边界内，居中显示
     fitEnd: 保持宽高比，缩小或者放大，使得图片完全显示在显示边界内, 和显示边界右下对齐
     center: 居中，无缩放
     centerInside: 使两边都在显示边界内，居中显示。如果图尺寸大于显示边界，则保持长宽比缩小图片
     centerCrop: 保持宽高比缩小或放大，使得两边都大于或等于显示边界。居中显示。
     focusCrop: 保持宽高比缩小或放大，使得两边都大于或等于显示边界, 指定某个点为居中点。
     */
    public static final ScalingUtils.ScaleType DEFAULT_SCALE_TYPE = ScalingUtils.ScaleType.CENTER_CROP;

    public static final String DEFAULT_DISK_CACHE_DIR_NAME = "image_cache";
    public static final String HTTP_SCHEME = "http";
    public static final String HTTPS_SCHEME = "https";
    public static final String LOCAL_FILE_SCHEME = "file";
    public static final String LOCAL_CONTENT_SCHEME = "content";
    public static final String LOCAL_ASSET_SCHEME = "asset";
    public static final String LOCAL_RESOURCE_SCHEME = "res";
    public static final String DATA_SCHEME = "data";//Uri中指定图片数据 data:mime/type;base64,	数据类型必须符合rfc2397规定(仅支持UTF-8)

}
