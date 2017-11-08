package com.cqyanyu.backing.ui.activity.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
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
import com.cqyanyu.backing.ui.mvpview.home.SearchView;
import com.cqyanyu.backing.ui.presenter.home.SearchPresenter;
import com.cqyanyu.backing.ui.widget.app.ClearableEditText;
import com.cqyanyu.mvpframework.utils.PermissionUtil;
import com.cqyanyu.mvpframework.view.recyclerView.XRecyclerView;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchView {
    private XRecyclerView recyclerView;
    private ImageView ivNotData, imgSearch;
    private TextView tvNotData;
    private LinearLayout layoutNotData;
    private ClearableEditText edtSearch;
    private static final String LABEL = "label";
    private ImageView imgScan;
    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA
    };
    private final int REQ_SCAN = 0x123;
    private boolean FirstClick = false;

    @Override
    protected SearchPresenter createPresenter() {
        return new SearchPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        recyclerView = (XRecyclerView) findViewById(R.id.recycler_view);
        ivNotData = (ImageView) findViewById(R.id.iv_not_data);
        tvNotData = (TextView) findViewById(R.id.tv_not_data);
        imgSearch = (ImageView) findViewById(R.id.img_search);
        layoutNotData = (LinearLayout) findViewById(R.id.layout_not_data);
        edtSearch = (ClearableEditText) findViewById(R.id.edt_search);
        imgScan = (ImageView) findViewById(R.id.img_saoyisao);
        if (TextUtils.equals(getLabel(), SystemManagementActivity.LABEL_VALUE_SEARCH_SET)) {
            imgScan.setVisibility(View.VISIBLE);
        } else {
            imgScan.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initListener() {
        setTopTitle(getLabel());
        imgScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PermissionUtil.initPermission(mContext, PERMISSIONS)) {
                    startActivityForResult(new Intent(mContext, CaptureActivity.class), REQ_SCAN);
                }
            }
        });
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
     *
     */
    public void search() {
        if (FirstClick) {//判断是否第一次点击搜索
            mPresenter.refresh();
        } else {
            mPresenter.initPresenter();
        }
        ((InputMethodManager) edtSearch.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                mContext.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    protected void initData() {
        switch (getLabel()) {
            case SystemManagementActivity.LABEL_VALUE_SEARCH_UNIT:
                //单位管理
                edtSearch.setHint("请输入单位名称");
                break;
            case SystemManagementActivity.LABEL_VALUE_SEARCH_SET:
                //设备管理
                edtSearch.setHint("请输入设备名称");
                break;
            case SystemManagementActivity.LABEL_VALUE_SEARCH_USER:
                //用户管理
                edtSearch.setHint("请输入用户名称");
                break;
        }
        edtSearch.setHintTextColor(R.color.color_hint);
    }

    @Override
    public String getLabel() {
        return getIntent().getStringExtra(LABEL);
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
    public XRecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public void hasShowData(boolean has) {
        FirstClick = true;
        layoutNotData.setVisibility(has ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        FirstClick = false;
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String result = data.getStringExtra("result");
                edtSearch.setText(result);
                if (FirstClick) {//判断是否第一次点击搜索
                    mPresenter.refresh();
                } else {
                    mPresenter.initPresenter();
                }
            }
        }
    }

}
