package com.cqyanyu.backing.ui.holder.statistics;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.entity.home.UnitManageEntity;
import com.cqyanyu.backing.ui.widget.app.XTextView;
import com.cqyanyu.mvpframework.view.recyclerView.IViewHolder;
import com.cqyanyu.mvpframework.view.recyclerView.XViewHolder;

/**
 * 网格统计
 * Created by Administrator on 2017/7/12.
 */

public class GridSatisticsHolder extends IViewHolder {

    @Override
    protected XViewHolder create(View view, RecyclerView.Adapter adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public int getLayout() {
        return R.layout.item_grid_statistics;
    }

    private class ViewHolder extends XViewHolder<UnitManageEntity> {
        XTextView tvUnitName;
        XTextView tvUnitPosition;
        XTextView tvUnitType;
        XTextView tvUnitLevel;
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
            tvUnitName = (XTextView) rootView.findViewById(R.id.tv_unit_name);//单位名称
            tvUnitPosition = (XTextView) rootView.findViewById(R.id.tv_unit_position);//位置
            tvUnitType = (XTextView) rootView.findViewById(R.id.tv_unit_type);//单位类型
            tvUnitLevel = (XTextView) rootView.findViewById(R.id.tv_unit_level);//监管等级
            tvNum = (TextView) rootView.findViewById(R.id.tv_num);
        }

        /**
         * 绑定数据
         *
         * @param itemData
         */
        @Override
        protected void onBindData(UnitManageEntity itemData) {
            tvNum.setText(getAdapterPosition() + 1 + "");//设置序列号
            /**设置单位名称*/
            tvUnitName.setText(itemData.getUnitstr());

            /**设置单位位置*/
            tvUnitPosition.setText(itemData.getPosition());

            /** 设置单位类型*/
            tvUnitType.setText(itemData.getTypestr());

            /** 设置监管等级*/
            tvUnitLevel.setText(itemData.getSelevelstr());
        }

        @Override
        public void onClick(View v) {

        }
    }
}
