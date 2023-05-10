package net.nice.turntable.activity;

import static net.nice.turntable.constant.MyAppApiConfig.config;
import static net.nice.turntable.constant.MyAppApiConfig.zpTopic;
import static net.nice.turntable.util.ConfigUtil.updateConfig;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.mmkv.MMKV;

import net.nice.turntable.R;
import net.nice.turntable.adapter.ConfigAdapter;
import net.nice.turntable.bean.ConfigBean;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class DispositionAct extends AppCompatActivity implements View.OnClickListener{

    private ImageButton back;
    private ImageButton save;
    private ImageButton add_config;
    private EditText Title;
    private RecyclerView recyclerview;
    private ArrayList<ConfigBean> list = new ArrayList<>();
    private ConfigAdapter contactsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_disposition);
        overridePendingTransition(R.drawable.slide_in_right, R.drawable.slide_out_left);
        initUI();
        initListener();
        initData();
    }
    public void initUI(){
        back = findViewById(R.id.back);
        save = findViewById(R.id.save);
        add_config = findViewById(R.id.add_config);
        Title = findViewById(R.id.Title);
        recyclerview = findViewById(R.id.recyclerview);

    }

    public void initListener(){
        back.setOnClickListener(this);
        save.setOnClickListener(this);
        add_config.setOnClickListener(this);
    }
    public void initData(){
        String turntable_config = MMKV.defaultMMKV().decodeString(config);
        if (turntable_config!=null){
            //  defaultCount = Integer.parseInt(s);
            Type type = new TypeToken<ArrayList<ConfigBean>>() {}.getType();
            list = new Gson().fromJson(turntable_config, type);

            start(false,list);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
            //    startActivity(new Intent(this,MainActivity.class));
                finish();
                break;
            case R.id.add_config:
             //   list.add(new ConfigBean("New Configuration"));
              //  updateConfig(list);
                contactsAdapter.addItem(new ConfigBean("New"));
                break;
            case R.id.save:
                contactsAdapter.save();
                MMKV.defaultMMKV().encode(zpTopic,Title.getText().toString());
             //   startActivity(new Intent(this,MainActivity.class));
                finish();
                break;
        }
    }

    public void start(Boolean b, ArrayList<ConfigBean> list){
        LinearLayoutManager manager = new LinearLayoutManager(DispositionAct.this, LinearLayoutManager.VERTICAL, b);
        contactsAdapter = new ConfigAdapter(this,list);
        recyclerview.setLayoutManager(manager);
        if (b){
            System.out.println("执行");
            recyclerview.scrollToPosition(contactsAdapter.getItemCount()-1);
        }
        recyclerview.setAdapter(contactsAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}