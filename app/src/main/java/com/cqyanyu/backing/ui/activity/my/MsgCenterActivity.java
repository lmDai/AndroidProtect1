package com.cqyanyu.backing.ui.activity.my;

import android.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.widget.LinearLayout;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.base.BaseActivity;
import com.cqyanyu.backing.ui.mvpview.my.MsgCenterView;
import com.cqyanyu.backing.ui.presenter.my.MsgCenterPresenter;
import com.cqyanyu.mvpframework.adapter.TabFragmentPagerAdapter;
import com.cqyanyu.mvpframework.utils.XScreenUtils;
import com.cqyanyu.mvpframework.view.viewPager.XViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息中心
 * Created by Administrator on 2017/7/11.
 */

public class MsgCenterActivity extends BaseActivity<MsgCenterPresenter> implements MsgCenterView {
    /**
     * 页面相关
     */
    private TabLayout mTabLayout;
    private XViewPager mViewPager;

    /**
     * 创建presenter
     *
     * @return
     */
    @Override
    protected MsgCenterPresenter createPresenter() {
        return new MsgCenterPresenter();
    }

    /**
     * 绑定布局文件
     *
     * @return
     */
    @Override
    protected int getLayout() {
        return R.layout.activity_msg_center;
    }

    /**
     * 初始化view
     */
    @Override
    protected void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (XViewPager) findViewById(R.id.view_pager);
    }

    @Override
    protected void initListener() {
        setTopTitle("消息中心");
    }

    @Override
    protected void initData() {
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
        TabFragmentPagerAdapter xFragmentPagerAdapter = new TabFragmentPagerAdapter(getFragmentManager(), xFragment, titles);
        mViewPager.setAdapter(xFragmentPagerAdapter);
        mViewPager.setOffscreenPageLimit(xFragment.size());
        mTabLayout.setupWithViewPager(mViewPager);
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
    }
}
