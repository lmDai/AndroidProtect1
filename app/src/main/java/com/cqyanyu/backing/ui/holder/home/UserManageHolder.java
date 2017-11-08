package com.cqyanyu.backing.ui.holder.home;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.manger.InfoManger;
import com.cqyanyu.backing.ui.activity.home.AddUserActivity;
import com.cqyanyu.backing.ui.entity.home.UserManageEntity;
import com.cqyanyu.mvpframework.utils.XToastUtil;
import com.cqyanyu.mvpframework.view.recyclerView.IViewHolder;
import com.cqyanyu.mvpframework.view.recyclerView.XViewHolder;

/**
 * 用户管理item view
 * Created by Administrator on 2017/6/11 0011.
 */
public class UserManageHolder extends IViewHolder {

    @Override
    protected XViewHolder create(View view, RecyclerView.Adapter adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public int getLayout() {
        return R.layout.item_user_manage;
    }

    private class ViewHolder extends XViewHolder<UserManageEntity> {

        TextView tvUserUnit;
        TextView tvUserType;
        TextView tvUserName;
        TextView tvUserTel;
        TextView tvNum;

        ViewHolder(View itemView, RecyclerView.Adapter adapter) {
            super(itemView, adapter);
        }

        @Override
        protected void initView(View rootView) {
            tvUserUnit = (TextView) rootView.findViewById(R.id.tv_user_unit);
            tvUserType = (TextView) rootView.findViewById(R.id.tv_user_type);
            tvUserName = (TextView) rootView.findViewById(R.id.tv_user_name);
            tvUserTel = (TextView) rootView.findViewById(R.id.tv_user_tel);
            tvNum = (TextView) rootView.findViewById(R.id.tv_regulatory_num);
        }

        @Override
        protected void onBindData(UserManageEntity itemData) {
            /** 设置所属单位*/
            tvUserUnit.setText(itemData.getUnitstr());

            /** 设置用户角色*/
            tvUserType.setText(itemData.getRolestr());

            /** 设置用户名*/
            tvUserName.setText(itemData.getUserstr());

            /**设置联系电话*/
            tvUserTel.setText(itemData.getPhone());

            /** 设置item数*/
            tvNum.setText("" + (getAdapterPosition() + 1) + "");
        }

        @Override
        public void onClick(View v) {
            if (InfoManger.getInstance().isPermission("64")) {//增加用户
                mContext.startActivity(new Intent(mContext, AddUserActivity.class)
                        .putExtra(AddUserActivity.LABEL, AddUserActivity.LABEL_VALUE_EDIT)
                        .putExtra("name", itemData.getUserstr())
                        .putExtra("phone", itemData.getPhone())
                );
            } else {
                XToastUtil.showToast("暂不拥有该权限！");
            }
        }
    }
}
