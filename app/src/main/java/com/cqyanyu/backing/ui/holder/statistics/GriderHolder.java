package com.cqyanyu.backing.ui.holder.statistics;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.entity.home.UserManageEntity;
import com.cqyanyu.backing.ui.widget.app.XTextView;
import com.cqyanyu.mvpframework.view.recyclerView.IViewHolder;
import com.cqyanyu.mvpframework.view.recyclerView.XViewHolder;

/**
 * 网格员
 * Created by Administrator on 2017/7/12.
 */

public class GriderHolder extends IViewHolder {

    @Override
    protected XViewHolder create(View view, RecyclerView.Adapter adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public int getLayout() {
        return R.layout.item_grider;
    }

    private class ViewHolder extends XViewHolder<UserManageEntity> {
        XTextView tvUserUnit;
        TextView tvUserType;
        TextView tvUserName;
        TextView tvUserTel;
        TextView tvNum;

        ViewHolder(View itemView, RecyclerView.Adapter adapter) {
            super(itemView, adapter);
        }

        /**
         * 初始化
         *
         * @param rootView
         */
        @Override
        protected void initView(View rootView) {
            tvUserUnit = (XTextView) rootView.findViewById(R.id.tv_user_unit);//单位名称
            tvUserType = (TextView) rootView.findViewById(R.id.tv_user_type);//用户角色
            tvUserName = (TextView) rootView.findViewById(R.id.tv_user_name);//用户名称
            tvUserTel = (TextView) rootView.findViewById(R.id.tv_user_tel);//用户电话
            tvNum = (TextView) rootView.findViewById(R.id.tv_num);
        }

        /**
         * 绑定数据
         *
         * @param itemData
         */
        @Override
        protected void onBindData(UserManageEntity itemData) {
            /** 设置所属单位*/
            tvUserUnit.setVisibility(View.VISIBLE);
            tvUserUnit.setXText(itemData.getUnitstr());
            tvNum.setText(getAdapterPosition() + 1 + "");
            /** 设置用户角色*/
            tvUserType.setText(itemData.getRolestr());

            /** 设置用户名*/
            tvUserName.setText(itemData.getUserstr());

            /**设置联系电话*/
            tvUserTel.setText(itemData.getPhone());
        }

        @Override
        public void onClick(View v) {

        }
    }
}
