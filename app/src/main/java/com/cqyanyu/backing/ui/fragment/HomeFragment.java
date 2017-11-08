package com.cqyanyu.backing.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.manger.InfoManger;
import com.cqyanyu.backing.ui.activity.base.BaseFragment;
import com.cqyanyu.backing.ui.activity.home.CheckRateActivity;
import com.cqyanyu.backing.ui.activity.home.FaultActivity;
import com.cqyanyu.backing.ui.activity.home.FireActivity;
import com.cqyanyu.backing.ui.activity.home.PrefectRateActivity;
import com.cqyanyu.backing.ui.activity.home.TotalSetActivity;
import com.cqyanyu.backing.ui.activity.home.WarningActivity;
import com.cqyanyu.backing.ui.adapter.HomeHolder;
import com.cqyanyu.backing.ui.adapter.base.XBaseAdapter;
import com.cqyanyu.backing.ui.entity.home.HomeBean;
import com.cqyanyu.backing.ui.mvpview.home.HomeView;
import com.cqyanyu.backing.ui.net.XHttpUtils;
import com.cqyanyu.backing.ui.presenter.home.HomePresenter;
import com.cqyanyu.backing.ui.widget.app.XTextView;
import com.cqyanyu.mvpframework.utils.XToastUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

/**
 * 首页
 * Created by Administrator on 2017/6/9 0009.
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements HomeView {
    private Banner banner;
    private XBaseAdapter adapter;
    private XTextView tvTotal;
    private LinearLayout layoutTotal;
    private XTextView tvCheck;
    private LinearLayout layoutCheck;
    private XTextView tvPrefect;
    private LinearLayout layoutPrefect;
    private XTextView tvFire;
    private XTextView tvWarning;
    private XTextView tvFault;
    private GridView gridView;
    private boolean isFirstVisible = true;
    private boolean isVisiBleToUser;

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        banner = (Banner) view.findViewById(R.id.banner);
        tvTotal = (XTextView) view.findViewById(R.id.tv_total);
        layoutTotal = (LinearLayout) view.findViewById(R.id.layout_total);
        tvCheck = (XTextView) view.findViewById(R.id.tv_check);
        layoutCheck = (LinearLayout) view.findViewById(R.id.layout_check);
        tvPrefect = (XTextView) view.findViewById(R.id.tv_prefect);
        layoutPrefect = (LinearLayout) view.findViewById(R.id.layout_prefect);
        gridView = (GridView) view.findViewById(R.id.grid_view);
        tvFire = (XTextView) view.findViewById(R.id.tv_fire);
        tvWarning = (XTextView) view.findViewById(R.id.tv_warning);
        tvFault = (XTextView) view.findViewById(R.id.tv_fault);
    }

    @Override
    protected void initListener() {
        layoutTotal.setOnClickListener(this);
        layoutCheck.setOnClickListener(this);
        layoutPrefect.setOnClickListener(this);
        tvFire.setOnClickListener(this);
        tvWarning.setOnClickListener(this);
        tvFault.setOnClickListener(this);
        adapter = new XBaseAdapter(HomeHolder.class);
        gridView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        if (mPresenter != null) mPresenter.init();
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

    @Override
    public void onResume() {
        if (isVisiBleToUser && !isFirstVisible && mPresenter != null) {
            if (mPresenter != null) mPresenter.refresh();
        }
        super.onResume();
    }

    @Override
    public void setBanner(List<String> mList, List<String> hearList) {
        if (mList != null && mList.size() > 0) {
            banner.setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    /**
                     注意：
                     1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
                     2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
                     传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
                     切记不要胡乱强转！
                     */
                    //Glide 加载图片简单用法
                    String url = (String) path;
                    Glide.with(mContext).load(XHttpUtils.getGlideUrl(CommonInfo.getInstance().getImageUrl(url)))
                            .thumbnail(0.1f)
                            .fitCenter()
                            .placeholder(R.mipmap.zzz)
                            .error(R.mipmap.zzz)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(imageView);
                }
            });
            banner.setImages(mList)//设置图片集合
//                    .setBannerTitles(hearList)
//                    .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)//设置banner样式
                    .setDelayTime(3000) //设置轮播时间
                    .isAutoPlay(true)//设置自动轮播，默认为true
                    .setIndicatorGravity(BannerConfig.RIGHT)//设置指示器位置（当banner模式中有指示器时）
                    .start();
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {

                }
            });
        }
    }

    @Override
    public void setHomeItem(List<HomeBean> mList) {
        if (adapter != null)
            adapter.setList(mList);
    }

    @Override
    public void setTotal(String total) {
        tvTotal.setXText(total);
    }

    @Override
    public void setClickRate(String rate) {
        tvCheck.setXText(rate);
    }

    @Override
    public void setPrefectRate(String rate) {
        tvPrefect.setXText(rate);
    }

    @Override
    public void setFire(String fire) {
        tvFire.setXText(fire);
    }

    @Override
    public void setWarning(String warning) {
        tvWarning.setXText(warning);
    }

    @Override
    public void setFault(String fault) {
        tvFault.setXText(fault);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_total:
                if (InfoManger.getInstance().isPermission("44")) {//设备数
                    startActivity(new Intent(mContext, TotalSetActivity.class));
                } else {
                    XToastUtil.showToast("暂不拥有该权限！");
                }
                break;

            case R.id.layout_check:
                if (InfoManger.getInstance().isPermission("45")) {//巡检率
                    startActivity(new Intent(mContext, CheckRateActivity.class));
                } else {
                    XToastUtil.showToast("暂不拥有该权限！");
                }
                break;

            case R.id.layout_prefect:
                if (InfoManger.getInstance().isPermission("46")) {//完好率
                    startActivity(new Intent(mContext, PrefectRateActivity.class));
                } else {
                    XToastUtil.showToast("暂不拥有该权限！");
                }
                break;
            case R.id.tv_fire:
                if (InfoManger.getInstance().isPermission("47")) {//火警
                    startActivity(new Intent(mContext, FireActivity.class));
                } else {
                    XToastUtil.showToast("暂不拥有该权限！");
                }
                break;

            case R.id.tv_warning:
                if (InfoManger.getInstance().isPermission("48")) {//预警
                    startActivity(new Intent(mContext, WarningActivity.class));
                } else {
                    XToastUtil.showToast("暂不拥有该权限！");
                }
                break;

            case R.id.tv_fault:
                if (InfoManger.getInstance().isPermission("49")) {//故障
                    startActivity(new Intent(mContext, FaultActivity.class));
                } else {
                    XToastUtil.showToast("暂不拥有该权限！");
                }
                break;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.isVisiBleToUser = isVisibleToUser;
        if (isVisibleToUser) {
            if (isFirstVisible) {
                if (mPresenter != null) mPresenter.refresh();
                isFirstVisible = false;
            }
        } else {
            isFirstVisible = true;
            this.isVisiBleToUser = false;
        }
        super.setUserVisibleHint(isVisibleToUser);
    }
}
