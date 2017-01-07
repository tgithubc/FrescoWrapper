package com.tgithubc.fresco_wapper.config;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.imagepipeline.request.Postprocessor;


/**
 * Created by tiancheng :)
 */

public class ImageLoadConfig {

    public boolean autoPlayAnimations;
    public boolean autoRotate;
    public boolean tapToRetry;
    public int fadeDuration;
    public int resizeWidth;
    public int resizeHeight;
    public float aspectRatio;
    public Drawable failureDrawable;
    public Drawable loadingDrawable;
    public Drawable overlayDrawable;
    public Drawable progressDrawable;
    public Drawable retryDrawable;
    public Drawable pressedDrawable;
    public ScalingUtils.ScaleType scaleType;
    public ScalingUtils.ScaleType loadingDrawableScaleType;
    public ScalingUtils.ScaleType failureDrawableScaleType;
    public Postprocessor postprocessor;
    public RoundingParams roundingParams;


    private ImageLoadConfig(Builder config) {
        this.autoPlayAnimations = config.autoPlayAnimations;
        this.autoRotate = config.autoRotate;
        this.tapToRetry = config.tapToRetry;
        this.fadeDuration = config.fadeDuration;
        this.resizeWidth = config.resizeWidth;
        this.resizeHeight = config.resizeHeight;
        this.aspectRatio = config.aspectRatio;
        this.failureDrawable = config.failureDrawable;
        this.loadingDrawable = config.loadingDrawable;
        this.overlayDrawable = config.overlayDrawable;
        this.progressDrawable = config.progressDrawable;
        this.retryDrawable = config.retryDrawable;
        this.pressedDrawable = config.pressedDrawable;
        this.scaleType = config.scaleType;
        this.loadingDrawableScaleType = config.loadingDrawableScaleType;
        this.failureDrawableScaleType = config.failureDrawableScaleType;
        this.postprocessor = config.postprocessor;
        this.roundingParams = config.roundingParams;
    }

    public static final class Builder {

        //default 配置
        private Context context;
        private boolean autoPlayAnimations = FrescoConstant.DEFAULT_AUTO_PLAY_ANIMATIONS;
        private boolean autoRotate = FrescoConstant.DEFAULT_AUTO_ROTATE;
        private boolean tapToRetry = FrescoConstant.DEFAULT_TAP_TO_RETRY;
        private int fadeDuration = FrescoConstant.DEFAULT_FADEDURATION;
        private int resizeWidth = 0;
        private int resizeHeight = 0;
        private float aspectRatio = 0f;
        private Drawable failureDrawable /*= resources.getDrawable(R.drawable.default_square)*/;
        private Drawable loadingDrawable /*= resources.getDrawable(R.drawable.default_square)*/;
        private Drawable overlayDrawable;
        private Drawable progressDrawable;
        private Drawable retryDrawable;
        private Drawable pressedDrawable;
        private ScalingUtils.ScaleType scaleType = FrescoConstant.DEFAULT_SCALE_TYPE;
        private ScalingUtils.ScaleType loadingDrawableScaleType;
        private ScalingUtils.ScaleType failureDrawableScaleType;
        private Postprocessor postprocessor;
        private RoundingParams roundingParams;

        public Builder(Context context) {
            this.context = context;
        }
        //default 配置


        public Builder circle() {//圆形
            this.roundingParams = RoundingParams.asCircle();
            return this;
        }

        public Builder circle(int shadeColor) {//圆形+遮罩
            this.roundingParams = RoundingParams.asCircle().setOverlayColor(shadeColor);
            return this;
        }

        public Builder circle(int borderWidth, int borderColor) {//圆形+边框
            this.roundingParams = RoundingParams.asCircle().setBorder(borderColor, borderWidth);
            return this;
        }

        /**
         * 圆角,4脚一样
         */
        public Builder roundedCorner(float radius) {
            this.roundingParams = RoundingParams.fromCornersRadius(radius);
            return this;
        }

        /**
         * 圆角+边框
         */
        public Builder roundedCorner(float radius, int borderWidth, int borderColor) {
            this.roundingParams = RoundingParams.fromCornersRadius(radius).setBorder(borderColor, borderWidth);
            return this;
        }

        /**
         * 4个角度分别设
         */
        public Builder roundedCorner(float topLeft, float topRight, float bottomRight, float bottomLeft) {
            this.roundingParams = RoundingParams.fromCornersRadii(topLeft, topRight, bottomRight, bottomLeft);
            return this;
        }

        /**
         * 4个角度+边框
         */
        public Builder roundedCorner(float topLeft, float topRight, float bottomRight, float bottomLeft, int borderWidth, int borderColor) {
            this.roundingParams = RoundingParams.fromCornersRadii(topLeft, topRight, bottomRight, bottomLeft).setBorder(borderColor, borderWidth);
            return this;
        }

        public Builder autoPlayAnimations(boolean autoPlayAnimations) {
            this.autoPlayAnimations = autoPlayAnimations;
            return this;
        }

        public Builder autoRotate(boolean autoRotate) {
            this.autoRotate = autoRotate;
            return this;
        }

        public Builder setTapToRetry(boolean tapToRetry) {
            this.tapToRetry = tapToRetry;
            return this;
        }

        public Builder setFadeDuration(int fadeDuration) {
            this.fadeDuration = fadeDuration;
            return this;
        }

        public Builder setFailureDrawable(int failureDrawableId) {
            this.failureDrawable = context.getResources().getDrawable(failureDrawableId);
            return this;
        }

        public Builder setLoadingDrawable(int defaultDrawableId) {
            this.loadingDrawable = context.getResources().getDrawable(defaultDrawableId);
            return this;
        }

        public Builder setFailureDrawable(int failureDrawableId, ScalingUtils.ScaleType type) {
            this.failureDrawable = context.getResources().getDrawable(failureDrawableId);
            this.failureDrawableScaleType = type;
            return this;
        }

        public Builder setLoadingDrawable(int loadingDrawableId, ScalingUtils.ScaleType type) {
            this.loadingDrawable = context.getResources().getDrawable(loadingDrawableId);
            this.loadingDrawableScaleType = type;
            return this;
        }

        public Builder setOverlayDrawable(int overlayDrawableId) {
            this.overlayDrawable = context.getResources().getDrawable(overlayDrawableId);
            return this;
        }

        public Builder setProgressDrawable(int progressDrawableId) {
            this.progressDrawable = context.getResources().getDrawable(progressDrawableId);
            return this;
        }

        public Builder setRetryDrawable(int retryDrawableId) {
            this.retryDrawable = context.getResources().getDrawable(retryDrawableId);
            return this;
        }

        public Builder setPressedDrawable(int pressedDrawableId) {
            this.pressedDrawable = context.getResources().getDrawable(pressedDrawableId);
            return this;
        }

        public Builder setResizeWidth(int resizeWidth) {
            this.resizeWidth = resizeWidth;
            return this;
        }

        public Builder setResizeHeight(int resizeHeight) {
            this.resizeHeight = resizeHeight;
            return this;
        }

        public Builder setScaleType(ScalingUtils.ScaleType type) {
            this.scaleType = type;
            return this;
        }

        public Builder setAspectRatio(float ratio) {
            this.aspectRatio = ratio;
            return this;
        }

        public Builder setPostprocessor(Postprocessor postprocessor) {
            this.postprocessor = postprocessor;
            return this;
        }

        public ImageLoadConfig create() {
            return new ImageLoadConfig(this);
        }
    }
}
