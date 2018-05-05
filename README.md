# FrescoWrapper
wrapper fresco like uil imageload

## Usage
First add ```maven { url "https://jitpack.io" }``` to   
```
allprojects {
    repositories {
    }
} 
```

and add ```compile 'com.tgithubc:fresco_wrapper:1.0'```  


## Example:
```java
ImageLoaderWrapper.getInstance().load(view, url);

ImageLoaderWrapper.getInstance().load(view, url, config);

ImageLoaderWrapper.getInstance().load(view, url, config, new IDisplayImageListener<ImageInfo>() {
            
            @Override
            public void onSuccess(ImageInfo result, Animatable animatable) {
                //do someting
            }

            @Override
            public void onFailure(Throwable throwable) {
                //do someting
            }
        });
        
ImageLoaderWrapper.getInstance().load(url, new SimpleDownloaderListener(){

            @Override
            public void onSuccess(Bitmap result) {
                super.onSuccess(result);
            }

            @Override
            public void onFailure(Throwable throwable) {
                super.onFailure(throwable);
            }

            @Override
            public void onProgress(float progress) {
                super.onProgress(progress);
            }
        });
        
ImageLoaderWrapper.getInstance().load(url, width, height, new SimpleDownloaderListener(){

            @Override
            public void onSuccess(Bitmap result) {
                super.onSuccess(result);
            }

            @Override
            public void onFailure(Throwable throwable) {
                super.onFailure(throwable);
            }

            @Override
            public void onProgress(float progress) {
                super.onProgress(progress);
            }
        });
```




![](https://github.com/tgithubc/FrescoWapper/raw/master/demo_pic/25063B8D-2890-427B-BC46-3F1AEDD83A17.png) 
