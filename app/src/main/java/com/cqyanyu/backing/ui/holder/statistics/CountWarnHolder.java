package com.cqyanyu.backing.ui.holder.statistics;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.alarm.PositionActivity;
import com.cqyanyu.backing.ui.entity.warn.WaterSystemEntity;
import com.cqyanyu.backing.utils.MyPosition;
import com.cqyanyu.backing.utils.NumberUtils;
import com.cqyanyu.mvpframework.utils.XDateUtil;
import com.cqyanyu.mvpframework.utils.XScreenUtils;
import com.cqyanyu.mvpframework.view.recyclerView.IViewHolder;
import com.cqyanyu.mvpframework.view.recyclerView.XViewHolder;

import static com.cqyanyu.backing.utils.NumberUtils.setDecimalFloat;

/**
 * 预警item view
 * Created by Administrator on 2017/6/11 0011.
 */
public class CountWarnHolder extends IViewHolder {

    @Override
    protected XViewHolder create(View view, RecyclerView.Adapter adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public int getLayout() {
        return R.layout.item_alarm_warn;
    }

    private class ViewHolder extends XViewHolder<WaterSystemEntity> {

        TextView tvSn;
        TextView tvReportDate;
        TextView tvPosition;
        TextView tvCount;
        TextView tvStatus;
        TextView tvNum;

        ViewHolder(View itemView, RecyclerView.Adapter adapter) {
            super(itemView, adapter);
        }

        @Override
        protected void initView(View rootView) {
            tvSn = (TextView) rootView.findViewById(R.id.tv_sn);
            tvPosition = (TextView) rootView.findViewById(R.id.tv_local);
            tvReportDate = (TextView) rootView.findViewById(R.id.tv_date);
            tvStatus = (TextView) rootView.findViewById(R.id.tv_status);
            tvCount = (TextView) rootView.findViewById(R.id.tv_count);
            tvNum = (TextView) rootView.findViewById(R.id.tv_regulatory_num);
        }

        @Override
        protected void onBindData(WaterSystemEntity itemData) {
            tvNum.setText(getAdapterPosition() + 1 + "");
            tvCount.setText(NumberUtils.setDecimal((float) itemData.getDurationsec() / 86400) + "天");
            tvPosition.setText(MyPosition.formatPosition(itemData.getUnitstr(), itemData.getBuildstr(), itemData.getPosition()));
            //tvPosition.setText(!TextUtils.isEmpty(itemData.getPosition()) ? (itemData.getBuildstr() + "@" + itemData.getPosition()) : itemData.getBuildstr());
            tvReportDate.setText(XDateUtil.getStringByFormatFromStr(String.valueOf(itemData.getReportlastdate()), "yyyy-MM-dd HH:mm:ss"));
            tvPosition.setOnClickListener(this);
            setStatus();
            setTitleIcon();
        }

        //设置标题图片
        private void setTitleIcon() {
            short hval = (short) itemData.getVal();
            String val = itemData.getSn() + "  " + setDecimalFloat((float) hval / 1000f);
            if (TextUtils.equals("66", itemData.getTypeid() + "")) {
                tvSn.setText("水压:" + val + "MPa" + "\n" + itemData.getSn());
                //tvSn.setText(val + "MPa");
                tvSn.setCompoundDrawablePadding(XScreenUtils.dip2px(mContext, 4));
                tvSn.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.ic_suiya), null, null, null);
            } else if (TextUtils.equals("67", itemData.getTypeid() + "")) {
                tvSn.setText("液位:" + val + "M" + "\n" + itemData.getSn());
                //tvSn.setText(val + "M");
                tvSn.setCompoundDrawablePadding(XScreenUtils.dip2px(mContext, 4));
                tvSn.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.ic_yewei), null, null, null);
            }
        }

        private void setStatus() {
            int eventid = itemData.getReportevent();
            if (eventid == 2) {
                tvStatus.setText("偏低");
                tvStatus.setOnClickListener(this);
                tvStatus.setTextColor(mContext.getResources().getColor(R.color.color_red));
            } else if (eventid == 1) {
                tvStatus.setText("偏高");
                tvStatus.setOnClickListener(this);
                tvStatus.setTextColor(mContext.getResources().getColor(R.color.color_red));
            } else {
                tvStatus.setText("正常");
                tvCount.setText(0 + "天");
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_local:
                    mContext.startActivity(new Intent(mContext, PositionActivity.class)
                            .putExtra("oid", itemData.getBuildid())
                            .putExtra("buildstr", itemData.getBuildstr()));

                    break;
            }
        }
    }
}
