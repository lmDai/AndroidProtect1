package com.cqyanyu.backing.ui.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.home.DutyStatusActivity;
import com.cqyanyu.backing.ui.activity.home.MyTaskActivity;
import com.cqyanyu.backing.ui.activity.home.PropagandaTrainingActivity;
import com.cqyanyu.backing.ui.activity.home.PublicResourceActivity;
import com.cqyanyu.backing.ui.activity.home.SystemManagementActivity;
import com.cqyanyu.backing.ui.activity.login.MainActivity;
import com.cqyanyu.backing.ui.adapter.base.XViewHolder;
import com.cqyanyu.backing.ui.entity.home.HomeBean;

/**
 * 首页holder
 * Created by Administrator on 2017/7/7.
 */
public class HomeHolder extends XViewHolder<HomeBean> {

    private ImageView ivIcon;
    private TextView tvName;
    private MainActivity activity;

    public HomeHolder(ViewGroup viewGroup) {
        super(viewGroup, R.layout.item_home_item);
    }

    @Override
    public void onBindView(View rootView) {
        ivIcon = (ImageView) rootView.findViewById(R.id.iv_icon);
        tvName = (TextView) rootView.findViewById(R.id.tv_name);
        if (mContext instanceof MainActivity) {
            activity = (MainActivity) mContext;
        }
    }

    @Override
    public void onBindData(HomeBean itemEntity) {
        ivIcon.setImageResource(itemEntity.getRes_id());
        tvName.setText(itemEntity.getName());
    }

    @Override
    public void onClick(View view) {
        switch (itemEntity.getKey_id()) {
            case 1://公共资源
                if (activity != null && activity.isLocal()) {
                    mContext.startActivity(new Intent(mContext, PublicResourceActivity.class));
                }
                break;

            case 2://宣传培训
                mContext.startActivity(new Intent(mContext, PropagandaTrainingActivity.class));
                break;
            case 3://我的任务
                mContext.startActivity(new Intent(mContext, MyTaskActivity.class));
                break;
            case 4://当值状态
                mContext.startActivity(new Intent(mContext, DutyStatusActivity.class));
                break;
            case 5://扫一扫
                if (activity != null) {
                    activity.gotoScan();
                }
                break;
            case 6://系统管理
                mContext.startActivity(new Intent(mContext, SystemManagementActivity.class));
                break;
        }
    }
}
