package com.cqyanyu.backing.ui.holder.statistics;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.entity.home.SetManageEntity;
import com.cqyanyu.mvpframework.view.recyclerView.IViewHolder;
import com.cqyanyu.mvpframework.view.recyclerView.XViewHolder;

/**
 * 消防设施
 * Created by Administrator on 2017/7/12.
 */

public class FireFacilitiesHolder extends IViewHolder {
    @Override
    protected XViewHolder create(View view, RecyclerView.Adapter adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public int getLayout() {
        return R.layout.item_fire_facilities;
    }

    private class ViewHolder extends XViewHolder<SetManageEntity> {
        TextView tvSetName;
        TextView tvSetUnit;
        TextView tvSetPosition;
        TextView tvSetState;
        TextView tvNum;

        ViewHolder(View itemView, RecyclerView.Adapter adapter) {
            super(itemView, adapter);
        }

        @Override
        protected void initView(View rootView) {
            tvSetName = (TextView) rootView.findViewById(R.id.tv_set_name);
            tvSetUnit = (TextView) rootView.findViewById(R.id.tv_set_unit);
            tvSetPosition = (TextView) rootView.findViewById(R.id.tv_set_position);
            tvSetState = (TextView) rootView.findViewById(R.id.tv_set_state);
            tvNum = (TextView) rootView.findViewById(R.id.tv_regulatory_num);
        }

        @Override
        protected void onBindData(SetManageEntity itemData) {
            /**TODO 设置设备名称*/
            tvSetName.setText(itemData.getTypestr() + "\t" + itemData.getSn());

            /**TODO 设置所属单位*/
            tvSetUnit.setText(itemData.getUnitstr());

            /** 设置位置*/
            tvSetPosition.setText(itemData.getPosition() + "");

            /** 设置状态*/
//            if (TextUtils.equals(itemData.getStatusid(), "0")) {
//                tvSetState.setText("正常");
//            } else {
//                tvSetState.setText("不正常");
//            }
            tvSetState.setText(itemData.getStatusstr());

            /** 设置item数*/
            tvNum.setText("" + (getAdapterPosition() + 1) + "");
        }

        @Override
        public void onClick(View v) {

        }
    }
}
