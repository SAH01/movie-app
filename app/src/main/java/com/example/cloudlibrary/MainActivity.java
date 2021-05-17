package com.example.cloudlibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.cloudlibrary.Adapter.MyFragmentPagerAdapter;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener {
    //UI Objects
    private RadioGroup main_group;
    private RadioButton main_rb_first;//rb_channel
    private RadioButton main_rb_cloud;//rb_message
    private RadioButton main_rb_my;//rb_better
    private ViewPager vpager;

    private MyFragmentPagerAdapter mAdapter;

    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        bindViews();
        main_rb_first.setChecked(true);
        //在Android4.0以后，会发现，只要是写在主线程（就是Activity）中的HTTP请求，运行时都会报错，
        // 这是因为Android在4.0以后为了防止应用的ANR（Aplication Not Response）异常，
        // Android这个设计是为了防止网络请求时间过长而导致界面假死的情况发生。
        //这样做有些卡顿，可以使用线程，但是子线程无法改变view，之后在解决
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    private void bindViews() {
        main_group = (RadioGroup) findViewById(R.id.main_group);
        main_rb_first = (RadioButton) findViewById(R.id.main_rb_first);
        main_rb_cloud = (RadioButton) findViewById(R.id.main_rb_cloud);
        main_rb_my = (RadioButton) findViewById(R.id.main_rb_my);
        main_group.setOnCheckedChangeListener(this);
        RadioButton[] rbs = new RadioButton[3];
        rbs[0] =main_rb_first;
        rbs[1] = main_rb_cloud;
        rbs[2] = main_rb_my;
        for (RadioButton rb : rbs) {
            //挨着给每个RadioButton加入drawable限制边距以控制显示大小
            Drawable[] drawables = rb.getCompoundDrawables();
            //获取drawables
            Rect r = new Rect(0, 0, drawables[1].getMinimumWidth() * 2 / 3, drawables[1].getMinimumHeight() * 2 / 3);
            //定义一个Rect边界
            drawables[1].setBounds(r);
            //给指定的radiobutton设置drawable边界
//            if (rb.getId() == R.id.rb_more) {
//                r = new Rect(0, 0, drawables[1].getMinimumWidth(), drawables[1].getMinimumHeight());
//                drawables[1].setBounds(r);
//            }
            //添加限制给控件
            rb.setCompoundDrawables(null, drawables[1], null, null);
        }

        vpager = (ViewPager) findViewById(R.id.vpager);
        vpager.setAdapter(mAdapter);
        vpager.setCurrentItem(0);
        vpager.addOnPageChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.main_rb_first:
                vpager.setCurrentItem(PAGE_ONE);
                break;
            case R.id.main_rb_cloud:
                vpager.setCurrentItem(PAGE_TWO);
                break;
            case R.id.main_rb_my:
                vpager.setCurrentItem(PAGE_THREE);
                break;
        }
    }


    //重写ViewPager页面切换的处理方法
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        if (state == 2) {
            switch (vpager.getCurrentItem()) {
                case PAGE_ONE:
                    main_rb_first.setChecked(true);
                    break;
                case PAGE_TWO:
                    main_rb_cloud.setChecked(true);
                    break;
                case PAGE_THREE:
                    main_rb_my.setChecked(true);
                    break;
            }
        }
    }
}