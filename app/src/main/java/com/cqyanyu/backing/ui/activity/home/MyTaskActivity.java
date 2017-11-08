package com.cqyanyu.backing.ui.activity.home;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.base.BaseActivity;
import com.cqyanyu.backing.ui.mvpview.home.MyTaskView;
import com.cqyanyu.backing.ui.presenter.home.MyTaskPresenter;
import com.cqyanyu.mvpframework.view.recyclerView.XRecyclerView;
import com.xys.libzxing.zxing.activity.CaptureActivity;

/**
 * 我的任务
 * Created by Administrator on 2017/7/10.
 */
public class MyTaskActivity extends BaseActivity<MyTaskPresenter> implements MyTaskView {

    private XRecyclerView recyclerView;
    private ImageView ivNotData;
    private TextView tvNotData;
    private LinearLayout layoutNotData;
    //    private Spinner spinSelector;
    private String type = "1";
    private String taskId;

    @Override
    protected MyTaskPresenter createPresenter() {
        return new MyTaskPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_my_task;
    }

    @Override
    protected void initView() {
        recyclerView = (XRecyclerView) findViewById(R.id.recycler_view);
        ivNotData = (ImageView) findViewById(R.id.iv_not_data);
        tvNotData = (TextView) findViewById(R.id.tv_not_data);
        layoutNotData = (LinearLayout) findViewById(R.id.layout_not_data);
//        spinSelector = (Spinner) findViewById(R.id.spin_time);
    }

//    /**
//     * 下拉选择列表
//     */
//    private void setSpinner() {
//        ArrayList<TypeEntity> list = new ArrayList<>();
//        TypeEntity month = new TypeEntity();
//        month.setContent("月");
//        month.setType(2);
//        TypeEntity date = new TypeEntity();
//        date.setContent("日");
//        date.setType(1);
//        list.add(month);
//        list.add(date);
//        //为下拉列表定义一个适配器
//        final ArrayAdapter<TypeEntity> adapter = new ArrayAdapter<>(mContext, R.layout.item_spinner, list);
//        //为适配器设置下拉列表下拉时的菜单样式。
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        //将适配器添加到下拉列表上
//        spinSelector.setAdapter(adapter);
//        spinSelector.setSelection(1, true);
//        spinSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                type = "" + adapter.getItem(position).getType();
//                mPresenter.refresh();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        if (mPresenter != null) mPresenter.initPresenter();
//        setSpinner();
    }

    @Override
    public XRecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public void hasShowData(boolean has) {
        layoutNotData.setVisibility(has ? View.GONE : View.VISIBLE);
        if (!has)
            tvNotData.setText("无任务");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x112 && resultCode == RESULT_OK) {
            if (data != null) {
                String result = data.getStringExtra("result");
                startActivity(new Intent(mContext, SetDetailsActivity.class)
                        .putExtra(SetDetailsActivity.KEY_CONTENT, result)
                        .putExtra(SetDetailsActivity.KEY_TASK_ID, taskId)
                        .putExtra("label", MyTaskActivity.class.getSimpleName())
                );
            }
        }
    }

    /**
     * 前往扫一扫页面
     *
     * @param taskId         任务id
     * @param inspectionRate 巡检率
     */
    public void gotoScan(String taskId, float inspectionRate) {
        this.taskId = taskId;
        startActivityForResult(new Intent(mContext, CaptureActivity.class)
                        .putExtra("progress", inspectionRate)
                , 0x112);
    }

    @Override
    public String getType() {
        return type;
    }
}
