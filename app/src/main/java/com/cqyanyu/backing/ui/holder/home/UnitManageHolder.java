package com.cqyanyu.backing.ui.holder.home;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.manger.InfoManger;
import com.cqyanyu.backing.ui.activity.home.AddUnitActivity;
import com.cqyanyu.backing.ui.entity.home.UnitManageEntity;
import com.cqyanyu.mvpframework.utils.XToastUtil;
import com.cqyanyu.mvpframework.view.recyclerView.IViewHolder;
import com.cqyanyu.mvpframework.view.recyclerView.XViewHolder;

/**
 * 单位管理item view
 * Created by Administrator on 2017/6/11 0011.
 */
public class UnitManageHolder extends IViewHolder {

    @Override
    protected XViewHolder create(View view, RecyclerView.Adapter adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public int getLayout() {
        return R.layout.item_unit_manage;
    }

    private class ViewHolder extends XViewHolder<UnitManageEntity> {

        TextView tvUnitName;
        TextView tvUnitPosition;
        TextView tvUnitType;
        TextView tvRegulatoryLevel;
        TextView tvNum;

        ViewHolder(View itemView, RecyclerView.Adapter adapter) {
            super(itemView, adapter);
        }

        @Override
        protected void initView(View rootView) {
            tvUnitName = (TextView) rootView.findViewById(R.id.tv_unit_name);
            tvUnitPosition = (TextView) rootView.findViewById(R.id.tv_unit_position);
            tvUnitType = (TextView) rootView.findViewById(R.id.tv_unit_type);
            tvRegulatoryLevel = (TextView) rootView.findViewById(R.id.tv_regulatory_level);
            tvNum = (TextView) rootView.findViewById(R.id.tv_regulatory_num);
        }

        @Override
        protected void onBindData(UnitManageEntity itemData) {

            /**设置单位名称*/
            tvUnitName.setText(itemData.getUnitstr());

            /**设置单位位置*/
            tvUnitPosition.setText(itemData.getPosition());

            /** 设置单位类型*/
            tvUnitType.setText(itemData.getTypestr());

            /** 设置监管等级*/
            tvRegulatoryLevel.setText(itemData.getSelevelstr());

            /** 设置item数*/
            tvNum.setText("" + (getAdapterPosition() + 1) + "");
        }

        @Override
        public void onClick(View v) {
            if (InfoManger.getInstance().isPermission("60")) {
                mContext.startActivity(new Intent(mContext, AddUnitActivity.class)
                        .putExtra(AddUnitActivity.LABEL, AddUnitActivity.LABEL_VALUE_EDIT)
                        .putExtra("oid", itemData.getOid())
                        .putExtra("unit",itemData)
                );
            } else {
                XToastUtil.showToast("暂不拥有编辑权限！");
            }
        }
    }
}
