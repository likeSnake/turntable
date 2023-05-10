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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.nice.turntable.R;
import net.nice.turntable.bean.ConfigBean;
import net.nice.turntable.bean.RandomBean;
import net.nice.turntable.util.MyUtil;

import java.util.ArrayList;
import java.util.List;

public class RandomAdapter extends RecyclerView.Adapter<RandomAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Integer> randomBeans;

    public RandomAdapter(Context context, ArrayList<Integer> configBeans) {
        this.context = context;
        this.randomBeans = configBeans;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView randoms;

        public ViewHolder(View itemView) {
            super(itemView);
            randoms = itemView.findViewById(R.id.randoms);


        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_random,parent,false);
        return new ViewHolder(view);
    }
    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,  int position) {
      //  RandomBean configBean = randomBeans.get(position);
        Integer integer = randomBeans.get(position);
        holder.randoms.setText(String.valueOf(integer));

    }


    @Override
    public int getItemCount() {
        return randomBeans.size();
    }

    // 设置随机数列表
    @SuppressLint("NotifyDataSetChanged")
    public void setNumbers(List<Integer> numbers) {
        randomBeans = (ArrayList<Integer>) numbers;
        // 通知适配器数据已经改变
        notifyDataSetChanged();
    }
}
