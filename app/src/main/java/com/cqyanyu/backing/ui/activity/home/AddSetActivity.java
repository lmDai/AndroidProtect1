package com.cqyanyu.backing.ui.activity.home;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.listener.OnSelectListener;
import com.cqyanyu.backing.manger.InfoManger;
import com.cqyanyu.backing.ui.activity.base.BaseActivity;
import com.cqyanyu.backing.ui.entity.home.EntryEntity;
import com.cqyanyu.backing.ui.entity.home.ListBean;
import com.cqyanyu.backing.ui.entity.home.MapChoiceBean;
import com.cqyanyu.backing.ui.entity.home.UnitEntity;
import com.cqyanyu.backing.ui.mvpview.home.AddSetView;
import com.cqyanyu.backing.ui.presenter.home.AddSetPresenter;
import com.cqyanyu.backing.ui.widget.picture.PictureSelect;
import com.cqyanyu.backing.utils.DialogUtils;
import com.cqyanyu.backing.utils.MyDate;
import com.cqyanyu.backing.utils.SharedPreferencesUtils;
import com.cqyanyu.backing.utils.Utils;
import com.cqyanyu.mvpframework.utils.XToastUtil;
import com.cqyanyu.mvpframework.view.ItemOptionView;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 增加设备
 * Created by Administrator on 2017/7/11.
 */
public class AddSetActivity extends BaseActivity<AddSetPresenter> implements AddSetView, View.OnFocusChangeListener {
    public static final String LABEL = "label";
    public static final String LABEL_VALUE_ADD = "增加设备";
    private static final String LABEL_VALUE_ADD_CHILD = "增加子设备";
    public static final String LABEL_VALUE_EDIT = "编辑设备";
    public static final String LABEL_VALUE_EDIT_CHILD = "编辑子设备";
    private static final int REQ_CODE = 0x012;
    private ItemOptionView iovSetBuild;
    private ItemOptionView iovSetType;
    private EditText editNum;
    private ImageView ivScan;
    private EditText etSetPosition;
    private ImageView ivLocal;
    private AutoCompleteTextView etProductCompany;
    private AutoCompleteTextView etBrand;
    private ItemOptionView iovGuarantee;
    private ItemOptionView iovInspectionWay;
    private ItemOptionView iovInspectionUnit;
    private AutoCompleteTextView etSetSize;
    private AutoCompleteTextView etSetFloor;
    private ItemOptionView iovSetStartData;
    private PictureSelect pictureSelect;
    private Dialog dialogEndData;
    private Dialog dialogStartData;
    private double latitude;
    private double longitude;
    private String unitId;
    private LinearLayout layoutContent;
    private ScrollView scrollView;
    private View view_blank;
    private MenuItem menuItem;

    @Override
    protected AddSetPresenter createPresenter() {
        return new AddSetPresenter();
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
            if (InfoManger.getInstance().isPermission("68")) {//删除设备
                if (mPresenter != null) mPresenter.deleteSet();
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
        return R.layout.activity_add_set;
    }

    @Override
    protected void initView() {
        view_blank = (View) findViewById(R.id.view);
        iovSetBuild = (ItemOptionView) findViewById(R.id.iov_set_build);
        iovSetType = (ItemOptionView) findViewById(R.id.iov_set_type);
        editNum = (EditText) findViewById(R.id.edit_num);
        ivScan = (ImageView) findViewById(R.id.iv_scan);
        etSetPosition = (EditText) findViewById(R.id.et_set_position);
        ivLocal = (ImageView) findViewById(R.id.iv_local);
        etProductCompany = (AutoCompleteTextView) findViewById(R.id.et_product_company);
        etBrand = (AutoCompleteTextView) findViewById(R.id.et_brand);
        iovGuarantee = (ItemOptionView) findViewById(R.id.iov_guarantee);
        iovInspectionWay = (ItemOptionView) findViewById(R.id.iov_inspection_way);
        iovInspectionUnit = (ItemOptionView) findViewById(R.id.iov_inspection_unit);
        etSetSize = (AutoCompleteTextView) findViewById(R.id.et_set_size);
        etSetFloor = (AutoCompleteTextView) findViewById(R.id.et_set_floor);
        iovSetStartData = (ItemOptionView) findViewById(R.id.iov_set_start_data);
        pictureSelect = (PictureSelect) findViewById(R.id.picture_select);
        layoutContent = (LinearLayout) findViewById(R.id.layout_content);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
    }

    @Override
    protected void initListener() {
        setTopTitle(getLabel());
        iovSetBuild.setOnClickListener(this);
        iovSetType.setOnClickListener(this);
        iovGuarantee.setOnClickListener(this);
        iovInspectionWay.setOnClickListener(this);
        iovInspectionUnit.setOnClickListener(this);
        iovSetStartData.setOnClickListener(this);
        ivLocal.setOnClickListener(this);
        ivScan.setOnClickListener(this);
        /**添加子设备不能选择所属建筑*/
        if (TextUtils.equals(getLabel(), LABEL_VALUE_ADD_CHILD)) {
            iovSetBuild.setVisibility(View.GONE);
        }
        editNum.setOnFocusChangeListener(this);
        etSetPosition.setOnFocusChangeListener(this);
        etProductCompany.setOnFocusChangeListener(this);
        etBrand.setOnFocusChangeListener(this);
        etSetFloor.setOnFocusChangeListener(this);
        etSetSize.setOnFocusChangeListener(this);
    }

    @Override
    protected void initData() {
        /**设置图片显示的根地址*/
        pictureSelect.setRootURL(ConstHost.IMAGE);
        if (mPresenter != null) mPresenter.init();
        dialogStartData = DialogUtils.getDateDialog(this, "", new OnSelectListener() {
            @Override
            public void onSelect(String date) {
                setStartData(date);
            }
        });
        dialogEndData = DialogUtils.getDateDialog(this, "", new OnSelectListener() {
            @Override
            public void onSelect(String date) {
                setGuarantee(date);
            }
        });
        ViewGroup.LayoutParams params = view_blank.getLayoutParams();
        params.height = Utils.getBottomStatusHeight(mContext);
        view_blank.setLayoutParams(params);
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
            case R.id.iov_set_build:
                //所属单位
                startActivity(new Intent(mContext, ProvinceActivity.class)
                        .putExtra(ProvinceActivity.LABEL, ProvinceActivity.LABEL_VALUE_BUILD)
                        .putExtra(ProvinceActivity.KEY_PID, CommonInfo.getInstance().getUserInfo().getUnitid())
                        .putExtra("isFirstIn", true)
                );
                break;
            case R.id.iov_set_type:
                //设备类型
                startActivity(new Intent(mContext, EntryActivity.class)
                        .putExtra(EntryActivity.LABEL, InfoManger.KEY_ENTRY_SET_TYPE)
                        .putExtra(EntryActivity.KEY_OID, InfoManger.getInstance().getEntryPid(InfoManger.KEY_ENTRY_SET_TYPE))
                );
                break;
            case R.id.iv_scan:
                //设备编号
                startActivityForResult(new Intent(mContext, CaptureActivity.class)
                        .putExtra("progress", 0f), REQ_CODE);
                break;
            case R.id.iov_guarantee:
                //保质期
                if (dialogEndData != null && !dialogEndData.isShowing()) {
                    dialogEndData.show();
                }
                break;
            case R.id.iov_inspection_way:
                //巡检方式
                List<ListBean> lList = InfoManger.getInstance().getListBeanFromEntryOfKey(mContext, InfoManger.KEY_ENTRY_INSPECTION_WAY);
                if (lList != null) {
                    DialogUtils.getListDialog(this, "巡检方式", getResources().getColor(R.color.color_white),
                            getResources().getColor(R.color.colorDark), lList, new DialogUtils.OnDialogChoiceListener() {
                                @Override
                                public void onDialogChoice(int position, String choice) {
                                    setInspectionWay(choice);
                                }
                            }).show();
                }
                break;
            case R.id.iov_inspection_unit:
                //巡检单位
                setInspectionUnit("");
                setInspectionUnitName("");
                startActivity(new Intent(mContext, EntryActivity.class)
                        .putExtra(EntryActivity.LABEL, InfoManger.KEY_ENTRY_INSPECTION_UNIT)
                        .putExtra(EntryActivity.KEY_OID, InfoManger.getInstance().getEntryPid(InfoManger.KEY_ENTRY_INSPECTION_UNIT))
                );
                break;
            case R.id.iov_set_start_data:
                //开始使用日期
                if (dialogStartData != null && !dialogStartData.isShowing()) {
                    dialogStartData.show();
                }
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
        //所属建筑id
        UnitEntity eventPresent = EventBus.getDefault().getStickyEvent(UnitEntity.class);
        if (eventPresent != null) {
            unitId = eventPresent.getPid();
            iovSetBuild.setContentHint(eventPresent.getOid());
            iovSetBuild.setContent(eventPresent.getUnitstr());
        }
        EventBus.getDefault().removeStickyEvent(UnitEntity.class);
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
                    setType(eventEntry.getOid(), eventEntry.getName());
                    break;
                case InfoManger.KEY_ENTRY_INSPECTION_UNIT:
                    //设备类型id
                    setInspectionUnit(eventEntry.getOid());
                    setInspectionUnitName(eventEntry.getName());
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

    @Override
    public void setStartData(String data) {
        iovSetStartData.setContentHint(data);
        iovSetStartData.setContent(MyDate.getFormatDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()), data));
    }

    @Override
    public String getInspectionUnitStr() {
        return getText(iovInspectionUnit);
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
    public void setLan(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String getUnitId() {
        if (getIntent().getStringExtra("unitId") != null) {//判断当前是编辑页面 还是新增页面
            unitId = getIntent().getStringExtra("unitId");
        }
        return unitId;
    }

    @Override
    public String getStartDate() {
        return getHint(iovSetStartData);
    }

    @Override
    public String getBuild() {
        return getHint(iovSetBuild);
    }

    @Override
    public void setBuild(String buildId, String buildStr) {
        iovSetBuild.setContentHint(buildId);
        iovSetBuild.setContent(buildStr);
    }

    @Override
    public String getType() {
        return getHint(iovSetType);
    }

    @Override
    public void setType(String typeId, String typeStr) {
        iovSetType.setContentHint(typeId);
        iovSetType.setContent(typeStr);
    }

    @Override
    public String getNum() {
        return editNum.getText().toString();
    }

    @Override
    public void setNum(String num) {
        editNum.setText(num != null ? num : "");
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
    public String getFactory() {
        return etProductCompany.getText().toString();
    }

    @Override
    public void setFactory(String factory) {
        etProductCompany.setText(factory);
    }

    @Override
    public String getBrand() {
        return etBrand.getText().toString();
    }

    @Override
    public void setBrand(String brand) {
        etBrand.setText(brand);
    }

    @Override
    public String getGuarantee() {
        return getHint(iovGuarantee);
    }

    @Override
    public void setGuarantee(String guarantee) {
        iovGuarantee.setContentHint(guarantee);
        iovGuarantee.setContent(MyDate.getFormatDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()), guarantee));
    }

    @Override
    public String getInspectionWay() {
        return getHint(iovInspectionWay);
    }

    @Override
    public void setInspectionWay(String inspection) {
        iovInspectionWay.setContentHint(inspection);
        iovInspectionWay.setContent(
                TextUtils.equals(InfoManger.getInstance().getEntryName(mContext, inspection), inspection)
                        ? "未知方式" :
                        InfoManger.getInstance().getEntryName(mContext, inspection));
    }

    @Override
    public String getInspectionUnit() {
        return getHint(iovInspectionUnit);
    }

    @Override
    public void setInspectionUnit(String inspectionId) {
        iovInspectionUnit.setContentHint(inspectionId);
    }

    @Override
    public String getSize() {
        return etSetSize.getText().toString();
    }

    @Override
    public void setSize(String size) {
        etSetSize.setText(size);
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

    @Override
    public void setUnitID(String unitid) {
        this.unitId = unitid;
    }

    @Override
    public void setInspectionUnitName(String unitstr) {
        iovInspectionUnit.setContent(unitstr);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQ_CODE) {
                //设备编号
                String result = data.getStringExtra("result");
                setNum(result);
            }
        }
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

    /**
     * 输入记忆功能
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (TextUtils.equals(getLabel(), AddSetActivity.LABEL_VALUE_ADD)) {
            String slatitude = Double.toString(getLatitude());
            String slongitude = Double.toString(getLongitude());
            SharedPreferencesUtils.setParam(mContext, "longitude", slongitude, SharedPreferencesUtils.FILE_NAME_SET);//经度
            SharedPreferencesUtils.setParam(mContext, "latitude", slatitude, SharedPreferencesUtils.FILE_NAME_SET);//纬度
            SharedPreferencesUtils.setParam(mContext, "unitid", getUnitId(), SharedPreferencesUtils.FILE_NAME_SET);//单位id
            SharedPreferencesUtils.setParam(mContext, "build", getBuild(), SharedPreferencesUtils.FILE_NAME_SET);//所属建筑
            SharedPreferencesUtils.setParam(mContext, "buildStr", getBuildStr(), SharedPreferencesUtils.FILE_NAME_SET);//所属建筑
            SharedPreferencesUtils.setParam(mContext, "type", getType(), SharedPreferencesUtils.FILE_NAME_SET);//设备类型
            SharedPreferencesUtils.setParam(mContext, "typeStr", getTypeStr(), SharedPreferencesUtils.FILE_NAME_SET);//设备类型
            SharedPreferencesUtils.setParam(mContext, "num", getNum(), SharedPreferencesUtils.FILE_NAME_SET);//设备编号
            SharedPreferencesUtils.setParam(mContext, "position", getPosition(), SharedPreferencesUtils.FILE_NAME_SET);//详细地址
            SharedPreferencesUtils.setParam(mContext, "factory", getFactory(), SharedPreferencesUtils.FILE_NAME_SET, MODE_PRIVATE);//厂家
            SharedPreferencesUtils.setParam(mContext, "brand", getBrand(), SharedPreferencesUtils.FILE_NAME_SET, MODE_PRIVATE);//品牌
            SharedPreferencesUtils.setParam(mContext, "guarantee", getGuarantee(), SharedPreferencesUtils.FILE_NAME_SET);//保质期
            SharedPreferencesUtils.setParam(mContext, "inspectionWay", getInspectionWay(), SharedPreferencesUtils.FILE_NAME_SET);//巡检方式
            SharedPreferencesUtils.setParam(mContext, "inspectionUnit", getInspectionUnit(), SharedPreferencesUtils.FILE_NAME_SET);//巡检单位
            SharedPreferencesUtils.setParam(mContext, "inspectionUnitstr", getInspectionUnitStr(), SharedPreferencesUtils.FILE_NAME_SET);//巡检单位名称
            SharedPreferencesUtils.setParam(mContext, "startDate", getStartDate(), SharedPreferencesUtils.FILE_NAME_SET);//开始使用时间
            SharedPreferencesUtils.setParam(mContext, "size", getSize(), SharedPreferencesUtils.FILE_NAME_SET, MODE_PRIVATE);//设备型号
            SharedPreferencesUtils.setParam(mContext, "floor", getFloor(), SharedPreferencesUtils.FILE_NAME_SET, MODE_PRIVATE);//楼层
        }
    }

    public String getBuildStr() {
        return getText(iovSetBuild);
    }

    public String getTypeStr() {
        return getText(iovSetType);
    }

    /**
     * 初始化历史记录，最多显示3项提示，采用sp保存
     */
    int nearHistory = 3;//显示记录条数

    private void initHistory(String history, AutoCompleteTextView view) {
        String[] hisArrays = history.split(",");
        ArrayAdapter<String> hisAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, hisArrays);
        //TODO:这个字符串会越来越长,开发中最好提供清理历史记录的功能
        if (hisArrays.length > nearHistory) {
            String[] newArrays = new String[nearHistory];
            System.arraycopy(hisArrays, 0, newArrays, 0, nearHistory);
            //布局可以自己创建,用android自带的也行
            hisAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, newArrays);
        }
        view.setAdapter(hisAdapter);//这里要传递的adapter参数必须是继承ListAdapter和Filterable的
        hisAdapter.notifyDataSetChanged();
        view.setDropDownHeight(350);//弹出显示列表的高度
        view.setThreshold(0);//当输入一个字符时就开始搜索历史,默认是2个
    }

    /**
     * 获取焦点
     *
     * @param v
     * @param hasFocus
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (TextUtils.equals(getLabel(), LABEL_VALUE_EDIT)) {
            menuItem.setVisible(true);
        }
        if (v.getId() != R.id.et_set_position && v.getId() != R.id.edit_num) {
            AutoCompleteTextView view = (AutoCompleteTextView) v;
            if (hasFocus) {
                String history = null;
                switch (view.getId()) {
                    case R.id.et_product_company:
                        history = (String) SharedPreferencesUtils.getParam(mContext, "factory", ""
                                , SharedPreferencesUtils.FILE_NAME_SET);
                        break;
                    case R.id.et_brand:
                        history = (String) SharedPreferencesUtils.getParam(mContext, "brand", ""
                                , SharedPreferencesUtils.FILE_NAME_SET);
                        break;
                    case R.id.et_set_size:
                        history = (String) SharedPreferencesUtils.getParam(mContext, "size", ""
                                , SharedPreferencesUtils.FILE_NAME_SET);
                        break;
                    case R.id.et_set_floor:
                        history = (String) SharedPreferencesUtils.getParam(mContext, "floor", ""
                                , SharedPreferencesUtils.FILE_NAME_SET);
                        break;
                }
                if (!TextUtils.isEmpty(history)) {//数据不为空时显示
                    initHistory(history, view);
                    view.showDropDown();
                }
            }
        }
    }
}
