package com.tgithubc.fresco_wapper;


import com.facebook.drawee.view.SimpleDraweeView;
import com.tgithubc.fresco_wapper.load.Loader;
import com.tgithubc.fresco_wapper.load.impl.FrescoImageLoader;


/**
 * Created by tiancheng :)
 */

public class ImageLoagerWapper {

    public static Loader<SimpleDraweeView> getInstance() {
        return FrescoImageLoader.getInstance();
    }

}
