package com.cqyanyu.backing.ui.holder.warn;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.alarm.PositionActivity;
import com.cqyanyu.backing.ui.activity.home.WarnReportActivity;
import com.cqyanyu.backing.ui.entity.warn.HostWarnEntity;
import com.cqyanyu.backing.ui.widget.app.XTextView;
import com.cqyanyu.backing.utils.MyDate;
import com.cqyanyu.mvpframework.utils.XScreenUtils;
import com.cqyanyu.mvpframework.view.recyclerView.IViewHolder;
import com.cqyanyu.mvpframework.view.recyclerView.XViewHolder;

/**
 * 主机告警
 * Created by Administrator on 2017/7/13.
 */
public class HostWarnHolder extends IViewHolder {

    @Override
    protected XViewHolder create(View view, RecyclerView.Adapter adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public int getLayout() {
        return R.layout.item_host_warn;
    }

    private class ViewHolder extends XViewHolder<HostWarnEntity> {

        XTextView tvTitle;
        TextView tvSn;
        TextView tvLocal;
        TextView tvDate;
        TextView tvSubtitle;
        ImageView ivGoNext;
        TextView tvReportCount;
        TextView tvNum;

        ViewHolder(View itemView, RecyclerView.Adapter adapter) {
            super(itemView, adapter);
        }

        @Override
        protected void initView(View rootView) {
            tvTitle = (XTextView) rootView.findViewById(R.id.tv_title);
            tvSn = (TextView) rootView.findViewById(R.id.tv_sn);
            tvLocal = (TextView) rootView.findViewById(R.id.tv_local);
            tvDate = (TextView) rootView.findViewById(R.id.tv_date);
            tvSubtitle = (TextView) rootView.findViewById(R.id.tv_subtitle);
            ivGoNext = (ImageView) rootView.findViewById(R.id.iv_go_next);
            tvReportCount = (TextView) rootView.findViewById(R.id.tv_report_count);
            tvNum = (TextView) rootView.findViewById(R.id.tv_regulatory_num);
            tvSubtitle.setOnClickListener(this);
            tvReportCount.setOnClickListener(this);
        }

        @Override
        protected void onBindData(HostWarnEntity itemData) {
            /**设置标题*/
            tvTitle.setText(itemData.getTypestr());
            /**设置设备编号*/
            tvSn.setText(itemData.getSn());
            if (!TextUtils.isEmpty(itemData.getPosition()) && CommonInfo.getInstance().getUserInfo().isnode()) {
                tvLocal.setText(itemData.getBuildstr() + "@" + itemData.getPosition());
            }else if (CommonInfo.getInstance().getUserInfo().isnode()) {
                tvLocal.setText(itemData.getBuildstr());
            }  else {
                tvLocal.setText(itemData.getUnitstr() + "@" + itemData.getBuildstr() + "@" + itemData.getPosition());
            }
            tvReportCount.setText(itemData.getTotalcount());
            /**设置时间*/
            tvDate.setText(MyDate.getFormatDate(itemData.getLastdate()));
            /** 设置故障(图很多)*/
            tvSubtitle.setText(itemData.getAffairstr());
            tvNum.setText(getAdapterPosition() + 1 + "");
            /**设置图片 (以后修改成网络图片)*/
            setTitleIcon();

            /**设置是否处理*/
            tvReportCount.setOnClickListener(this);
            tvLocal.setOnClickListener(this);
        }

        //设置标题图片
        private void setTitleIcon() {
            tvTitle.setCompoundDrawablePadding(XScreenUtils.dip2px(mContext, 4));
            tvTitle.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.ic_yangan), null, null, null);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_local:
                    mContext.startActivity(new Intent(mContext, PositionActivity.class)
                            .putExtra("pid", itemData.getUnitid())
                            .putExtra("oid", itemData.getBuildid()));
                    break;
                case R.id.tv_report_count:
                    mContext.startActivity(new Intent(mContext, WarnReportActivity.class)
                            .putExtra(WarnReportActivity.LABEL, WarnReportActivity.LABEL_VALUE_HOST)
                            .putExtra("oid", itemData.getOid())
                    );
                    break;
            }
        }
    }
}

