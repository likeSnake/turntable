package net.nice.turntable.fragment;

import static net.nice.turntable.constant.MyAppApiConfig.config;
import static net.nice.turntable.constant.MyAppApiConfig.zpTopic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.mmkv.MMKV;

import net.nice.turntable.R;
import net.nice.turntable.activity.DispositionAct;
import net.nice.turntable.bean.ConfigBean;
import net.nice.turntable.util.LuckPanLayout;
import net.nice.turntable.util.RotatePan;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TurntableFragment extends Fragment implements View.OnClickListener , LuckPanLayout.AnimationEndListener {
    private LuckPanLayout luckPanLayout;
    private RotatePan rotatePan;
    private ImageView go;
    private Context context;
    private ImageButton configure;
    private ArrayList<ConfigBean> result = new ArrayList<>();
    private TextView topic;
    private Button start;
    public TurntableFragment(Context context){
        this.context = context;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_zhuan_pan, container, false);
        initView(rootView);
        initDate();
        initListener();
        return rootView;
    }



    public void initView(View rootView){
        luckPanLayout = rootView.findViewById(R.id.luckpan_layout);
        rotatePan = rootView.findViewById(R.id.rotatePan);
        go = rootView.findViewById(R.id.go);
        luckPanLayout = rootView.findViewById(R.id.luckpan_layout);
        configure = rootView.findViewById(R.id.configure);
        topic = rootView.findViewById(R.id.topic);
        start = rootView.findViewById(R.id.start);

    }
    public void initListener(){
        go.setOnClickListener(this);
        luckPanLayout.setAnimationEndListener(this);
        configure.setOnClickListener(this);
        start.setOnClickListener(this);
    }
    public void initDate(){
        //String s = MMKV.defaultMMKV().decodeString(zpCount);
        String turntable_config = MMKV.defaultMMKV().decodeString(config);
        String turntable_Topic = MMKV.defaultMMKV().decodeString(zpTopic);

        if (turntable_config!=null){

            Type type = new TypeToken<ArrayList<ConfigBean>>() {}.getType();
            result = new Gson().fromJson(turntable_config, type);

            rotatePan.setDatas(getNames(result));//设置转盘数量
        }else {
            //使用默认值
            result.add(new ConfigBean("Beef ramen"));
            result.add(new ConfigBean("Sandwich"));
            result.add(new ConfigBean("Hamburger"));
            result.add(new ConfigBean("Apple pie"));
            result.add(new ConfigBean("Salad"));
            String s = new Gson().toJson(result);
            MMKV.defaultMMKV().encode(config,s);

            rotatePan.setDatas(getNames(result));//设置转盘数量

        }
        if (turntable_Topic!=null){
            topic.setText(turntable_Topic);
        }


    }

    private List<String> getNames(ArrayList<ConfigBean> result) {
        List<String> data = new ArrayList<>();
        for (ConfigBean configBean : result) {
            data.add(configBean.getName());
        }
        return data;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go:
            case R.id.start:
                start.setClickable(false);
                start.setBackgroundResource(R.drawable.shape_no);
                luckPanLayout.rotate(-1, 100);
                break;
            case R.id.configure:
                 context.startActivity(new Intent(context, DispositionAct.class));
                ((Activity)context).overridePendingTransition(R.drawable.activity_in_left,
                        R.drawable.activity_out_right);
                break;
        }

    }

    @Override
    public void endAnimation(int position) {

        String name = result.get(position).getName();
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_simple);
        dialog.getWindow().setBackgroundDrawableResource(R.color.my_colors);
        dialog.setCancelable(true);
        TextView viewById = dialog.findViewById(R.id.dialog_text);
        viewById.setText(name);
        dialog.findViewById(R.id.dialog_close_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        start.setClickable(true);
        start.setBackgroundResource(R.drawable.shape);
    }

    public void updateData() {
        // 更新数据或视图
        initDate();
    }
}
