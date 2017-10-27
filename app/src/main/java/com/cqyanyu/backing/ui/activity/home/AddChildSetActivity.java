package com.cqyanyu.backing.ui.activity.home;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.manger.InfoManger;
import com.cqyanyu.backing.ui.activity.base.BaseActivity;
import com.cqyanyu.backing.ui.entity.home.EntryEntity;
import com.cqyanyu.backing.ui.entity.home.MapChoiceBean;
import com.cqyanyu.backing.ui.mvpview.home.AddChildSetView;
import com.cqyanyu.backing.ui.presenter.home.AddChildSetPresenter;
import com.cqyanyu.backing.ui.widget.picture.PictureSelect;
import com.cqyanyu.backing.utils.SharedPreferencesUtils;
import com.cqyanyu.mvpframework.view.ItemOptionView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 增加子设备
 * Created by Administrator on 2017/7/11.
 */
public class AddChildSetActivity extends BaseActivity<AddChildSetPresenter> implements AddChildSetView {
    public static final String LABEL = "label";
    public static final String LABEL_VALUE_ADD_CHILD = "增加子设备";
    public static final String LABEL_VALUE_EDIT_CHILD = "编辑子设备";
    private ItemOptionView iovSetType;
    private EditText etSetNum;
    private EditText etSetPosition;
    private ImageView ivLocal;
    private EditText etMaxValue;
    private EditText etMinValue;
    private EditText etSetFloor;
    private PictureSelect pictureSelect;
    private LinearLayout layoutContent;
    private String buildId;
    private String unitId;
    private double latitude;
    private double longitude;

    @Override
    protected AddChildSetPresenter createPresenter() {
        return new AddChildSetPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_add_child_set;
    }

    @Override
    protected void initView() {
        iovSetType = (ItemOptionView) findViewById(R.id.iov_set_type);
        etSetNum = (EditText) findViewById(R.id.et_set_num);
        etSetPosition = (EditText) findViewById(R.id.et_set_position);
        ivLocal = (ImageView) findViewById(R.id.iv_local);
        etMaxValue = (EditText) findViewById(R.id.et_max_value);
        etMinValue = (EditText) findViewById(R.id.et_min_value);
        etSetFloor = (EditText) findViewById(R.id.et_set_floor);
        pictureSelect = (PictureSelect) findViewById(R.id.picture_select);
        layoutContent = (LinearLayout) findViewById(R.id.layout_content);
    }

    @Override
    protected void initListener() {
        setTopTitle(getLabel());
        iovSetType.setOnClickListener(this);
        ivLocal.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        /**设置图片显示的根地址*/
        pictureSelect.setRootURL(ConstHost.IMAGE);
        if (mPresenter != null) mPresenter.init();
        //设置单位id
        unitId = getIntent().getStringExtra("unitId");
        //设置建筑id
        buildId = getIntent().getStringExtra("buildId");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right://完成
                if (mPresenter != null) mPresenter.commitPicture();
                break;
            case R.id.iov_set_type:
                //设备类型
                startActivity(new Intent(mContext, EntryActivity.class)
                        .putExtra(EntryActivity.LABEL, InfoManger.KEY_ENTRY_SET_TYPE)
                        .putExtra(EntryActivity.KEY_OID, "32")
                );
                break;
            case R.id.iv_local:
                //定位
                if (isLocal()) {
                    startActivity(new Intent(mContext, ChoicePositionActivity.class)
                            .putExtra("latitude", getLatitude())
                            .putExtra("longitude", getLongitude())
                            .putExtra("position", getPosition()));
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        //返回位置信息/
        MapChoiceBean eventPosition = EventBus.getDefault().getStickyEvent(MapChoiceBean.class);
        if (eventPosition != null) {
            setPosition(eventPosition.getDescribe());
            setLan(eventPosition.getLatitude(), eventPosition.getLongitude());
        }
        EventBus.getDefault().removeStickyEvent(MapChoiceBean.class);
        //返回词条信息
        EntryEntity eventEntry = EventBus.getDefault().getStickyEvent(EntryEntity.class);
        if (eventEntry != null) {
            switch (eventEntry.result) {
                case InfoManger.KEY_ENTRY_SET_TYPE:
                    //设备类型id
                    setType(eventEntry.getOid());
                    break;
            }
        }
        EventBus.getDefault().removeStickyEvent(EntryEntity.class);
        super.onResume();
    }

    @Override
    public void setTextRight(TextView textRight) {
        textRight.setText(R.string.text_finish);
        textRight.setOnClickListener(this);
    }

    @Override
    public String getLabel() {
        return getIntent().getStringExtra(LABEL);
    }

    @Override
    public String getOid() {
        return getIntent().getStringExtra("oid");
    }

    @Override
    public void setLan(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String getUnitId() {
        return unitId;
    }

    @Override
    public String getBuild() {
        return buildId;
    }

    @Override
    public String getType() {
        return getHint(iovSetType);
    }

    @Override
    public void setType(String typeId) {
        iovSetType.setContentHint(typeId);
        iovSetType.setContent(InfoManger.getInstance().getEntryName(mContext, typeId));
    }

    @Override
    public String getNum() {
        return etSetNum.getText().toString();
    }

    @Override
    public void setNum(String num) {
        etSetNum.setText(num);
    }

    @Override
    public String getPosition() {
        return etSetPosition.getText().toString();
    }

    @Override
    public void setPosition(String position) {
        etSetPosition.setText(position);
    }

    @Override
    public String getFloor() {
        return etSetFloor.getText().toString();
    }

    @Override
    public void setFloor(String floor) {
        etSetFloor.setText(floor);
    }

    @Override
    public String getMaxValue() {
        return etMaxValue.getText().toString();
    }

    @Override
    public void setMaxValue(String maxValue) {
        etMaxValue.setText(maxValue);
    }

    @Override
    public String getMinValue() {
        return etMinValue.getText().toString();
    }

    @Override
    public void setMinValue(String minValue) {
        etMinValue.setText(minValue);
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }

    @Override
    public List<String> getPictureList() {
        return pictureSelect.getSelectList();
    }

    /**
     * 获取ItemOptionView里的提示内容
     *
     * @param view ItemOptionView
     * @return 提示内容
     */
    private String getHint(ItemOptionView view) {
        CharSequence hint = ((TextView) view.findViewById(R.id.content)).getHint();
        return hint != null ? hint.toString() : "";
    }

    /**
     * 获取ItemOptionView里的文本内容
     *
     * @param view ItemOptionView
     * @return 文本内容
     */
    private String getText(ItemOptionView view) {
        CharSequence text = ((TextView) view.findViewById(R.id.content)).getText();
        return text != null ? text.toString() : "";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (TextUtils.equals(getLabel(), AddChildSetActivity.LABEL_VALUE_ADD_CHILD)) {
            SharedPreferencesUtils.setParam(mContext, "childType", getType(), SharedPreferencesUtils.FILE_NAME_SET);//设备类型
            SharedPreferencesUtils.setParam(mContext, "childNum", getNum(), SharedPreferencesUtils.FILE_NAME_SET);//设备编号
            SharedPreferencesUtils.setParam(mContext, "childPosition", getPosition(), SharedPreferencesUtils.FILE_NAME_SET);//详细地址
            SharedPreferencesUtils.setParam(mContext, "maxValue", getMaxValue(), SharedPreferencesUtils.FILE_NAME_SET);//最大值
            SharedPreferencesUtils.setParam(mContext, "minValue", getMinValue(), SharedPreferencesUtils.FILE_NAME_SET);//最小值
            SharedPreferencesUtils.setParam(mContext, "childFloor", getFloor(), SharedPreferencesUtils.FILE_NAME_SET);//楼层
        }
    }
}
