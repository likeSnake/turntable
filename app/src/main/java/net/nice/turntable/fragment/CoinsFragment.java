package net.nice.turntable.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import net.nice.turntable.R;
import net.nice.turntable.util.Coin;
import net.nice.turntable.util.MyUtil;

import java.util.Random;

public class CoinsFragment extends Fragment  {

    private Context context;
    private Boolean isHead = true;
    private Coin coin;

    private Boolean isFlipped = true;

    private TextView front_count;
    private TextView reverse_count;
    private TextView reset_count;
    public CoinsFragment(Context context){
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_coins, container, false);

        initView(rootView);
        initDate();
        initListener();
        return rootView;
    }
    public void initListener() {
        coin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCoin();
            }
        });

        reset_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                front_count.setText(String.valueOf(0));
                reverse_count.setText(String.valueOf(0));
            }
        });
    }
    public void initView(View rootView){
        // 获取ImageView
        coin = rootView.findViewById(R.id.coin);
        front_count = rootView.findViewById(R.id.front_count);
        reverse_count = rootView.findViewById(R.id.reverse_count);
        reset_count = rootView.findViewById(R.id.reset_count);
    }
    public void initDate(){

    }
    private void flipCoin() {
        // 放大/缩小动画
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1.0f, 1.6f, 1.0f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.0f, 1.6f, 1.0f);
        //随机旋转圈数
        int quan = new Random().nextInt(4) + 19;
        PropertyValuesHolder rotation = PropertyValuesHolder.ofFloat(View.ROTATION_X, 0, 180*quan);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(coin, scaleX, scaleY, rotation);
        animator.setDuration(6000); // 持续时间
        animator.setInterpolator(new AccelerateDecelerateInterpolator()); // 加速减速插值器模拟重力效果
        animator.setInterpolator(new BounceInterpolator());//硬币反弹效果
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // 动画结束时，增加计数
                addCount();
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = (float) animation.getAnimatedValue("rotationX");
                MyUtil.MyLog(progress);


                int quadrant = ((int)(progress / 90) % 4) + 1;
                MyUtil.MyLog(quadrant);

                if (quadrant==1 || quadrant==4){
                    if (!isHead) {
                        coin.setImageResource(R.mipmap.decision_coin_header);
                        isHead = true;
                        isFlipped = true;
                    }
                }else {
                    if (isHead) {
                        coin.setImageResource(R.mipmap.decision_coin_number);
                        isHead = false;
                    }
                }
            }
        });

        animator.start();
    }

    private void addCount() {
       // coin.setBackgroundResource(R.drawable.fan);
        if (isHead){
            //正面
            front_count.setText(String.valueOf(Integer.parseInt(front_count.getText().toString())+1));
        }else {
            //反面
            reverse_count.setText(String.valueOf(Integer.parseInt(reverse_count.getText().toString())+1));
        }
    }







}
