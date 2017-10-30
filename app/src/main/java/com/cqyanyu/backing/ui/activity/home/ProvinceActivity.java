package com.cqyanyu.backing.ui.activity.home;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.base.BaseActivity;
import com.cqyanyu.backing.ui.entity.home.UnitEntity;
import com.cqyanyu.backing.ui.mvpview.home.ProvinceView;
import com.cqyanyu.backing.ui.presenter.home.ProvincePresenter;
import com.cqyanyu.mvpframework.utils.XToastUtil;
import com.cqyanyu.mvpframework.view.recyclerView.XRecyclerView;

import org.greenrobot.eventbus.EventBus;

/**
 * 省市
 * Created by Administrator on 2017/7/12.
 */
public class ProvinceActivity extends BaseActivity<ProvincePresenter> implements ProvinceView {
    public static final String LABEL = "label";
    public static final String LABEL_VALUE_PRESENT = "上级单位";
    public static final String LABEL_VALUE_USER = "所属单位";
    public static final String LABEL_VALUE_BUILD = "所属建筑";
    public static final String KEY_PID = "上级ID";

    private XRecyclerView recyclerView;
    private ImageView ivNotData;
    private TextView tvNotData;
    private LinearLayout layoutNotData;

    @Override
    protected ProvincePresenter createPresenter() {
        return new ProvincePresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_province;
    }

    @Override
    protected void initView() {
        recyclerView = (XRecyclerView) findViewById(R.id.recycler_view);
        ivNotData = (ImageView) findViewById(R.id.iv_not_data);
        tvNotData = (TextView) findViewById(R.id.tv_not_data);
        layoutNotData = (LinearLayout) findViewById(R.id.layout_not_data);
    }

    @Override
    protected void initListener() {
        setTopTitle(getLabel());
    }

    @Override
    protected void initData() {
        if (mPresenter != null) mPresenter.initPresenter();
    }

    @Override
    public XRecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public void hasShowData(boolean has) {
        layoutNotData.setVisibility(has ? View.GONE : View.VISIBLE);
    }

    @Override
    public String getLabel() {
        return getIntent().getStringExtra(LABEL);
    }

    @Override
    public String getPid() {
        return getIntent().getStringExtra(KEY_PID);
    }

    @Override
    public boolean isMustSelectLastGrade() {
        return TextUtils.equals(getLabel(), LABEL_VALUE_BUILD);
    }

    @Override
    public boolean isMineId() {
        return TextUtils.equals(getPid(), CommonInfo.getInstance().getUserInfo().getUnitid());
    }

    @Override
    public boolean getFirstIn() {
        return getIntent().getBooleanExtra("isFirstIn", false);
    }

    @Override
    protected void onResume() {
        /**下级选择完成，便可以取到值*/
        UnitEntity event = EventBus.getDefault().getStickyEvent(UnitEntity.class);
        if (event != null) {
            finish();
        }
        super.onResume();
    }

    /**
     * 前往下级页面
     *
     * @param entity     单位信息
     * @param goNext     是否前往下级页面
     * @param childCount 子单位的总数
     */
    public void gotoNext(UnitEntity entity, boolean goNext, String childCount, String childBuild) {
        if (TextUtils.equals(childCount, "0") && TextUtils.equals(childBuild, "0")) {
            //没有下一级了
            returnUpPage(entity);
        } else {
            if (isMustSelectLastGrade()) {
                goNextPage(entity);
            } else {
                if (goNext) {
                    goNextPage(entity);
                } else {
                    returnUpPage(entity);
                }
            }
        }
    }

    /**
     * 返回上级页面
     *
     * @param entity 单位信息
     */
    private void returnUpPage(UnitEntity entity) {
        if (TextUtils.equals(getLabel(), LABEL_VALUE_BUILD)) {
            //判断只有是楼宇的时候才能被选中
            if (!TextUtils.equals(entity.getTypeid(), "12")) {
                XToastUtil.showToast("请选择楼宇建筑类型!");
                return;
            }
        } else if (TextUtils.equals(getLabel(), LABEL_VALUE_USER) || TextUtils.equals(getLabel(), LABEL_VALUE_PRESENT)) {
            if (TextUtils.equals(entity.getTypeid(), "12")) {
                XToastUtil.showToast("请选择非楼宇类型!");
                return;
            }
        }
        EventBus.getDefault().postSticky(entity);
        finish();
    }

    /**
     * 前往下级页面
     *
     * @param entity 单位信息
     */
    private void goNextPage(UnitEntity entity) {
        startActivity(new Intent(mContext, ProvinceActivity.class)
                .putExtra(ProvinceActivity.LABEL, getLabel())
                .putExtra(KEY_PID, entity.getOid())
                .putExtra("isFirstIn", false)
        );
    }
}
