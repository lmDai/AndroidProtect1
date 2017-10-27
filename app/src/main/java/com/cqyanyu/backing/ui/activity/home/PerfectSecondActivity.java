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
import com.cqyanyu.backing.ui.activity.base.BaseActivity;
import com.cqyanyu.backing.ui.mvpview.home.PrefectSecondView;
import com.cqyanyu.backing.ui.presenter.home.PrefectSecondPresenter;
import com.cqyanyu.backing.ui.widget.app.ClearableEditText;
import com.cqyanyu.mvpframework.view.recyclerView.XRecyclerView;

/**
 * 完好率二级
 * Created by Administrator on 2017/7/10.
 */
public class PerfectSecondActivity extends BaseActivity<PrefectSecondPresenter> implements PrefectSecondView {

    private XRecyclerView recyclerView;
    private ImageView ivNotData, imgSearch;
    private TextView tvNotData;
    private LinearLayout layoutNotData;
    private ClearableEditText edtSearch;

    @Override
    protected PrefectSecondPresenter createPresenter() {
        return new PrefectSecondPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_perfect_sencond;
    }

    @Override
    protected void initView() {
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
//        BDLocation bdLocation = EventBus.getDefault().getStickyEvent(BDLocation.class);
//        //修改默认地区
//        if (bdLocation != null) {
//            tvLocal.setText(bdLocation.getSemaAptag());
//        } else {
//            XToastUtil.showToast("请先定位!否则将无法使用该功能");
//            finish();
//        }
        setTopTitle(TextUtils.isEmpty(getIntent().getStringExtra("typeStr")) ? "完好率" : getIntent().getStringExtra("typeStr"));
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
    public String getmCondition() {
        return TextUtils.isEmpty(edtSearch.getText()) ? getIntent().getStringExtra("typeStr") : edtSearch.getText().toString();
    }

    @Override
    public String getmTypeId() {
        return getIntent().getStringExtra("mTypeId");
    }
}
