package com.cqyanyu.backing.ui.activity.home;

import android.content.Intent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.manger.InfoManger;
import com.cqyanyu.backing.ui.activity.base.BaseActivity;
import com.cqyanyu.backing.ui.entity.home.EntryEntity;
import com.cqyanyu.backing.ui.entity.home.MapChoiceBean;
import com.cqyanyu.backing.ui.entity.home.UnitEntity;
import com.cqyanyu.backing.ui.entity.home.UnitManageEntity;
import com.cqyanyu.backing.ui.mvpview.home.AddUnitView;
import com.cqyanyu.backing.ui.presenter.home.AddUnitPresenter;
import com.cqyanyu.backing.ui.widget.picture.PictureSelect;
import com.cqyanyu.backing.utils.SharedPreferencesUtils;
import com.cqyanyu.mvpframework.utils.XToastUtil;
import com.cqyanyu.mvpframework.view.ItemOptionView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static com.cqyanyu.backing.R.id.iov_unit_sort;


/**
 * 增加单位
 * Created by Administrator on 2017/7/11.
 */
public class AddUnitActivity extends BaseActivity<AddUnitPresenter> implements AddUnitView, View.OnFocusChangeListener {
    public static final String LABEL = "label";
    public static final String LABEL_VALUE_ADD = "增加单位";
    public static final String LABEL_VALUE_EDIT = "编辑单位";

    private ItemOptionView iovPresentUnit;
    private ItemOptionView iovRegulatoryLevel;
    private ItemOptionView iovUnitSort;
    private EditText etUnitName;
    private EditText etUnitPosition;
    private ImageView ivLocal;
    private EditText etContact;
    private EditText etTel;
    private double latitude;
    private double longitude;
    private PictureSelect pictureSelect;
    private boolean childIsNull;
    private UnitManageEntity unit;
    private MenuItem menuItem;


    @Override
    protected AddUnitPresenter createPresenter() {
        return new AddUnitPresenter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (TextUtils.equals(getLabel(), LABEL_VALUE_EDIT)) {
            getMenuInflater().inflate(R.menu.menu_opreation, menu);
            menuItem = menu.findItem(R.id.menu_finish);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_delete) {
            if (InfoManger.getInstance().isPermission("62")) {//删除单位
                if (mPresenter != null) mPresenter.deleteUnit();
            } else {
                XToastUtil.showToast("暂不拥有该权限！");
            }
            return true;
        } else if (item.getItemId() == R.id.menu_finish) {
            if (mPresenter != null) mPresenter.commitPicture();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_add_unit;
    }

    @Override
    protected void initView() {
        iovPresentUnit = (ItemOptionView) findViewById(R.id.iov_present_unit);
        iovRegulatoryLevel = (ItemOptionView) findViewById(R.id.iov_regulatory_level);
        iovUnitSort = (ItemOptionView) findViewById(iov_unit_sort);
        etUnitName = (EditText) findViewById(R.id.et_unit_name);
        etContact = (EditText) findViewById(R.id.et_contact);
        etTel = (EditText) findViewById(R.id.et_tel);
        etUnitPosition = (EditText) findViewById(R.id.et_unit_position);
        pictureSelect = (PictureSelect) findViewById(R.id.picture_select);
        ivLocal = (ImageView) findViewById(R.id.iv_local);
    }

    @Override
    protected void initListener() {
        setTopTitle(getLabel());
        iovPresentUnit.setOnClickListener(this);
        iovRegulatoryLevel.setOnClickListener(this);
        iovUnitSort.setOnClickListener(this);
        ivLocal.setOnClickListener(this);
        etContact.setOnFocusChangeListener(this);
        etUnitPosition.setOnFocusChangeListener(this);
        etTel.setOnFocusChangeListener(this);
        etUnitName.setOnFocusChangeListener(this);
    }

    @Override
    protected void initData() {
        if (TextUtils.equals(getLabel(), AddUnitActivity.LABEL_VALUE_EDIT)) {//当前为编辑页面
            unit = (UnitManageEntity) getIntent().getSerializableExtra("unit");
            setPresentUnit(unit.getPid(), unit.getPunitstr());//上级单位
            setRegulatoryLevel(unit.getSelevelid(), unit.getSelevelstr());//监管等级
            setUnitSort(unit.getTypeid(), unit.getTypestr());//单位类型
            setUnitName(unit.getUnitstr());//单位名称
            setUnitPosition(unit.getPosition());//单位位置
            setContact(unit.getLinkman());//联系人
            setContactTel(unit.getLinkphone());//联系电话
            setLan(unit.getLatitude(), unit.getLongitude());//经纬度
            setPicture(unit.getPicpath());//图片地址
            if (unit.getChilddevicecount() > 0) {
                setIsNull(false);
            } else {
                setIsNull(true);
            }
        }
        pictureSelect.setRootURL(ConstHost.IMAGE);
        if (mPresenter != null) mPresenter.init();
    }

    @Override
    public void onClick(View v) {
        if (TextUtils.equals(getLabel(), LABEL_VALUE_EDIT)) {
            menuItem.setVisible(true);
        }
        switch (v.getId()) {
            case R.id.btn_right://完成
                if (mPresenter != null) mPresenter.commitPicture();
                break;
            case R.id.iov_present_unit://上级单位
                startActivity(new Intent(mContext, ProvinceActivity.class)
                        .putExtra(ProvinceActivity.LABEL, ProvinceActivity.LABEL_VALUE_PRESENT)
                        .putExtra(ProvinceActivity.KEY_PID, CommonInfo.getInstance().getUserInfo().getUnitid())
                        .putExtra("isFirstIn", true)
                );
                break;
            case R.id.iv_local://定位
                if (isLocal()) {
                    startActivity(new Intent(mContext, ChoicePositionActivity.class)
                            .putExtra("latitude", getLatitude())
                            .putExtra("longitude", getLongitude())
                            .putExtra("position", getUnitPosition()));
                }
                break;
            case R.id.iov_regulatory_level://监督等级
//                List<ListBean> lList = InfoManger.getInstance().getListBeanFromEntryOfKey(mContext, InfoManger.KEY_ENTRY_REGULATORY);
//                if (lList != null) {
//                    DialogUtils.getListDialog(this, "监管等级", getResources().getColor(R.color.color_white),
//                            getResources().getColor(R.color.colorDark), lList, new DialogUtils.OnDialogChoiceListener() {
//                                @Override
//                                public void onDialogChoice(int position, String choice) {
//                                    setRegulatoryLevel(choice);
//                                }
//                            }).show();
//                }
                startActivity(new Intent(mContext, EntryActivity.class)
                        .putExtra(EntryActivity.LABEL, InfoManger.KEY_ENTRY_REGULATORY)
                        .putExtra(EntryActivity.KEY_OID, InfoManger.getInstance().getEntryPid(InfoManger.KEY_ENTRY_REGULATORY))
                );
                break;
            case iov_unit_sort://楼宇和其他类型间不能修改
//                List<ListBean> sList = InfoManger.getInstance().getListBeanFromEntryOfKey(mContext, InfoManger.KEY_ENTRY_UNIT_TYPE);
//                if (sList != null) {
//                    DialogUtils.getListDialog(this, "单位类型", getResources().getColor(R.color.color_white),
//                            getResources().getColor(R.color.colorDark), sList, new DialogUtils.OnDialogChoiceListener() {
//                                @Override
//                                public void onDialogChoice(int position, String choice) {
//                                    setUnitSort(choice);
//                                }
//                            }).show();
//                }
//                if (!childIsNull && TextUtils.equals(getLabel(), LABEL_VALUE_EDIT)) {
//                    XToastUtil.showToast("该单位存在设备，暂不能修改类型");
//                } else {
                    startActivity(new Intent(mContext, EntryActivity.class)
                            .putExtra(EntryActivity.LABEL, InfoManger.KEY_ENTRY_UNIT_TYPE)
                            .putExtra(EntryActivity.KEY_OID, InfoManger.getInstance().getEntryPid(InfoManger.KEY_ENTRY_UNIT_TYPE))
                    );
//                }
                break;
        }
    }

    @Override
    protected void onResume() {
        //返回上级单位信息
        UnitEntity eventPresent = EventBus.getDefault().getStickyEvent(UnitEntity.class);
        if (eventPresent != null) {
            iovPresentUnit.setContentHint(eventPresent.getOid());
            iovPresentUnit.setContent(eventPresent.getUnitstr());
        }
        EventBus.getDefault().removeStickyEvent(UnitEntity.class);
        //返回位置信息
        MapChoiceBean eventPosition = EventBus.getDefault().getStickyEvent(MapChoiceBean.class);
        if (eventPosition != null) {
            etUnitPosition.setText(eventPosition.getDescribe());
            latitude = eventPosition.getLatitude();
            longitude = eventPosition.getLongitude();
        }
        EventBus.getDefault().removeStickyEvent(MapChoiceBean.class);
        //返回词条信息
        EntryEntity eventEntry = EventBus.getDefault().getStickyEvent(EntryEntity.class);
        if (eventEntry != null) {
            switch (eventEntry.result) {
                case InfoManger.KEY_ENTRY_REGULATORY:
                    //监督等级
                    setRegulatoryLevel(eventEntry.getOid(), eventEntry.getName());
                    break;
                case InfoManger.KEY_ENTRY_UNIT_TYPE:
                    //单位类型
                    setUnitSort(eventEntry.getOid(), eventEntry.getName());
                    break;
            }
        }
        EventBus.getDefault().removeStickyEvent(EntryEntity.class);
        super.onResume();
    }

    @Override
    public void setTextRight(TextView textRight) {
        if (TextUtils.equals(getLabel(), LABEL_VALUE_ADD)) {
            textRight.setText(R.string.text_finish);
            textRight.setOnClickListener(this);
        }
    }

    @Override
    public String getLabel() {
        return getIntent().getStringExtra(LABEL);
    }

    @Override
    public String getOid() {
        return getIntent().getStringExtra("oid");
    }

    public void setUnitSort(String sort, String typeStr) {
        iovUnitSort.setContentHint(sort);
        iovUnitSort.setContent(typeStr);
    }

    @Override
    public void setLan(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String getPresentUnit() {
        CharSequence hint = ((TextView) iovPresentUnit.findViewById(R.id.content)).getHint();
        if (hint != null) {
            return hint.toString();
        } else {
            return null;
        }
    }

    @Override
    public void setPresentUnit(String unitId, String pUnitStr) {
        iovPresentUnit.setContentHint(unitId);
        iovPresentUnit.setContent(pUnitStr);
    }

    @Override
    public String getRegulatoryLevel() {
        CharSequence hint = ((TextView) iovRegulatoryLevel.findViewById(R.id.content)).getHint();
        if (hint != null) {
            return hint.toString();
        } else {
            return null;
        }
    }

    @Override
    public void setRegulatoryLevel(String level, String regulatory) {
        iovRegulatoryLevel.setContentHint(level);
        iovRegulatoryLevel.setContent(regulatory);
    }

    @Override
    public String getUnitName() {
        return etUnitName.getText().toString();
    }

    @Override
    public void setUnitName(String name) {
        etUnitName.setText(name);
    }

    @Override
    public String getUnitType() {
        CharSequence hint = ((TextView) iovUnitSort.findViewById(R.id.content)).getHint();
        if (hint != null) {
            return hint.toString();
        } else {
            return null;
        }
    }

    @Override
    public String getUnitPosition() {
        return etUnitPosition.getText().toString();
    }

    @Override
    public void setUnitPosition(String position) {
        etUnitPosition.setText(position);
    }

    @Override
    public String getContact() {
        return etContact.getText().toString();
    }

    @Override
    public void setContact(String contact) {
        etContact.setText(contact);
    }

    @Override
    public String getContactTel() {
        return etTel.getText().toString();
    }

    @Override
    public void setContactTel(String tel) {
        etTel.setText(tel);
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
    public void setIsNull(boolean b) {
        this.childIsNull = b;
    }

    @Override
    public void setPicture(String picture) {
        if (!TextUtils.isEmpty(picture)) {
            String[] split = picture.split(";");
            List<String> mList = new ArrayList<>();
            for (int i = 0; i < split.length; i++) {
                if (!TextUtils.isEmpty(split[i])) {
                    mList.add(split[i]);
                }
            }
            pictureSelect.setList(mList);
        }
    }

    @Override
    public List<String> getPictureList() {
        return pictureSelect.getSelectList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (TextUtils.equals(getLabel(), AddUnitActivity.LABEL_VALUE_ADD)) {
            String slatitude = Double.toString(getLatitude());
            String slongitude = Double.toString(getLongitude());
            SharedPreferencesUtils.setParam(mContext, "longitude", slongitude, SharedPreferencesUtils.FILE_NAME_UNIT);//经度
            SharedPreferencesUtils.setParam(mContext, "latitude", slatitude, SharedPreferencesUtils.FILE_NAME_UNIT);//纬度
            SharedPreferencesUtils.setParam(mContext, "presentUnit", getPresentUnit(), SharedPreferencesUtils.FILE_NAME_UNIT);
            SharedPreferencesUtils.setParam(mContext, "pUnitStr", getPUnitStr(), SharedPreferencesUtils.FILE_NAME_UNIT);
            SharedPreferencesUtils.setParam(mContext, "seleveStr", getSeleveStr(), SharedPreferencesUtils.FILE_NAME_UNIT);
            SharedPreferencesUtils.setParam(mContext, "regulatoryLevel", getRegulatoryLevel(), SharedPreferencesUtils.FILE_NAME_UNIT);
            SharedPreferencesUtils.setParam(mContext, "unitType", getUnitType(), SharedPreferencesUtils.FILE_NAME_UNIT);
            SharedPreferencesUtils.setParam(mContext, "typeStr", getTypeStr(), SharedPreferencesUtils.FILE_NAME_UNIT);
            SharedPreferencesUtils.setParam(mContext, "unitPosition", getUnitPosition(), SharedPreferencesUtils.FILE_NAME_UNIT);
            SharedPreferencesUtils.setParam(mContext, "unitName", getUnitName(), SharedPreferencesUtils.FILE_NAME_UNIT);
            SharedPreferencesUtils.setParam(mContext, "userName", getContact(), SharedPreferencesUtils.FILE_NAME_UNIT);
            SharedPreferencesUtils.setParam(mContext, "linkPhone", getContactTel(), SharedPreferencesUtils.FILE_NAME_UNIT);
        }
    }

    private String getTypeStr() {
        CharSequence hint = ((TextView) iovUnitSort.findViewById(R.id.content)).getText();
        if (hint != null) {
            return hint.toString();
        } else {
            return null;
        }
    }

    private String getSeleveStr() {
        CharSequence hint = ((TextView) iovRegulatoryLevel.findViewById(R.id.content)).getText();
        if (hint != null) {
            return hint.toString();
        } else {
            return null;
        }
    }

    public String getPUnitStr() {
        CharSequence hint = ((TextView) iovPresentUnit.findViewById(R.id.content)).getText();
        if (hint != null) {
            return hint.toString();
        } else {
            return null;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (TextUtils.equals(getLabel(), LABEL_VALUE_EDIT)) {
            menuItem.setVisible(true);
        }
    }
}
