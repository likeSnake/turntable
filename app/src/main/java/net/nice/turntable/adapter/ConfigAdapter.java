package net.nice.turntable.adapter;

import static net.nice.turntable.util.ConfigUtil.updateConfig;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.nice.turntable.R;
import net.nice.turntable.bean.ConfigBean;
import net.nice.turntable.util.MyUtil;


import java.util.ArrayList;

public class ConfigAdapter extends RecyclerView.Adapter<ConfigAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ConfigBean> configBeans;

    public ConfigAdapter(Context context, ArrayList<ConfigBean> configBeans) {
        this.context = context;
        this.configBeans = configBeans;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private EditText Title;
        private ImageButton delete;
        public ViewHolder(View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.Title);
            delete = itemView.findViewById(R.id.delete);

        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_configure,parent,false);
        return new ViewHolder(view);
    }
    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,  int position) {
        ConfigBean configBean = configBeans.get(position);
        String name = configBean.getName();
        holder.Title.setText(name);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(position);
            }
        });
        holder.Title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String s1 = s.toString();
                configBean.setName(s1);
            }
        });

    }

    // 添加数据
    public void addItem(ConfigBean item) {
        configBeans.add(item);
        notifyItemInserted(configBeans.size() - 1); // 刷新插入的位置
    }
    // 删除数据
    public void removeItem(int position) {
        notifyItemRangeChanged(position, configBeans.size());
        configBeans.remove(position);
      //  updateConfig(configBeans);
        notifyItemRemoved(position); // 刷新删除的位置
    }

    public void save(){
        updateConfig(configBeans);
    }
    @Override
    public int getItemCount() {
        return configBeans.size();
    }
}
