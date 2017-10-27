package com.cqyanyu.backing.ui.activity.home;

import android.content.Context;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.event.ItemEvent;
import com.cqyanyu.backing.ui.activity.base.BaseActivity;
import com.cqyanyu.backing.ui.mvpview.home.ChildDeviceView;
import com.cqyanyu.backing.ui.presenter.home.ChildAlarmDevicePresenter;
import com.cqyanyu.backing.ui.widget.app.ClearableEditText;
import com.cqyanyu.mvpframework.view.recyclerView.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 设备管理
 */
public class ChildAlarmDeviceActivity extends BaseActivity<ChildAlarmDevicePresenter> implements ChildDeviceView {
    private XRecyclerView recyclerView;
    private ImageView ivNotData, imgSearch;
    private TextView tvNotData;
    private LinearLayout layoutNotData;
    private ClearableEditText edtSearch;
    public static final String LABEL = "label";

    @Override
    protected ChildAlarmDevicePresenter createPresenter() {
        return new ChildAlarmDevicePresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_set_manage;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        recyclerView = (XRecyclerView) findViewById(R.id.recycler_view);
        ivNotData = (ImageView) findViewById(R.id.iv_not_data);
        tvNotData = (TextView) findViewById(R.id.tv_not_data);
        layoutNotData = (LinearLayout) findViewById(R.id.layout_not_data);
        edtSearch = (ClearableEditText) findViewById(R.id.edt_search);
        imgSearch = (ImageView) findViewById(R.id.img_search);
    }

    @Override
    protected void initListener() {

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

    /**
     *
     */
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
        setTopTitle(TextUtils.isEmpty(getIntent().getStringExtra("devicetypestr"))
                ? "消防主机列表"
                : getIntent().getStringExtra("devicetypestr"));
    }

    @Override
    public String getLabel() {
        return getIntent().getStringExtra(LABEL);
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
            if (itemEvent.getActivity() == ItemEvent.ACTIVITY.SystemManagementFragment) {
                if (itemEvent.getAction() == ItemEvent.ACTION.refreshing) {
                    if (mPresenter != null) mPresenter.refresh();
                }
            }
        }
    }

    /**
     * 获取输入框的数据
     *
     * @return
     */
    @Override
    public String getSearchText() {
        return edtSearch.getText().toString();
    }

    @Override
    public String getType() {
        return getIntent().getStringExtra("deviceId");
    }

    @Override
    public String getDeviceStr() {
        return getIntent().getStringExtra("devicetypestr");
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
