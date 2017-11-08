package com.cqyanyu.backing.ui.holder.statistics;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.manger.InfoManger;
import com.cqyanyu.backing.ui.activity.home.TotalSetActivity;
import com.cqyanyu.backing.ui.activity.login.MainActivity;
import com.cqyanyu.backing.ui.activity.statistics.AlarmActivity;
import com.cqyanyu.backing.ui.activity.statistics.GridSatisticsActivity;
import com.cqyanyu.backing.ui.activity.statistics.GriderActivity;
import com.cqyanyu.backing.ui.activity.statistics.InspectionTaskActivity;
import com.cqyanyu.backing.ui.adapter.base.XViewHolder;
import com.cqyanyu.backing.ui.entity.statistics.CountBean;
import com.cqyanyu.backing.ui.widget.app.XTextView;
import com.cqyanyu.mvpframework.utils.XToastUtil;

/**
 * 统计Itemholder
 * Created by Administrator on 2017/7/7.
 */
public class CountItemHolder extends XViewHolder<CountBean> {

    private ImageView ivIcon;
    private TextView tvName;
    private XTextView tvTitle;
    private TextView tvCount;
    private MainActivity activity;

    public CountItemHolder(ViewGroup viewGroup) {
        super(viewGroup, R.layout.item_count);
    }

    @Override
    public void onBindView(View rootView) {
        ivIcon = (ImageView) rootView.findViewById(R.id.iv_icon);
        tvName = (TextView) rootView.findViewById(R.id.tv_name);
        tvTitle = (XTextView) rootView.findViewById(R.id.tv_title);
        tvCount = (TextView) rootView.findViewById(R.id.tv_count);
        if (mContext instanceof MainActivity) {
            activity = (MainActivity) mContext;
        }
    }

    @Override
    public void onBindData(CountBean itemEntity) {
        ivIcon.setImageResource(itemEntity.getRes_id());
        tvName.setText(itemEntity.getName());
        tvCount.setText(itemEntity.getCount());
        tvTitle.setXText(itemEntity.getTitle());
    }

    @Override
    public void onClick(View view) {
        switch (itemEntity.getKey_id()) {
            case 1:
                if (InfoManger.getInstance().isPermission("73")) {//设备总数
                    if (activity != null) {
                        mContext.startActivity(new Intent(mContext, TotalSetActivity.class));
                    }
                } else {
                    XToastUtil.showToast("暂不拥有该权限！");
                }
                break;

            case 2:
                if (InfoManger.getInstance().isPermission("74")) {//联网单位
                    mContext.startActivity(new Intent(mContext, GridSatisticsActivity.class));
                } else {
                    XToastUtil.showToast("暂不拥有该权限！");
                }
                break;

            case 3:
                if (InfoManger.getInstance().isPermission("75")) {//告警统计
                    mContext.startActivity(new Intent(mContext, AlarmActivity.class));
                } else {
                    XToastUtil.showToast("暂不拥有该权限！");
                }

                break;

            case 6:
                if (InfoManger.getInstance().isPermission("78")) {//联网用户(网格员)
                    mContext.startActivity(new Intent(mContext, GriderActivity.class));
                } else {
                    XToastUtil.showToast("暂不拥有该权限！");
                }
                break;

            case 5:
                if (InfoManger.getInstance().isPermission("77")) {//设施巡检
                    mContext.startActivity(new Intent(mContext, InspectionTaskActivity.class));
                } else {
                    XToastUtil.showToast("暂不拥有该权限！");
                }
                break;

            case 4://舆情分析
//                if (InfoManger.getInstance().isPermission("78")) {
//                    mContext.startActivity(new Intent(mContext, SafeTrendActivity.class));
//                } else {
//                    XToastUtil.showToast("暂不拥有该权限！");
//                }
                break;
        }
    }
}
