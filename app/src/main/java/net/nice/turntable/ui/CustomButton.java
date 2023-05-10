package net.nice.turntable.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;

import net.nice.turntable.R;

public class CustomButton extends AppCompatButton {

    public CustomButton(Context context) {
        super(context);
        init();
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setBackgroundResource(R.mipmap.decisionsdk_dice1);
        setTextColor(getResources().getColor(R.color.white));
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        setPadding(32, 16, 32, 16);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理按钮单击事件
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 处理按下事件
                break;
            case MotionEvent.ACTION_UP:
                // 处理释放事件
                break;
        }
        return super.onTouchEvent(event);
    }
}