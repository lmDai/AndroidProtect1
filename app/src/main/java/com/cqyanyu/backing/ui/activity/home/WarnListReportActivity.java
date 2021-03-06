package com.cqyanyu.backing.ui.activity.home;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.base.BaseActivity;
import com.cqyanyu.backing.ui.entity.home.WarnNHEntity;
import com.cqyanyu.backing.ui.mvpview.home.WarnListReportView;
import com.cqyanyu.backing.ui.presenter.home.WarnListReprotPresenter;
import com.cqyanyu.backing.ui.widget.app.XTextView;
import com.cqyanyu.backing.utils.MPChartHelper;
import com.cqyanyu.backing.utils.MyDate;
import com.cqyanyu.backing.utils.NumberUtils;
import com.cqyanyu.mvpframework.utils.XDateUtil;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;
import java.util.List;

/**
 * 预警列表上报详情
 */

public class WarnListReportActivity extends BaseActivity<WarnListReprotPresenter> implements WarnListReportView {
    private TextView txtUnit, txtSn, txtValue, txtType, txtDate;
    private XTextView txtShuiya, txtCount, txtWaterHigh, txtWaterLow;
    private LineChart barChart1;
    private ImageButton ibAdd, ibDel;
    private int clickable = 1;
    private LinearLayout llNoData;
    private WarnNHEntity waterSystemEntity;

    @Override
    protected WarnListReprotPresenter createPresenter() {
        return new WarnListReprotPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_warn_list_report;
    }

    @Override
    protected void initView() {
        txtUnit = (TextView) findViewById(R.id.txt_unit);
        txtSn = (TextView) findViewById(R.id.txt_sn);
        txtValue = (TextView) findViewById(R.id.txt_value);
        txtType = (TextView) findViewById(R.id.txt_type);
        txtDate = (TextView) findViewById(R.id.txt_date);
        txtShuiya = (XTextView) findViewById(R.id.txt_shuiya);
        txtWaterHigh = (XTextView) findViewById(R.id.water_high);
        txtWaterLow = (XTextView) findViewById(R.id.water_low);
        barChart1 = (LineChart) findViewById(R.id.barChart1);
        ibAdd = (ImageButton) findViewById(R.id.ib_add);
        ibDel = (ImageButton) findViewById(R.id.ib_del);
        llNoData = (LinearLayout) findViewById(R.id.layout_not_data);
        waterSystemEntity = (WarnNHEntity) getIntent().getSerializableExtra("item");
        setTxtUnit(waterSystemEntity.getUnitstr());
        setTxtSn(waterSystemEntity.getSn());
        setTxtDate(XDateUtil.getStringByFormatFromStr(String.valueOf(waterSystemEntity.getReportlastdate()), "yyyy-MM-dd"));
        if (TextUtils.equals(waterSystemEntity.getTypeid(), "66")) {
            setTxtValue(NumberUtils.setDecimalFloat((float) waterSystemEntity.getVal() / 1000f) + "Mpa");
        } else {
            setTxtValue(NumberUtils.setDecimalFloat((float) waterSystemEntity.getVal() / 1000f) + "M");
        }
    }

    @Override
    protected void initListener() {
        ibAdd.setOnClickListener(this);
        ibDel.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        if (mPresenter != null) mPresenter.getWaterSystemReport();
    }

    @Override
    public String getPid() {
        return waterSystemEntity.getOid();
    }

    /**
     * 绘制图表
     *
     * @param alarmTotals
     * @param xValues
     */
    @Override
    public void setAlarmTotal(List<Float> alarmTotals, List<String> xValues) {
        barChart1.setVisibility(View.VISIBLE);
        llNoData.setVisibility(View.GONE);
        ibAdd.setEnabled(!XDateUtil.getCurrentDate("yyyy-MM-dd").equals(txtDate.getText().toString()));
        List<String> titles = new ArrayList<>();
        List<List<Float>> yAxisValue = new ArrayList<>();
        titles.add("预警值");
        yAxisValue.add(alarmTotals);
        MPChartHelper.setLinesChart(barChart1, xValues,
                yAxisValue, titles, false, null,
                Float.valueOf(waterSystemEntity.getMaxval()) / 1000.0f, Float.valueOf(waterSystemEntity.getMinval()) / 1000.0f);
    }

    @Override
    public void setTxtUnit(String unit) {
        txtUnit.setText(unit);
    }

    @Override
    public void setTxtSn(String Sn) {
        txtSn.setText(Sn);
    }

    @Override
    public void setTxtValue(String Value) {
        txtValue.setText(Value);
    }

    @Override
    public void setTxtType(String Type) {
        txtValue.setText(Type);
    }

    @Override
    public void setTxtDate(String Date) {
        txtDate.setText(Date);
    }

    @Override
    public void setTxtShuiya(String Shuiya) {
        if (TextUtils.equals(waterSystemEntity.getTypeid(), "66")) {
            txtShuiya.setXText("平均水压：" + Shuiya);
        } else {
            txtShuiya.setXText("平均液位：" + Shuiya);
        }
    }

    @Override
    public void setTxtCount(String Count) {
        txtCount.setXText(Count);
    }

    @Override
    public void setTxtWaterHigh(String WaterHigh) {
        if (TextUtils.equals(waterSystemEntity.getTypeid(), "66")) {
            txtWaterHigh.setXText("水压峰值：" + WaterHigh);
        } else {
            txtWaterHigh.setXText("液位峰值：" + WaterHigh);
        }
    }

    @Override
    public void setTxtWaterLow(String WaterLow) {
        if (TextUtils.equals(waterSystemEntity.getTypeid(), "66")) {
            txtWaterLow.setXText("水压谷值：" + WaterLow);
        } else {
            txtWaterLow.setXText("液位谷值：" + WaterLow);
        }
    }

    @Override
    public String getStartDate() {
        return (MyDate.stringToLong(txtDate.getText().toString()
                , "yyyy-MM-dd") / 1000 - 24 * 60 * 60) + "";
    }

    @Override
    public String getEndDate() {
        return MyDate.stringToLong(txtDate.getText().toString(), "yyyy-MM-dd") / 1000 + "";
    }

    @Override
    public int getClickable() {
        return clickable;
    }

    @Override
    public void setNoData(int clickable) {
        llNoData.setVisibility(View.VISIBLE);
        barChart1.setVisibility(View.GONE);
        ibAdd.setEnabled(!XDateUtil.getCurrentDate("yyyy-MM-dd").equals(txtDate.getText().toString()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_add:
                clickable = 0;
                txtDate.setText(XDateUtil.getStringByFormatFromStr(
                        String.valueOf((MyDate.stringToLong(txtDate.getText().toString()
                                , "yyyy-MM-dd") / 1000 + 24 * 60 * 60))
                        , "yyyy-MM-dd"));
                mPresenter.getWaterSystemReport();
                break;
            case R.id.ib_del:
                clickable = 1;
                txtDate.setText(XDateUtil.getStringByFormatFromStr(
                        String.valueOf((MyDate.stringToLong(txtDate.getText().toString()
                                , "yyyy-MM-dd") / 1000 - 24 * 60 * 60))
                        , "yyyy-MM-dd"));
                mPresenter.getWaterSystemReport();
                break;
        }
    }
}
