package com.cqyanyu.backing.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.base.BaseFragment;
import com.cqyanyu.backing.ui.activity.home.SearchActivity;
import com.cqyanyu.backing.ui.fragment.alarm.AlarmFragment;
import com.cqyanyu.backing.ui.mvpview.warn.WarnView;
import com.cqyanyu.backing.ui.presenter.alarm.WarnPresenter;
import com.cqyanyu.mvpframework.adapter.TabFragmentPagerAdapter;
import com.cqyanyu.mvpframework.utils.XScreenUtils;

import java.util.ArrayList;

/**
 * 警告
 * Created by Administrator on 2017/6/9 0009.
 */
@SuppressLint("NewApi")
public class WarnFragment extends BaseFragment<WarnPresenter> implements WarnView {
    public static final String LABEL = "label";
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ArrayList<Fragment> xFragment;
    private int position = -1;
    private TextView btnRight;
    public int totalTabNum = 2;//假如有四个Fragment页面
    public int openTabNum = 0;//当前已打开的页面数量
    private TabFragmentPagerAdapter fragmentPagerAdapter;
    private boolean isLoadData = false;

    @Override
    protected WarnPresenter createPresenter() {
        return new WarnPresenter();
    }

    @Override
    protected int getLayout() {
        showBack = false;
        return R.layout.fragment_warn;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        btnRight = (TextView) view.findViewById(R.id.btn_right);
    }

    @Override
    protected void initListener() {
        setTopTitle("告警");
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mViewPager.getCurrentItem()) {
                    case 0://主机告警
                        startActivity(new Intent(mContext, SearchActivity.class)
                                .putExtra(AlarmFragment.LABEL, AlarmFragment.LABEL_VALUE_HOST)
                        );
                        break;
                    case 1://水系统告警
                        startActivity(new Intent(mContext, SearchActivity.class)
                                .putExtra(AlarmFragment.LABEL, AlarmFragment.LABEL_VALUE_WATER_SYSTEM)
                        );
                        break;
                }
            }
        });
    }

    @Override
    protected void initData() {
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_soushuo);
        drawable.setBounds(0, 0, 0, 0);
        btnRight.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null);
        if (mPresenter != null) mPresenter.init();
    }

    @Override
    public void setViewPage(ArrayList<String> titles, final ArrayList<Fragment> xFragment) {
        this.xFragment = xFragment;
        //设置TabLayout的样式
        final LinearLayout linearLayout = (LinearLayout) mTabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(getActivity(),
                R.drawable.layout_divider_vertical));
        linearLayout.setDividerPadding(XScreenUtils.dip2px(mContext, 10));
        //添加数据
        fragmentPagerAdapter = new TabFragmentPagerAdapter(getChildFragmentManager(), xFragment, titles);
        mViewPager.setAdapter(fragmentPagerAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager);
//        ((AlarmFragment) fragmentPagerAdapter.getItem(0)).sendMessage();
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //滑动监听加载数据，一次只加载一个标签页
//                if (openTabNum < totalTabNum) {
//                    ((AlarmFragment) fragmentPagerAdapter.getItem(position)).sendMessage();
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onResume() {
        if (xFragment != null && xFragment.size() > position && position >= 0) {
            mViewPager.setCurrentItem(position);
//            ((AlarmFragment) fragmentPagerAdapter.getItem(position)).sendMessage();
            position = -1;
        }
        super.onResume();
    }

    /**
     * 前往哪一个fragment
     */
    public void gotoFragment(int position) {
        this.position = position;
    }
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser && !isLoadData) {
//            if (mPresenter != null) mPresenter.init();
//            isLoadData = true;
//        } else {
//
//        }
//    }
}
