package com.cqyanyu.backing.ui.holder.warn;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.manger.InfoManger;
import com.cqyanyu.backing.ui.activity.alarm.HandleActivity;
import com.cqyanyu.backing.ui.activity.alarm.PositionActivity;
import com.cqyanyu.backing.ui.entity.home.AlarmNHEntity;
import com.cqyanyu.backing.ui.widget.app.XTextView;
import com.cqyanyu.backing.utils.MyDate;
import com.cqyanyu.mvpframework.utils.XScreenUtils;
import com.cqyanyu.mvpframework.utils.XToastUtil;
import com.cqyanyu.mvpframework.view.recyclerView.IViewHolder;
import com.cqyanyu.mvpframework.view.recyclerView.XViewHolder;

/**
 * 火警
 * Created by Administrator on 2017/7/13.
 */
public class AlarmHolder extends IViewHolder {

    @Override
    protected XViewHolder create(View view, RecyclerView.Adapter adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public int getLayout() {
        return R.layout.item_alarm_alarm;
    }

    private class ViewHolder extends XViewHolder<AlarmNHEntity> {

        XTextView tvTitle;
        TextView tvSn;
        TextView tvLocal;
        TextView tvDate;
        TextView tvSubtitle;
        ImageView ivGoNext;
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
            tvNum = (TextView) rootView.findViewById(R.id.tv_regulatory_num);
            tvSubtitle.setOnClickListener(this);
            tvLocal.setOnClickListener(this);
        }

        @Override
        protected void onBindData(AlarmNHEntity itemData) {
            /**设置标题*/
            tvTitle.setText(itemData.getTypestr());
            /**设置设备编号*/
            tvSn.setText(itemData.getSn());
            /**设置位置*/
            /**设置位置*/
            if (!TextUtils.isEmpty(itemData.getPosition()) && CommonInfo.getInstance().getUserInfo().isnode()) {
                tvLocal.setText(itemData.getBuildstr() + "@" + itemData.getPosition());
            } else if (CommonInfo.getInstance().getUserInfo().isnode()) {
                tvLocal.setText(itemData.getBuildstr());
            } else {
                tvLocal.setText(itemData.getUnitstr() + "@" + itemData.getBuildstr() + "@" + itemData.getPosition());
            }
            /**设置时间*/
            tvDate.setText(MyDate.getFormatDate(itemData.getReportdate()));
            /** 设置故障(图很多)*/
            tvSubtitle.setText(itemData.getAffairstr());
            /**设置图片 (以后修改成网络图片)*/
            setTitleIcon();
            tvNum.setText(getAdapterPosition() + 1 + "");
            /**设置是否处理*/
            tvSubtitle.setOnClickListener(this);
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
                            .putExtra("oid", itemData.getBuildid()));
                    break;
                case R.id.tv_subtitle:
                    //处理
                    if (InfoManger.getInstance().isPermission("69")) {
                        //if (mContext instanceof MainActivity) {
                        String label = "";
                        switch (itemData.getAffairid()) {
                            case 1://火警处理
                                label = HandleActivity.LABEL_VALUE_FIRE;
                                break;
                            case 2://故障处理
                                label = HandleActivity.LABEL_VALUE_FAULT;
                                break;
                        }
                        if (label.length() > 0) {
                            mContext.startActivity(new Intent(mContext, HandleActivity.class)
                                    .putExtra(HandleActivity.LABEL, label)
                                    .putExtra(HandleActivity.KEY_ENTITY, itemData));
                        }
                        //}
                    } else {
                        XToastUtil.showToast("暂不拥有该权限！");
                    }
                    break;
            }
        }
    }
}

