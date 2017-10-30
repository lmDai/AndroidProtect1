package com.cqyanyu.backing.ui.activity.home;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.base.BaseActivity;
import com.cqyanyu.backing.ui.mvpview.home.SetDetailsView;
import com.cqyanyu.backing.ui.presenter.home.SetDetailsPresenter;
import com.cqyanyu.backing.ui.widget.app.ProblemRecycler;
import com.cqyanyu.backing.ui.widget.picture.PictureSelect;
import com.cqyanyu.backing.utils.DialogUtils;
import com.cqyanyu.mvpframework.view.ItemOptionView;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备详情
 * Created by Administrator on 2017/7/18.
 */
public class SetDetailsActivity extends BaseActivity<SetDetailsPresenter> implements SetDetailsView {

    public static final String KEY_CONTENT = "扫描结果";
    public static final String KEY_TASK_ID = "任务id";
    public static final String LABEL = "label";

    private ItemOptionView iovNumber;
    private ItemOptionView iovType;
    private ItemOptionView iovState;
    private ItemOptionView iovPosition;
    private EditText etDescribe;
    private PictureSelect pictureSelect;
    private Button btnCommit;
    private ProblemRecycler problemRecycler;
    private LinearLayout llDescribe;
    private String content;
    private boolean isScan = false;
    private TextView txtResult;

    @Override
    protected SetDetailsPresenter createPresenter() {
        return new SetDetailsPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_set_details;
    }

    @Override
    protected void initView() {
        iovNumber = (ItemOptionView) findViewById(R.id.iov_number);
        iovType = (ItemOptionView) findViewById(R.id.iov_type);
        iovState = (ItemOptionView) findViewById(R.id.iov_state);
        iovPosition = (ItemOptionView) findViewById(R.id.iov_position);
        etDescribe = (EditText) findViewById(R.id.et_describe);
        btnCommit = (Button) findViewById(R.id.btn_commit);
        txtResult = (TextView) findViewById(R.id.txt_result);
        pictureSelect = (PictureSelect) findViewById(R.id.picture_select);
        problemRecycler = (ProblemRecycler) findViewById(R.id.problem_recycler);
        llDescribe = (LinearLayout) findViewById(R.id.ll_describe);
    }

    @Override
    protected void initListener() {
        btnCommit.setOnClickListener(this);
        problemRecycler.setOnMyClickListener(new ProblemRecycler.OnMyClickListener() {
            @Override
            public void onClick(String id, String name) {
                setIsProblem(!TextUtils.equals(name, "正常"));
            }
        });
    }

    @Override
    protected void initData() {
        pictureSelect.setRootURL(ConstHost.IMAGE);
        if (mPresenter != null) mPresenter.init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_commit:
                if (mPresenter != null) mPresenter.commit();
                break;
        }
    }

    @Override
    public String getScanResult() {
        if (!isScan) content = getIntent().getStringExtra(KEY_CONTENT);
        return content;
    }

    @Override
    public String getTaskIds() {
        return getIntent().getStringExtra(KEY_TASK_ID);
    }

    @Override
    public void setProblemList(List<String> listName, List<String> listId) {
        problemRecycler.setDataList(listName, listId);
    }

    @Override
    public void onTask() {
        DialogUtils.getCommonDialog(mContext, "提醒", "此任务没有该设备！请重试", "", "", new DialogUtils.OnDialogOperationListener() {
            @Override
            public void onDialogOperation(DialogUtils.Operation operation) {
                finish();
            }
        }).show();
    }

    @Override
    public void setCode(String code) {
        iovNumber.setContent(code);
    }

    @Override
    public void setType(String typeId) {
        iovType.setContentHint(typeId);
        iovType.setContent(typeId);
    }

    @Override
    public void setInspectionState(String state) {
        iovState.setContent(state);
    }

    @Override
    public void setPosition(String position) {
        iovPosition.setContent(position);
    }

    @Override
    public String getDescribe() {
        return etDescribe.getText().toString();
    }

    @Override
    public String getProblemId() {
        return problemRecycler.getSelectId();
    }

    @Override
    public boolean getIsProblem() {
        return pictureSelect.getVisibility() == View.VISIBLE;
    }

    @Override
    public void setIsProblem(boolean b) {
        //problemRecycler.setVisibility(b ? View.VISIBLE : View.GONE);
        if (!b) {
            pictureSelect.clearData();
        }
        pictureSelect.setVisibility(b ? View.VISIBLE : View.GONE);
        llDescribe.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    @Override
    public List<String> getPictureList() {
        return pictureSelect.getSelectList();
    }

    @Override
    public void gotoScan() {
        /**
         * 前往二维码页面
         */
        if (mPresenter != null) mPresenter.getInspectionRate();
    }

    @Override
    public void setAllInspectionRate(float myInspectionRate) {
        txtResult.setVisibility(View.GONE);
        problemRecycler.clearData();
        pictureSelect.clearData();
        startActivityForResult(new Intent(mContext, CaptureActivity.class)
                        .putExtra("progress", myInspectionRate)
                , 0x112);
    }

    @Override
    public void setEnable(boolean b) {
        if (!b) {
            btnCommit.setBackgroundColor(mContext.getResources().getColor(R.color.color_hint));
        }
        btnCommit.setEnabled(b);
    }

    @Override
    public void setPicture(String picpath) {
        List<String> mList = new ArrayList<>();
        if (!TextUtils.isEmpty(picpath) || !TextUtils.equals("", picpath)) {
            String[] split = picpath.split(";");
            for (int i = 0; i < split.length; i++) {
                if (!TextUtils.isEmpty(split[i])) {
                    mList.add(split[i]);
                }
            }
            pictureSelect.setList(mList);
        } else {
            pictureSelect.setVisibility(View.GONE);
        }
    }

    @Override
    public void setDescibe(String s) {
        if (TextUtils.isEmpty(s)) {
            llDescribe.setVisibility(View.GONE);
        } else {
            etDescribe.setText(s);
        }
    }

    @Override
    public String getLable() {
        return getIntent().getStringExtra("label");
    }

    @Override
    public void setResult(String statusStr) {
        txtResult.setVisibility(View.VISIBLE);
        txtResult.setText(statusStr);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x112 && resultCode == RESULT_OK) {
            if (data != null) {
                isScan = true;
                content = data.getStringExtra("result");
                mPresenter.init();
            }
        }
    }
}
