package net.nice.turntable.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import net.nice.turntable.R;

public class SettingFragment extends Fragment {
    private Context context;
    public SettingFragment(Context context){
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);
       /* initView(rootView);
        initDate();
        initListener();*/
        return rootView;
    }
}
