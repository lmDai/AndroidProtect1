package com.cqyanyu.backing.ui.activity.home;

import android.content.Intent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.manger.InfoManger;
import com.cqyanyu.backing.ui.activity.base.BaseActivity;
import com.cqyanyu.backing.ui.entity.home.EntryEntity;
import com.cqyanyu.backing.ui.entity.home.UnitEntity;
import com.cqyanyu.backing.ui.mvpview.home.AddUserView;
import com.cqyanyu.backing.ui.presenter.home.AddUserPresenter;
import com.cqyanyu.backing.utils.SharedPreferencesUtils;
import com.cqyanyu.mvpframework.utils.XToastUtil;
import com.cqyanyu.mvpframework.view.ItemOptionView;

import org.greenrobot.eventbus.EventBus;

/**
 * 增加用户
 * Created by Administrator on 2017/7/11.
 */
public class AddUserActivity extends BaseActivity<AddUserPresenter> implements AddUserView {

    public static final String LABEL = "label";
    public static final String LABEL_VALUE_ADD = "增加用户";
    public static final String LABEL_VALUE_EDIT = "编辑用户";

    private ItemOptionView iovPresentUnit;
    private ItemOptionView iovUserSort;
    private EditText etUsername;
    private EditText etTelephone;

    @Override
    protected AddUserPresenter createPresenter() {
        return new AddUserPresenter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (TextUtils.equals(getLabel(), LABEL_VALUE_EDIT)) {
            getMenuInflater().inflate(R.menu.menu_opreation, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_delete) {
            if (InfoManger.getInstance().isPermission("87")) {
                if (mPresenter != null) mPresenter.deleteUserInfo();
            } else {
                XToastUtil.showToast("暂不拥有该权限！");
            }
            return true;
        } else if (item.getItemId() == R.id.menu_finish) {
            if (mPresenter != null) mPresenter.modifyUserInfo();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_add_user;
    }

    @Override
    protected void initView() {
        iovPresentUnit = (ItemOptionView) findViewById(R.id.iov_present_unit);
        iovUserSort = (ItemOptionView) findViewById(R.id.iov_user_sort);
        etUsername = (EditText) findViewById(R.id.et_username);
        etTelephone = (EditText) findViewById(R.id.et_telephone);
    }

    @Override
    protected void initListener() {
        setTopTitle(getLabel());
        iovPresentUnit.setOnClickListener(this);
        iovUserSort.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        if (mPresenter != null) mPresenter.init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right://完成
                if (mPresenter != null) mPresenter.addUserInfo();
                break;
            case R.id.iov_present_unit://所属单位
                startActivity(new Intent(mContext, ProvinceActivity.class)
                        .putExtra(ProvinceActivity.LABEL, ProvinceActivity.LABEL_VALUE_USER)
                        .putExtra(ProvinceActivity.KEY_PID, CommonInfo.getInstance().getUserInfo().getUnitid())
                );
                break;
            case R.id.iov_user_sort://用户角色
                startActivity(new Intent(mContext, EntryActivity.class)
                        .putExtra(EntryActivity.LABEL, InfoManger.KEY_ENTRY_ROLE)
                        .putExtra(EntryActivity.KEY_OID, InfoManger.getInstance().getEntryPid(InfoManger.KEY_ENTRY_ROLE))
                );
                break;
        }
    }

    @Override
    public void setTextRight(TextView textRight) {
        if (TextUtils.equals(getLabel(), LABEL_VALUE_ADD)) {
            textRight.setText(R.string.text_finish);
            textRight.setOnClickListener(this);
        }
    }

    @Override
    protected void onResume() {
        //返回单位信息
        UnitEntity event = EventBus.getDefault().getStickyEvent(UnitEntity.class);
        if (event != null) {
            iovPresentUnit.setContentHint(event.getOid());
            iovPresentUnit.setContent(event.getUnitstr());
        }
        EventBus.getDefault().removeStickyEvent(UnitEntity.class);
        //返回词条信息
        EntryEntity eventEntry = EventBus.getDefault().getStickyEvent(EntryEntity.class);
        if (eventEntry != null) {
            switch (eventEntry.result) {
                case InfoManger.KEY_ENTRY_ROLE:
                    //用户角色
                    setRole(eventEntry.getOid());
                    break;
            }
        }
        EventBus.getDefault().removeStickyEvent(EntryEntity.class);
        super.onResume();
    }

    @Override
    public String getLabel() {
        return getIntent().getStringExtra(LABEL);
    }

    @Override
    public String getNameUP() {
        return getIntent().getStringExtra("name");
    }

    @Override
    public String getPhoneUP() {
        return getIntent().getStringExtra("phone");
    }

    @Override
    public void setUnit(String unitId, String unitstr) {
        iovPresentUnit.setContentHint(unitId);
        iovPresentUnit.setContent(unitstr);
    }

    @Override
    public String getUnitStr() {
        CharSequence hint = ((TextView) iovPresentUnit.findViewById(R.id.content)).getText();
        if (hint != null) {
            return hint.toString();
        } else {
            return null;
        }
    }

    @Override
    public String getUnit() {
        CharSequence hint = ((TextView) iovPresentUnit.findViewById(R.id.content)).getHint();
        if (hint != null) {
            return hint.toString();
        } else {
            return null;
        }
    }

    @Override
    public String getRole() {
        CharSequence hint = ((TextView) iovUserSort.findViewById(R.id.content)).getHint();
        if (hint != null) {
            return hint.toString();
        } else {
            return null;
        }
    }

    @Override
    public void setRole(String role) {
        iovUserSort.setContentHint(role);
        iovUserSort.setContent(InfoManger.getInstance().getEntryName(mContext, role));
    }

    @Override
    public String getUsername() {
        return etUsername.getText().toString();
    }

    @Override
    public void setUsername(String name) {
        etUsername.setText(name);
    }

    @Override
    public String getTelephone() {
        return etTelephone.getText().toString();
    }

    @Override
    public void setTelephone(String phone) {
        etTelephone.setText(phone);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (TextUtils.equals(getLabel(), AddUserActivity.LABEL_VALUE_ADD)) {
            SharedPreferencesUtils.setParam(mContext, "userUnit", getUnit(), SharedPreferencesUtils.FILE_NAME_USER);//所属单位
            SharedPreferencesUtils.setParam(mContext, "unitstr", getUnitStr(), SharedPreferencesUtils.FILE_NAME_USER);//所属单位
            SharedPreferencesUtils.setParam(mContext, "userRole", getRole(), SharedPreferencesUtils.FILE_NAME_USER);//用户角色
            SharedPreferencesUtils.setParam(mContext, "userName", getUsername(), SharedPreferencesUtils.FILE_NAME_USER);//用户名
            SharedPreferencesUtils.setParam(mContext, "linkPhone", getTelephone(), SharedPreferencesUtils.FILE_NAME_USER);//联系电话
        }
    }
}
