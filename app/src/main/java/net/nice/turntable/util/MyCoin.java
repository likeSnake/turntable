package net.nice.turntable.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import net.nice.turntable.R;

public class MyCoin {

    private static final float GRAVITY = 9.8f; // 重力加速度
    private static final float SCALE_FACTOR = 0.05f; // 缩放因子

    private Bitmap mBitmap;
    private float mX;
    private float mY;
    private float mZ;
    private float mVelocity;
    private float mRotation;
    private float mScale;
    private long mStartTime;

    public MyCoin(Context context) {
        System.out.println(context.getClass().getName());
        mBitmap = getBitmapFromVectorDrawable(context,R.drawable.ying_b);
       // mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ying_b);
        System.out.println(mBitmap);
        mX = 0;
        mY = 0;
        mZ = 0;
        mVelocity = 0;
        mRotation = 0;
        mScale = 1.0f;
        mStartTime = 0;
    }

    public void start(float x, float y, float z, long startTime) {
        mX = x;
        mY = y;
        mZ = z;
        mVelocity = 0;
        mRotation = 0;
        mScale = 1.0f;
        mStartTime = startTime;
    }

    public void update(long currentTime) {
        long elapsedTime = currentTime - mStartTime;
        mVelocity += GRAVITY * elapsedTime / 1000.0f;
        mY += mVelocity * elapsedTime / 1000.0f;
        mRotation = (float) (mY * 360 / (mZ * Math.PI));
        mScale = 1.0f + SCALE_FACTOR * mY / mZ;
    }

    public void draw(Canvas canvas) {
        canvas.save();
        canvas.translate(mX, mY);
        canvas.rotate(mRotation, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
        canvas.scale(mScale, mScale, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
        canvas.drawBitmap(mBitmap, 0, 0, null);
        canvas.restore();
    }

    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}