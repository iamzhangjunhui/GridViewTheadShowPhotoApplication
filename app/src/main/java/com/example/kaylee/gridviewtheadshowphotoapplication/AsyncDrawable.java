package com.example.kaylee.gridviewtheadshowphotoapplication;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import java.lang.ref.WeakReference;

public class AsyncDrawable extends BitmapDrawable {
    private final WeakReference bitmapWorkerTaskReference;

    public AsyncDrawable(Resources res, Bitmap bitmap,
                         Adapter.MyAsykTask bitmapWorkerTask) {
        super(res, bitmap);
        bitmapWorkerTaskReference =
            new WeakReference(bitmapWorkerTask);
    }

    public Adapter.MyAsykTask  getBitmapWorkerTask() {
        return (Adapter.MyAsykTask) bitmapWorkerTaskReference.get();
    }
}