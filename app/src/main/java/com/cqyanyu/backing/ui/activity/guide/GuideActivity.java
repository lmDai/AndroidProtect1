package com.cqyanyu.backing.ui.activity.guide;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.widget.CircleIndicator;
import com.cqyanyu.mvpframework.activity.base.XBaseActivity;
import com.cqyanyu.mvpframework.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * 首次启动引导页
 */

public class GuideActivity extends XBaseActivity {

    private List<ImageView> imageViews;
    private ViewPager viewPager;
    private TextView textJoinIn;
    private GuideAdapter adapter;
    private CircleIndicator circleIndicator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // StatusBarUtil.setStatusBarColor(this, R.color.transparent);
    }

    @Override
    protected BasePresenter createPresenter() {
        return new BasePresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initView() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        textJoinIn = (TextView) findViewById(R.id.text_join_in);
        circleIndicator = (CircleIndicator) findViewById(R.id.circle_indicator);
    }

    @Override
    protected void initListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == imageViews.size() - 1) {
                    textJoinIn.setVisibility(View.VISIBLE);
                } else {
                    textJoinIn.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initData() {
        imageViews = new ArrayList<>();
//        imageViews.add(getPageImage(R.mipmap.guide1));
//        imageViews.add(getPageImage(R.mipmap.guide2));
//        imageViews.add(getPageImage(R.mipmap.guide3));
//        imageViews.add(getPageImage(R.mipmap.guide4));

        adapter = new GuideAdapter(imageViews);
        viewPager.setAdapter(adapter);
        circleIndicator.setViewPager(viewPager);
        adapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
    }

    /**
     * 获取引导页的内容
     *
     * @param resId 资源id
     * @return ImageView
     */
    private ImageView getPageImage(int resId) {
        ImageView imageGuide = new ImageView(this);
        imageGuide.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageGuide.setImageResource(resId);
        imageGuide.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageGuide;
    }

}
