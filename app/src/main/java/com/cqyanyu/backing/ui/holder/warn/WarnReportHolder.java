package com.cqyanyu.backing.ui.holder.warn;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.entity.warn.WaterReportEntity;
import com.cqyanyu.backing.ui.widget.app.XTextView;
import com.cqyanyu.backing.utils.MyDate;
import com.cqyanyu.mvpframework.view.recyclerView.IViewHolder;
import com.cqyanyu.mvpframework.view.recyclerView.XViewHolder;

/**
 * 告警上报详情列表 view
 * Created by Administrator on 2017/6/11 0011.
 */
public class WarnReportHolder extends IViewHolder {

    @Override
    protected XViewHolder create(View view, RecyclerView.Adapter adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public int getLayout() {
        return R.layout.item_warn_report;
    }

    private class ViewHolder extends XViewHolder<WaterReportEntity> {

        TextView tvType;
        TextView tvWaterPress;
        ImageView ivGoNext;
        TextView tvDate;
        XTextView tvLocal;

        ViewHolder(View itemView, RecyclerView.Adapter adapter) {
            super(itemView, adapter);
        }

        @Override
        protected void initView(View rootView) {
            tvType = (TextView) rootView.findViewById(R.id.tv_type);
            tvWaterPress = (TextView) rootView.findViewById(R.id.tv_water_press);
            ivGoNext = (ImageView) rootView.findViewById(R.id.iv_go_next);
            tvDate = (TextView) rootView.findViewById(R.id.tv_date);
            tvLocal = (XTextView) rootView.findViewById(R.id.tv_local);
            tvWaterPress.setOnClickListener(this);
        }

        @Override
        protected void onBindData(WaterReportEntity itemData) {
            /**设置时间*/
            tvDate.setText(MyDate.getFormatDate(itemData.getReportdate()));
            /**设置位置加设置建筑名称*/

            tvLocal.setText(itemData.getAffairstr());
            /**设置是否处理*/
            // setHandle();
            tvLocal.setOnClickListener(this);
        }

        /**
         * 设置是否处理==> 判断是否是首页（如果是首页才有可能显示按钮,不是首页就隐藏按钮）
         */
//        private void setHandle() {
//            /**是否已经处理 0：未处理 1：已处理*/
//            if (mContext instanceof MainActivity) {
//                if (itemData.getIshandle() == 0 && itemData.getReporteventid() != 0) {
//                    ivGoNext.setVisibility(View.VISIBLE);
//                } else {
//                    ivGoNext.setVisibility(View.GONE);
//                }
//            } else {
//                ivGoNext.setVisibility(View.GONE);
//            }
//        }
        @Override
        public void onClick(View v) {

        }
    }
}
