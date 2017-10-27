package com.cqyanyu.backing.ui.activity.alarm;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.manger.InfoManger;
import com.cqyanyu.backing.ui.activity.base.BaseActivity;
import com.cqyanyu.backing.ui.entity.home.AlarmNHEntity;
import com.cqyanyu.backing.ui.entity.home.WarnNHEntity;
import com.cqyanyu.backing.ui.entity.warn.WarnEntity;
import com.cqyanyu.backing.ui.entity.warn.WarningEntity;
import com.cqyanyu.backing.ui.mvpview.warn.HandleView;
import com.cqyanyu.backing.ui.presenter.alarm.HandlePresenter;
import com.cqyanyu.backing.ui.widget.app.ProblemRecycler;
import com.cqyanyu.backing.ui.widget.app.XTextView;
import com.cqyanyu.backing.utils.MyDate;
import com.cqyanyu.mvpframework.utils.XToastUtil;

import java.io.Serializable;
import java.util.List;

/**
 * 处理告警
 * Created by Administrator on 2017/7/18.
 */
public class HandleActivity extends BaseActivity<HandlePresenter> implements HandleView {
    public static final String LABEL = "label";
    public static final String LABEL_VALUE_FIRE = "火警";
    public static final String LABEL_VALUE_FAULT = "故障";
    public static final String LABEL_VALUE_WARNING = "预警";
    public static final String LABEL_VALUE_OTHER = "其他";
    public static final String KEY_ENTITY = "实体类";

    private int alarmType = 0;//告警处理类型 1：主机 2：水系统
    private String oid = null;
    private String pid = null;
    private String deviceid = null;
    private String buildid = null;
    private String unitid = null;
    private String affairid = null;
    private String reportdate = null;

    private XTextView tvTitle;
    private TextView tvLocal;
    private TextView tvDate;
    private TextView tvFaultType;
    private EditText etRemark;
    private WarnEntity warnEntity;
    private WarningEntity warningEntity;
    private WarnNHEntity mWarnNHEntity;
    private AlarmNHEntity mAlarmNHEntity;
    private ProblemRecycler problemRecycler;

    @Override
    protected HandlePresenter createPresenter() {
        return new HandlePresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_handle;
    }

    @Override
    protected void initView() {
        tvTitle = (XTextView) findViewById(R.id.tv_title);
        tvLocal = (TextView) findViewById(R.id.tv_local);
        tvDate = (TextView) findViewById(R.id.tv_date);
        tvFaultType = (TextView) findViewById(R.id.tv_fault_type);
        etRemark = (EditText) findViewById(R.id.et_remark);
        problemRecycler = (ProblemRecycler) findViewById(R.id.problem_recycler);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        if (mPresenter != null) mPresenter.init();
        /**初始化数据*/
        Serializable serializable = getIntent().getSerializableExtra(KEY_ENTITY);
        if (serializable instanceof WarnEntity) {//主机
            warnEntity = (WarnEntity) serializable;
            alarmType = 1;
            oid = warnEntity.getOid();
            deviceid = warnEntity.getDeviceid();
            buildid = warnEntity.getBuildid();
            unitid = warnEntity.getUnitid();
            affairid = warnEntity.getAffairid() + "";
            reportdate = warnEntity.getReportdate() + "";
            setData(warnEntity);
        } else if (serializable instanceof WarningEntity) {//水系统
            warningEntity = (WarningEntity) serializable;
            alarmType = 2;
            pid = warnEntity.getOid();
            deviceid = warnEntity.getDeviceid();
            buildid = warnEntity.getBuildid();
            unitid = warningEntity.getUnitid();
            //reportdate=warningEntity.la()+"";
            setData(warningEntity);
        } else if (serializable instanceof WarnNHEntity) {//水系统
            mWarnNHEntity = (WarnNHEntity) serializable;
            pid = mWarnNHEntity.getOid();
            deviceid = mWarnNHEntity.getDeviceid();
            buildid = mWarnNHEntity.getBuildid();
            unitid = mWarnNHEntity.getUnitid();
            reportdate = mWarnNHEntity.getReportlastdate() + "";
            alarmType = 2;
            setData(mWarnNHEntity);
        } else if (serializable instanceof AlarmNHEntity) {//主机
            mAlarmNHEntity = (AlarmNHEntity) serializable;
            oid = mAlarmNHEntity.getOid();
            deviceid = mAlarmNHEntity.getDeviceid();
            buildid = mAlarmNHEntity.getBuildid();
            unitid = mAlarmNHEntity.getUnitid();
            affairid = mAlarmNHEntity.getAffairid() + "";
            reportdate = mAlarmNHEntity.getReportdate() + "";
            alarmType = 1;
            setData(mAlarmNHEntity);
        } else {
            XToastUtil.showToast("错误信息！");
            finish();
        }
    }

    @Override
    public void setTextRight(TextView textRight) {
        textRight.setText(R.string.text_finish);
        textRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right:
                if (mPresenter != null) mPresenter.commitHandle();
                break;
        }
    }

    @Override
    public void setProblemList(List<String> listName, List<String> listId) {
        problemRecycler.setDataList(listName, listId);
    }

    @Override
    public String getLabel() {
        return getIntent().getStringExtra(LABEL);
    }

    @Override
    public String getType() {
        return alarmType + "";
    }

    @Override
    public String getOid() {
        return oid;
    }

    @Override
    public String getPid() {
        return pid;
    }

    @Override
    public String getDeviceID() {
        return deviceid;
    }

    @Override
    public String getBuildId() {
        return buildid;
    }

    @Override
    public String getAffairID() {
        return affairid;
    }

    @Override
    public String getDate() {
        return reportdate;
    }

    @Override
    public String getUnitId() {
        return unitid;
    }

    @Override
    public String getHandle() {
        return problemRecycler.getSelectId();
    }

    @Override
    public String getRemark() {
        return etRemark.getText().toString();
    }

    /**
     * 设置数据
     *
     * @param entity 火警、故障、其他
     */
    private void setData(WarnEntity entity) {
        if (entity != null) {
            /**设置标题*/
            tvTitle.setText(entity.getTypestr());
            /**设置地址*/
            tvLocal.setText(entity.getPosition());
            /**设置时间*/
            tvDate.setText(MyDate.getFormatDate(entity.getReportdate()));
            /**TODO 设置故障(图很多)*/
            tvFaultType.setText("故障类型   " + entity.getAffairstr() + "");
            /***/
        }
    }

    /**
     * 设置数据
     *
     * @param entity 火警、故障、其他
     */
    private void setData(AlarmNHEntity entity) {
        if (entity != null) {
            /**设置标题*/
            tvTitle.setText(entity.getTypestr());
            /**设置地址*/
            tvLocal.setText(entity.getPosition());
            /**设置时间*/
            tvDate.setText(MyDate.getFormatDate(entity.getReportdate()));
            /**TODO 设置故障(图很多)*/
            tvFaultType.setText("故障类型   " + entity.getAffairstr() + "");
            /***/
        }
    }

    /**
     * 设置数据
     *
     * @param entity 预警
     */
    private void setData(WarningEntity entity) {
        if (entity != null) {
            /**设置建筑名称*/
            tvTitle.setText(InfoManger.getInstance().getUnitNameOfOid(mContext, entity.getBuildid()));
            /** 设置位置(以后完善)*/
            tvLocal.setText(InfoManger.getInstance().getPositionOfSet(mContext, entity.getDeviceid()));
            /**设置时间*/
            tvDate.setText(MyDate.getFormatDate(entity.getReportdate()));
            /**TODO 设置故障(图很多)*/
            switch (entity.getReporteventid()) {
                case 2:
                    tvFaultType.setText("故障类型   偏低");
                    break;
                case 1:
                    tvFaultType.setText("故障类型   偏高");
                    break;
            }
        }
    }

    /**
     * 设置数据
     *
     * @param entity 预警
     */
    private void setData(WarnNHEntity entity) {
        if (entity != null) {
            /**设置建筑名称*/
            tvTitle.setText(entity.getBuildstr());
            // tvTitle.setText(InfoManger.getInstance().getUnitNameOfOid(mContext, entity.getBuildid()));
            /** 设置位置(以后完善)*/
            tvLocal.setText(entity.getPosition());
            //tvLocal.setText(InfoManger.getInstance().getPositionOfSet(mContext, entity.getDeviceid()));
            /**设置时间*/
            tvDate.setText(MyDate.getFormatDate(entity.getReportlastdate()));
            /**TODO 设置故障(图很多)*/
            switch (entity.getReportlasteeventid()) {
                case 2:
                    tvFaultType.setText("故障类型   偏低");
                    break;
                case 1:
                    tvFaultType.setText("故障类型   偏高");
                    break;
            }
        }
    }

}
