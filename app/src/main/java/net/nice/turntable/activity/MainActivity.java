package net.nice.turntable.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import net.nice.turntable.R;
import net.nice.turntable.fragment.CoinsFragment;
import net.nice.turntable.fragment.DiceFragment;
import net.nice.turntable.fragment.RandomFragment;
import net.nice.turntable.fragment.SettingFragment;
import net.nice.turntable.fragment.TurntableFragment;
import net.nice.turntable.util.MyUtil;

public class MainActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;
    private Fragment[] fragments;
    private int lastFragment;
    private BottomNavigationView mNavigationView;

    private TurntableFragment mHomeFragment;
    private DiceFragment mPlanFragment;
    private CoinsFragment mGameFragment;
    private RandomFragment randomFragment;
    private SettingFragment mSettingFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        initUI();
        initFragment();
        initListener();

    }

    public void initUI(){
        mNavigationView = findViewById(R.id.main_navigation_bar);
    }

    private void initFragment() {
        mHomeFragment = new TurntableFragment(this);
        mPlanFragment = new DiceFragment(this);
        mGameFragment = new CoinsFragment(this);
        randomFragment = new RandomFragment(this);
        mSettingFragment = new SettingFragment(this);
        fragments = new Fragment[]{mHomeFragment, mPlanFragment, mGameFragment,randomFragment,mSettingFragment};
        mFragmentManager = getSupportFragmentManager();
        //默认显示HomeFragment
        mFragmentManager.beginTransaction()
                .replace(R.id.main_page_controller, mHomeFragment)
                .show(mHomeFragment)
                .commit();
    }

    private void initListener() {
        mNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.turntable:
                        if (lastFragment != 0) {
                            MainActivity.this.switchFragment(lastFragment, 0);
                            lastFragment = 0;
                        }
                        return true;
                    case R.id.dice:
                        if (lastFragment != 1) {
                            MainActivity.this.switchFragment(lastFragment, 1);
                            lastFragment = 1;
                        }
                        return true;
                    case R.id.game:
                        if (lastFragment != 2) {
                            MainActivity.this.switchFragment(lastFragment, 2);
                            lastFragment = 2;
                        }
                        return true;
                    case R.id.random:
                        if (lastFragment != 3) {
                            MainActivity.this.switchFragment(lastFragment, 3);
                            lastFragment = 3;
                        }
                        return true;
                    case R.id.setting:
                        if (lastFragment != 4) {
                            MainActivity.this.switchFragment(lastFragment, 4);
                            lastFragment = 4;
                          //  mNavigationView.setSelectedItemId(R.id.setting);
                        }
                        return true;

                }
                return false;
            }
        });
    }

    private void switchFragment(int lastFragment, int index) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.hide(fragments[lastFragment]);
        if (!fragments[index].isAdded()){
            transaction.add(R.id.main_page_controller,fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyUtil.MyLog("onResume");
        mHomeFragment.updateData();
    }
}