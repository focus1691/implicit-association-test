package com.example.josh.iat;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.lang.ref.SoftReference;

public class ImageAsync {

    int[] mFrames;
    private ImageView mImageView;
    private Activity mActivity;
    private boolean isBlack;
    private SoftReference<AnimationDrawable> mAnimSoftRef;
    private AnimationDrawable mAnimHardRef;
    private boolean mShowAnim;

    ImageAsync(Activity activity, int[] frames, ImageView imageview, boolean skinColour) {
        mActivity = activity;
        mFrames = frames;
        mImageView = imageview;
        isBlack = skinColour;
    }

    public void showFirstFrame() {
        mShowAnim = false;
        Glide.clear(mImageView);
        Glide.with(mActivity).load(mFrames[0]).into(mImageView);
        mAnimHardRef = mAnimSoftRef == null ? null : mAnimSoftRef.get();
        if (mAnimHardRef == null) {
            loadAnimationAsync();
        }
    }

    private void loadAnimationAsync() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                long time = System.currentTimeMillis();
                final AnimationDrawable drawable = new AnimationDrawable();
                drawable.setOneShot(true);
                for(int frRes : mFrames) {
                    Bitmap temp = BitmapFactory.decodeResource(mActivity.getResources(), frRes);
                    temp = Bitmap.createScaledBitmap(temp, temp.getWidth() / 6, temp.getHeight() / 6, false);
                    drawable.addFrame(new BitmapDrawable(temp), 30);
                }
                mAnimSoftRef = new SoftReference<AnimationDrawable>(drawable);
                mAnimHardRef = drawable;
                if (mShowAnim) mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mShowAnim) showAnimationNow();
                    }
                });
                Log.d("Joshua", "BG load time: " + (System.currentTimeMillis() - time));
            }
        });

    }

    public void showAnimation() {
        Log.d("Joshua", "showAnimation(): " + mAnimHardRef);
        mShowAnim = true;
        if (mAnimHardRef != null) {
            showAnimationNow();
        }
    }


    //Should be called only when Drawable is ready.
    private void showAnimationNow() {
        Log.d("Joshua", "showAnimationNow()..");
        Glide.clear(mImageView);
        mImageView.setImageDrawable(mAnimHardRef);
        mAnimHardRef.start();
        mAnimHardRef = null;
        mShowAnim = false;
    }

    public void clear() {
        Glide.clear(mImageView);
    }

    public boolean isBlackMan() {
        return isBlack;
    }
}