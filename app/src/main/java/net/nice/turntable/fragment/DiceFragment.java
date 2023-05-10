package net.nice.turntable.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import net.nice.turntable.R;
import net.nice.turntable.util.Coin;
import net.nice.turntable.util.MyUtil;

import java.util.ArrayList;
import java.util.Random;

public class DiceFragment extends Fragment implements View.OnClickListener{

    private Context context;
    private ImageButton startButton;
    private ArrayList<Integer> drawables;
    private ViewGroup layout;
    private ImageButton add;
    private ImageButton subtract;
    private TextView dice_num;
    private TextView points;
    private int allPoints = 0;
    private int  dice_Number;


    public DiceFragment(Context context){
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dice, container, false);

        initView(rootView);
        initDate();
        initListener();
        return rootView;
    }

    public void initView(View rootView){


        startButton = rootView.findViewById(R.id.start_button);
        layout = rootView.findViewById(R.id.my_desk);
        add = rootView.findViewById(R.id.add);
        subtract = rootView.findViewById(R.id.subtract);
        dice_num = rootView.findViewById(R.id.dice_num);
        points = rootView.findViewById(R.id.points);

    }
    public void initDate(){
        drawables = new ArrayList<>();
        drawables.add(R.mipmap.decisionsdk_dice1);
        drawables.add(R.mipmap.decisionsdk_dice2);
        drawables.add(R.mipmap.decisionsdk_dice3);
        drawables.add(R.mipmap.decisionsdk_dice4);
        drawables.add(R.mipmap.decisionsdk_dice5);
        drawables.add(R.mipmap.decisionsdk_dice6);
        dice_Number =  Integer.parseInt(dice_num.getText().toString());
    }

    public void initListener() {
        startButton.setOnClickListener(this);

        add.setOnClickListener(this);
        subtract.setOnClickListener(this);


    }

    public void start2(int num) {
        // 清除之前的视图
        layout.removeAllViews();


        AnimatorSet animatorSet = new AnimatorSet();

        // 设置子视图的布局参数
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
        );
        // 设置子视图的对齐方式为底部
        layoutParams.gravity = Gravity.BOTTOM;

        for (int i = 0; i < num; i++) {
            int pixelSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
            // 创建一个新的ImageView对象
            ImageView imageView = new ImageView(context);
            // 随机点数、距离、旋转圈数
            int point = new Random().nextInt(6);//随即点数
            float distance = new Random().nextFloat() * 5 + 4; // 生成4到9之间的随机单精度浮点数
            double quan = new Random().nextDouble() + 1;//1-2

            //统计点数
            allPoints += point;

            imageView.setImageResource(drawables.get(point));
            //添加布局参数
            imageView.setLayoutParams(layoutParams);

            // 设置ImageView的宽度和高度（以像素为单位）
            imageView.getLayoutParams().width = pixelSize;
            imageView.getLayoutParams().height = pixelSize;


            // 将ImageView添加到父视图中
            layout.addView(imageView);

            // 创建 ObjectAnimator 实例，指定要动画的属性为 translationY
            //Drawable drawable = drawables.get(new Random().nextInt(6));
            ObjectAnimator translationYAnimator = ObjectAnimator.ofFloat(imageView, "translationY", 0.0f, -distance * 138);

            // 设置动画持续时间
            translationYAnimator.setDuration(1000);

            // 创建 ObjectAnimator 实例，指定要动画的属性为 rotation
            ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(imageView, "rotation", 0.0f, (float) (360.0f*quan));
            rotationAnimator.setDuration(1000);
            rotationAnimator.setInterpolator(new DecelerateInterpolator());


            animatorSet.playTogether(translationYAnimator, rotationAnimator);

            // 监听器
            translationYAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float translationY = (float) valueAnimator.getAnimatedValue();
                }
            });

        }

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                points.setText("Points: "+(allPoints+dice_Number));
                points.setVisibility(View.VISIBLE);
                allPoints = 0;
                startButton.setClickable(true);
                startButton.setBackgroundResource(R.mipmap.decisionsdk_dice_start);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                points.setVisibility(View.GONE);
            }
        });
        // 启动动画
        animatorSet.start();
    }
    @SuppressLint("ShowToast")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_button:
                dice_Number = Integer.parseInt(dice_num.getText().toString());
                startButton.setClickable(false);
                startButton.setBackgroundResource(R.mipmap.decisionsdk_dice_start_click);
                start2(dice_Number);
                break;
            case R.id.add:
                if (Integer.parseInt(dice_num.getText().toString())>0&&Integer.parseInt(dice_num.getText().toString())<8){
                    dice_num.setText(String.valueOf(Integer.parseInt(dice_num.getText().toString())+1));
                }else {
                    Toast toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
                    toast.setText("The minimum number of dice is 1 and the maximum is 8");
                    toast.show();
                }
                break;
            case R.id.subtract:
                if (Integer.parseInt(dice_num.getText().toString())>1&&Integer.parseInt(dice_num.getText().toString())<9){
                    dice_num.setText(String.valueOf(Integer.parseInt(dice_num.getText().toString())-1));
                }else {
                    Toast toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
                    toast.setText("The minimum number of dice is 1 and the maximum is 8");
                    toast.show();
                }
        }
    }
}
