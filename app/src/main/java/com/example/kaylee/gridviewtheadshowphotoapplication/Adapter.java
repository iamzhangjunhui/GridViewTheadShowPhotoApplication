package com.example.kaylee.gridviewtheadshowphotoapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaylee on 2018/7/6.
 */

public class Adapter extends BaseAdapter {
    private List<Integer> mList = new ArrayList<>();
    private Context mContext;

    public Adapter(Context context) {
        mContext = context;
        mList.add(R.drawable.timg);
        mList.add(R.drawable.timg2);
        mList.add(R.drawable.timg3);
        mList.add(R.drawable.timg4);
        mList.add(R.drawable.timg5);
        mList.add(R.drawable.timg6);
        mList.add(R.drawable.timg7);
        mList.add(R.drawable.timg8);
        mList.add(R.drawable.timg9);
        mList.add(R.drawable.timg10);
        mList.add(R.drawable.timg11);
        mList.add(R.drawable.timg12);
        mList.add(R.drawable.timg13);
        mList.add(R.drawable.timg14);
        mList.add(R.drawable.timg15);
        mList.add(R.drawable.timg16);
        mList.add(R.drawable.timg17);
        mList.add(R.drawable.timg8);
        mList.add(R.drawable.timg19);
        mList.add(R.drawable.timg20);
        mList.add(R.drawable.timg21);
        mList.add(R.drawable.timg22);
    }

    public static boolean cancelPotentialWork(int data, ImageView imageView) {
        final MyAsykTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

        if (bitmapWorkerTask != null) {
            final int bitmapData = bitmapWorkerTask.data;
            if (bitmapData == 0 || bitmapData != data) {
                // Cancel previous task
                bitmapWorkerTask.cancel(true);
            } else {
                // The same work is already in progress
                return false;
            }
        }
        // No task associated with the ImageView, or an existing task was cancelled
        return true;
    }

    private static MyAsykTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        loadBitmap((Integer) getItem(position), viewHolder.mImageView);
        return convertView;
    }

    private Bitmap decodeBitmapFromResouce(int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(mContext.getResources(), resId, options);
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        if (outHeight > 100 || outWidth > 100) {
            int widthSample = outWidth / 100;
            int heightSample = outHeight / 100;
            options.inSampleSize = widthSample > heightSample ? widthSample : heightSample;
        }
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(mContext.getResources(), resId, options);
    }

    public void loadBitmap(int resId, ImageView imageView) {
        if (cancelPotentialWork(resId, imageView)) {
            final MyAsykTask task = new MyAsykTask(imageView);
            //占位图
            Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.qw);
            final AsyncDrawable asyncDrawable =
                    new AsyncDrawable(mContext.getResources(), bitmap, task);
            imageView.setImageDrawable(asyncDrawable);
            task.execute(resId);
        }
    }

    class ViewHolder {
        ImageView mImageView;

        public ViewHolder(View view) {
            mImageView = view.findViewById(R.id.image);
        }

    }

    public class MyAsykTask extends AsyncTask<Integer, Void, Bitmap> {
        public int data;
        private WeakReference imageWeakReference;

        public MyAsykTask(ImageView imageView) {
            imageWeakReference = new WeakReference(imageView);
        }

        @Override
        protected Bitmap doInBackground(Integer... integers) {
            data = integers[0];
            return decodeBitmapFromResouce(data);

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }

            if (imageWeakReference != null && bitmap != null) {
                final ImageView imageView = (ImageView) imageWeakReference.get();
                final MyAsykTask bitmapWorkerTask =
                        getBitmapWorkerTask(imageView);
                if (this == bitmapWorkerTask && imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }
}
