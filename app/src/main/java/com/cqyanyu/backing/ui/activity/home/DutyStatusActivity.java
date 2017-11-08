package com.cqyanyu.backing.ui.activity.home;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.event.ItemEvent;
import com.cqyanyu.backing.manger.InfoManger;
import com.cqyanyu.backing.ui.activity.base.BaseActivity;
import com.cqyanyu.backing.ui.mvpview.home.DutyStatusView;
import com.cqyanyu.backing.ui.presenter.home.DutyStatusPresenter;
import com.cqyanyu.backing.ui.widget.app.ClearableEditText;
import com.cqyanyu.mvpframework.utils.XToastUtil;
import com.cqyanyu.mvpframework.view.recyclerView.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 当值状态
 * Created by Administrator on 2017/7/10.
 */
public class DutyStatusActivity extends BaseActivity<DutyStatusPresenter> implements DutyStatusView {

    private XRecyclerView recyclerView;
    private ImageView ivNotData, imgSearch;
    private TextView tvNotData;
    private LinearLayout layoutNotData;
    private ClearableEditText edtSearch;
    private Button btnCheck;
    private Button btnLocal;

    @Override
    protected DutyStatusPresenter createPresenter() {
        return new DutyStatusPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_duty_status;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        recyclerView = (XRecyclerView) findViewById(R.id.recycler_view);
        ivNotData = (ImageView) findViewById(R.id.iv_not_data);
        tvNotData = (TextView) findViewById(R.id.tv_not_data);
        layoutNotData = (LinearLayout) findViewById(R.id.layout_not_data);
        btnCheck = (Button) findViewById(R.id.btn_check);
        btnLocal = (Button) findViewById(R.id.btn_local);
        imgSearch = (ImageView) findViewById(R.id.img_search);
        edtSearch = (ClearableEditText) findViewById(R.id.edt_search);
    }

    @Override
    protected void initListener() {
        btnCheck.setOnClickListener(this);
        btnLocal.setOnClickListener(this);
        edtSearch.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 当按了搜索之后关闭软键盘
                    search();
                    return true;
                }
                return false;
            }
        });
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
    }

    public void search() {
        mPresenter.refresh();
        ((InputMethodManager) edtSearch.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                mContext.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    protected void initData() {
        if (mPresenter != null) mPresenter.initPresenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_check:
                if (InfoManger.getInstance().isPermission("58")) {//查岗
                    startActivity(new Intent(mContext, CheckPostActivity.class));
                } else {
                    XToastUtil.showToast("暂不拥有该权限！");
                }
                break;
            case R.id.btn_local:
                if (InfoManger.getInstance().isPermission("59")) {//定位
                    if (isLocal()) {
                        startActivity(new Intent(mContext, LocalActivity.class));
                    }
                } else {
                    XToastUtil.showToast("暂不拥有该权限！");
                }
                break;
        }
    }

    @Override
    public XRecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public void hasShowData(boolean has) {
        layoutNotData.setVisibility(has ? View.GONE : View.VISIBLE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void itemEvent(ItemEvent itemEvent) {
        if (itemEvent != null) {
            if (itemEvent.getActivity() == ItemEvent.ACTIVITY.DutyStatusActivity) {
                if (itemEvent.getAction() == ItemEvent.ACTION.refreshing) {
                    if (mPresenter != null) mPresenter.refresh();
                }
            }
        }
    }

    @Override
    public String getmCondition() {
        return edtSearch.getText().toString();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


}
