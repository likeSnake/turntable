package net.nice.turntable.util;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class Coin extends AppCompatImageView {

    public Coin(Context context) {
        super(context);
    }

    public Coin(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Coin(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}