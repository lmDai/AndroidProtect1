package com.cqyanyu.backing.ui.holder.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.home.CheckPostActivity;
import com.cqyanyu.backing.ui.entity.home.UserManageEntity;
import com.cqyanyu.mvpframework.view.recyclerView.IViewHolder;
import com.cqyanyu.mvpframework.view.recyclerView.XViewHolder;

/**
 * 一键查岗
 * Created by Administrator on 2017/7/18.
 */
public class CheckPostHolder extends IViewHolder {

    @Override
    protected XViewHolder create(View view, RecyclerView.Adapter adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public int getLayout() {
        return R.layout.item_check_post;
    }

    private class ViewHolder extends XViewHolder<UserManageEntity> {

        TextView name;
        TextView unit;
        CheckBox cbChoice;
        CheckPostActivity activity;

        ViewHolder(View itemView, RecyclerView.Adapter adapter) {
            super(itemView, adapter);
        }

        @Override
        protected void initView(View rootView) {
            name = (TextView) rootView.findViewById(R.id.name);
            unit = (TextView) rootView.findViewById(R.id.unit);
            cbChoice = (CheckBox) rootView.findViewById(R.id.cb_choice);
            if (mContext instanceof CheckPostActivity) {
                activity = (CheckPostActivity) mContext;
            }
            cbChoice.setOnClickListener(this);
        }

        @Override
        protected void onBindData(UserManageEntity itemData) {
            //姓名
            name.setText("用户：\t\t" + itemData.getUserstr() + "");
            //单位
            if (CommonInfo.getInstance().getUserInfo().isnode()) {
                unit.setVisibility(View.GONE);
            } else {
                unit.setVisibility(View.VISIBLE);
                unit.setText("单位：\t\t" + itemData.getUnitstr() + "");
            }
            cbChoice.setChecked(itemData.isClickable());
        }

        @Override
        public void onClick(View v) {
            if (activity != null) {
                if (v.getId() != R.id.cb_choice) {
                    //设置点击
                    cbChoice.setChecked(!cbChoice.isChecked());
                }
                //同步数据
                itemData.setClickable(cbChoice.isChecked());
                activity.clickItem();
            }
        }
    }

}

