package com.cqyanyu.backing.ui.holder.warn;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.alarm.PositionActivity;
import com.cqyanyu.backing.ui.activity.home.WarnReportActivity;
import com.cqyanyu.backing.ui.entity.warn.WaterReportEntity;
import com.cqyanyu.backing.utils.MyDate;
import com.cqyanyu.mvpframework.view.recyclerView.IViewHolder;
import com.cqyanyu.mvpframework.view.recyclerView.XViewHolder;

/**
 * 主机上报详情列表 view
 * Created by Administrator on 2017/6/11 0011.
 */
public class HostReportHolder extends IViewHolder {

    @Override
    protected XViewHolder create(View view, RecyclerView.Adapter adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public int getLayout() {
        return R.layout.item_host_report;
    }

    private class ViewHolder extends XViewHolder<WaterReportEntity> {

        TextView tvDate;
        TextView tvAffair, tvNum;

        ViewHolder(View itemView, RecyclerView.Adapter adapter) {
            super(itemView, adapter);
        }

        @Override
        protected void initView(View rootView) {
            tvDate = (TextView) rootView.findViewById(R.id.tv_date);
            tvAffair = (TextView) rootView.findViewById(R.id.tv_affair);
            tvNum = (TextView) rootView.findViewById(R.id.tv_num);
        }

        @Override
        protected void onBindData(WaterReportEntity itemData) {
            tvNum.setText(getAdapterPosition() + 1 + "");
            tvDate.setText(MyDate.getFormatDate(itemData.getReportdate()));
            tvAffair.setText(itemData.getAffairstr());
            tvAffair.setOnClickListener(this);//导航
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_affair:
                    if (itemData.getBuildId() != null) {
                        mContext.startActivity(new Intent(mContext, PositionActivity.class)
                                .putExtra("oid", itemData.getBuildId()));
                    }
                    break;
                case R.id.tv_date:
                    mContext.startActivity(new Intent(mContext, WarnReportActivity.class)
                                    .putExtra(WarnReportActivity.LABEL, WarnReportActivity.LABEL_VALUE_HOST)
                                    .putExtra("oid", itemData.getOid())
                                    .putExtra("buildid", itemData.getBuildId())
                            //.putExtra("unitid", itemData.get())
                    );
                    break;
            }
        }
    }
}
