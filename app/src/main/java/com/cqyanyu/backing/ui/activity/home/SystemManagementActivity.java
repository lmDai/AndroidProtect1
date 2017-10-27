package com.cqyanyu.backing.ui.activity.home;

import android.app.Fragment;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.manger.InfoManger;
import com.cqyanyu.backing.ui.activity.base.BaseActivity;
import com.cqyanyu.backing.ui.mvpview.home.SystemManagementView;
import com.cqyanyu.backing.ui.presenter.home.SystemManagementPresenter;
import com.cqyanyu.mvpframework.adapter.TabFragmentPagerAdapter;
import com.cqyanyu.mvpframework.utils.XScreenUtils;
import com.cqyanyu.mvpframework.utils.XToastUtil;
import com.cqyanyu.mvpframework.view.viewPager.XViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统管理
 * Created by Administrator on 2017/7/10.
 */
public class SystemManagementActivity extends BaseActivity<SystemManagementPresenter> implements SystemManagementView {

    private TabLayout mTabLayout;
    private XViewPager mViewPager;
    public static final String LABEL = "label";
    public static final String LABEL_VALUE_SEARCH_UNIT = "单位管理";
    public static final String LABEL_VALUE_SEARCH_SET = "设备管理";
    public static final String LABEL_VALUE_SEARCH_USER = "用户管理";
    public int totalTabNum = 3;//假如有四个Fragment页面
    public int openTabNum = 0;//当前已打开的页面数量
    private int curTab;

    @Override
    protected SystemManagementPresenter createPresenter() {
        return new SystemManagementPresenter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_add) {
            switch (mViewPager.getCurrentItem()) {
                case 0://增加单位
                    if (InfoManger.getInstance().isPermission("40")) {
                        startActivity(new Intent(mContext, AddUnitActivity.class)
                                .putExtra(AddUnitActivity.LABEL, AddUnitActivity.LABEL_VALUE_ADD)
                        );
                    } else {
                        XToastUtil.showToast("暂不拥有该权限！");
                    }
                    break;
                case 1://增加设备
                    if (InfoManger.getInstance().isPermission("61")) {
                        startActivity(new Intent(mContext, AddSetActivity.class)
                                .putExtra(AddSetActivity.LABEL, AddSetActivity.LABEL_VALUE_ADD)
                        );
                    } else {
                        XToastUtil.showToast("暂不拥有该权限！");
                    }
                    break;
                case 2://增加用户
                    if (InfoManger.getInstance().isPermission("63")) {
                        startActivity(new Intent(mContext, AddUserActivity.class)
                                .putExtra(AddUserActivity.LABEL, AddUserActivity.LABEL_VALUE_ADD)
                        );
                    } else {
                        XToastUtil.showToast("暂不拥有该权限！");
                    }
                    break;
            }
            return true;
        } else if (item.getItemId() == R.id.menu_search) {
            switch (mViewPager.getCurrentItem()) {
                case 0://搜索单位
                    startActivity(new Intent(mContext, SearchActivity.class)
                            .putExtra(SystemManagementActivity.LABEL, SystemManagementActivity.LABEL_VALUE_SEARCH_UNIT)
                    );
                    break;
                case 1://搜索设备
                    startActivity(new Intent(mContext, SearchActivity.class)
                            .putExtra(SystemManagementActivity.LABEL, SystemManagementActivity.LABEL_VALUE_SEARCH_SET)
                    );
                    break;
                case 2://搜索用户
                    startActivity(new Intent(mContext, SearchActivity.class)
                            .putExtra(SystemManagementActivity.LABEL, SystemManagementActivity.LABEL_VALUE_SEARCH_USER)
                    );
                    break;
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_system_management;
    }

    @Override
    protected void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (XViewPager) findViewById(R.id.view_pager);
    }

    @Override
    protected void initListener() {
        setTopTitle("单位管理");
    }

    @Override
    protected void initData() {
        curTab = getIntent().getIntExtra("curTab", 0);
        if (mPresenter != null) mPresenter.init();
    }

    @Override
    public void setViewPage(List<String> titles, ArrayList<Fragment> xFragment) {
        //设置TabLayout的样式
        LinearLayout linearLayout = (LinearLayout) mTabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.layout_divider_vertical));
        linearLayout.setDividerPadding(XScreenUtils.dip2px(mContext, 10));
        //添加数据
        final TabFragmentPagerAdapter xFragmentPagerAdapter = new TabFragmentPagerAdapter(getFragmentManager(), xFragment, titles);
        mViewPager.setAdapter(xFragmentPagerAdapter);
        mViewPager.setOffscreenPageLimit(xFragment.size());
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(curTab);
        //设置监听
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setTopTitle(tab.getText().toString());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //滑动监听加载数据，一次只加载一个标签页
//                if (openTabNum < totalTabNum) {
//                    ((SystemManagementFragment) xFragmentPagerAdapter.getItem(position)).sendMessage();
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
