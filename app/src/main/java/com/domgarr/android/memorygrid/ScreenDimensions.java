package com.domgarr.android.memorygrid;

import android.content.res.Resources;
import android.util.Log;

/**
 * Created by Domenic on 2017-05-12.
 */

public class ScreenDimensions {
    private static final String TAG = "ScreenDimensions";

    private int mHeight;
    private int mWidth;

    public ScreenDimensions(){
        mHeight = Resources.getSystem().getDisplayMetrics().heightPixels ;
        mWidth = Resources.getSystem().getDisplayMetrics().widthPixels ;

        Log.d(TAG,"Screen Height: "  + mHeight );
        Log.d(TAG,"Screen Width" +  mWidth);
    }

    public int getHeight() {
        return mHeight;
    }

    public int getWidth() {
        return mWidth;
    }
}
