package net.nice.turntable.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.nice.turntable.R;
import net.nice.turntable.adapter.RandomAdapter;
import net.nice.turntable.util.MyUtil;

import java.util.ArrayList;
import java.util.List;

public class RandomFragment extends Fragment implements View.OnClickListener{

    // 动画持续时间（毫秒）
    private static final int ANIMATION_DURATION = 5000;
    // 动画开始时的间隔时间（毫秒）
    private static final int ANIMATION_INTERVAL_START = 10;
    // 动画结束时的间隔时间（毫秒）
    private static final int ANIMATION_INTERVAL_END = 500;

    // RecyclerView 需要的适配器
    private RandomAdapter mAdapter;

    // 随机数列表
    private List<Integer> mNumbers = new ArrayList<>();
    private RecyclerView recyclerView;
    private  Button startButton;
    // 是否允许重复的数字
    private boolean mAllowDuplicates = false;

    // 最小值
    private int mMinValue = 1;
    // 最大值
    private int mMaxValue = 10;
    // 随机数个数
    private int mNumberCount = 5;

    // 是否正在进行动画
    private boolean mIsAnimating = false;
    // 当前的数字（用于动画效果）
    private int mCurrentNumber = 0;
    // 处理动画效果的 Handler
    private Handler mHandler = new Handler();
    public long mStartTime = 0;
    // 动画效果的 Runnable
    private Runnable mAnimationRunnable = new Runnable() {
        // 动画开始时间（毫秒）


        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void run() {
            if (mIsAnimating) {
                // 获取当前的时间
                long currentTime = System.currentTimeMillis();
                // 计算动画的进度（从 0 到 1）
                float progress = (float) (currentTime - mStartTime) / ANIMATION_DURATION;
                // 如果动画已经结束
                if (progress >= 1) {
                    // 停止动画
                    stopAnimation();
                } else {
                    // 计算当前的间隔时间
                    int interval = getAnimationInterval(progress);
                    // 延迟 interval 毫秒后再次运行此 Runnable
                    MyUtil.MyLog(interval);
                    mHandler.postDelayed(this, interval);
                    // 获取一个随机数
                    mCurrentNumber = getRandomNumber();
                    // 通知适配器数据已经改变
                   // mAdapter.notifyDataSetChanged();
                    updateNumbers();
                }
            }
        }

        // 计算动画的间隔时间
        private int getAnimationInterval(float progress) {
            // 计算指数函数的值，其中 B 的值在动画进度范围内从小到大变化
            float b = 6.5f * progress;
            float value = (float) (ANIMATION_INTERVAL_START * Math.exp(b * Math.pow(progress, 2))) + ANIMATION_INTERVAL_END;
            // 转换为整数


            return Math.round(value);
        }
    };


    private Context context;
    private EditText Minimum;
    private EditText Maximum;
    private EditText number_builds;
    private ImageButton AllowRepetition;
    private TextView numbers;
    public RandomFragment(Context context){
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_random, container, false);

        initView(rootView);
        initListener();
        startAdapter();
        return rootView;
    }

    public void initView(View rootView){
        numbers = rootView.findViewById(R.id.numbers);
        startButton = rootView.findViewById(R.id.startButton);
        Minimum = rootView.findViewById(R.id.Minimum);
        Maximum = rootView.findViewById(R.id.Maximum);
        number_builds = rootView.findViewById(R.id.number_builds);
        AllowRepetition = rootView.findViewById(R.id.AllowRepetition);
        recyclerView = rootView.findViewById(R.id.recyclerView);


    }

    public void startAdapter(){
        // 获取 RecyclerView
        // 创建适配器
        mAdapter = new RandomAdapter(context, (ArrayList<Integer>) mNumbers);
        // 设置 RecyclerView 的 LayoutManager 和 Adapter
        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        recyclerView.setAdapter(mAdapter);
    }
    public void initDate(){
        numbers.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        String min = Minimum.getText().toString();
        String max = Maximum.getText().toString();
        String number = number_builds.getText().toString();
        
        if (min.equals("")){
            Toast toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
            toast.setText("The minimum value cannot be empty");
            toast.show();
            return;
        }
        if (max.equals("")){
            Toast toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
            toast.setText("The max value cannot be empty");
            toast.show();
            return;
        }
        if (number.equals("")){
            Toast toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
            toast.setText("The number value cannot be empty");
            toast.show();
            return;
        }
        if (Integer.parseInt(min)>=Integer.parseInt(max)){
            Toast toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
            toast.setText("The minimum value cannot be greater than or equal to the maximum value");
            toast.show();
            return;
        }
        if (!mAllowDuplicates) {
            if (Integer.parseInt(max) - Integer.parseInt(min) < Integer.parseInt(number)) {
                Toast toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
                toast.setText("Because it cannot be repeated, the number of builds is insufficient");
                toast.show();
                return;
            }
        }

        mMinValue = Integer.parseInt(min);
        mMaxValue = Integer.parseInt(max);
        mNumberCount = Integer.parseInt(number);


        startAnimation();

    }

    public void initListener() {
        // 设置开始按钮的单击侦听器
        startButton.setOnClickListener(this);
        AllowRepetition.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.startButton:

                initDate();
                break;
            case R.id.AllowRepetition:
                if (mAllowDuplicates) {
                    AllowRepetition.setBackgroundResource(R.drawable.shutdown);
                    mAllowDuplicates = false;
                }else {
                    AllowRepetition.setBackgroundResource(R.drawable.open);
                    mAllowDuplicates = true;
                }
        }
    }

    // 更新随机数列表
    private void updateNumbers() {
        // 清空随机数列表
        mNumbers.clear();

        // 生成 mNumberCount 个随机数
        for (int i = 0; i < mNumberCount; i++) {
            // 获取一个随机数
            int randomNumber = getRandomNumber();
            // 如果不允许重复的数字
            if (!mAllowDuplicates) {
                // 循环直到生成一个不在列表中的随机数
                while (mNumbers.contains(randomNumber)) {
                    randomNumber = getRandomNumber();
                }
            }
            // 将随机数添加到列表中
            mNumbers.add(randomNumber);
        }

        if (mNumberCount==1){
            numbers.setText(String.valueOf(mNumbers.get(0)));
            numbers.setVisibility(View.VISIBLE);
        }else {
            // 通知适配器数据已经改变
            mAdapter.setNumbers(mNumbers);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    // 获取一个随机数
    private int getRandomNumber() {
        return (int) (Math.random() * (mMaxValue - mMinValue + 1)) + mMinValue;
    }

    // 开始动画效果
    private void startAnimation() {
        startButton.setClickable(false);
        startButton.setBackgroundResource(R.drawable.shape_no);
        // 设置 mIsAnimating 为 true
        mIsAnimating = true;
        // 获取当前的时间
        long currentTime = System.currentTimeMillis();
        // 设置动画开始时间
        mStartTime = currentTime;
        // 运行动画效果的 Runnable
        mHandler.postDelayed(mAnimationRunnable, ANIMATION_INTERVAL_START);
    }

    // 停止动画效果
    private void stopAnimation() {
        // 设置 mIsAnimating 为 false
        mIsAnimating = false;
        // 重置 mCurrentNumber
        mCurrentNumber = 0;
        // 移除动画效果的 Runnable
        mHandler.removeCallbacks(mAnimationRunnable);
        // 更新随机数列表
        updateNumbers();
        //可点击
        startButton.setClickable(true);
        startButton.setBackgroundResource(R.drawable.shape);
    }

}
