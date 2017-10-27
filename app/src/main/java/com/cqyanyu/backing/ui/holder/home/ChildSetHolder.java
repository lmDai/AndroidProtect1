package com.cqyanyu.backing.ui.holder.home;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.manger.InfoManger;
import com.cqyanyu.backing.ui.activity.home.AddChildSetActivity;
import com.cqyanyu.backing.ui.activity.home.AddSetActivity;
import com.cqyanyu.backing.ui.activity.home.ChildSetActivity;
import com.cqyanyu.backing.ui.entity.home.SetManageEntity;
import com.cqyanyu.mvpframework.utils.XToastUtil;
import com.cqyanyu.mvpframework.view.recyclerView.IViewHolder;
import com.cqyanyu.mvpframework.view.recyclerView.XViewHolder;

/**
 * 子设备管理 view
 * Created by Administrator on 2017/6/11 0011.
 */
public class ChildSetHolder extends IViewHolder {

    @Override
    protected XViewHolder create(View view, RecyclerView.Adapter adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public int getLayout() {
        return R.layout.item_set_manage;
    }

    private class ViewHolder extends XViewHolder<SetManageEntity> {
        TextView tvSetName;
        TextView tvSetUnit;
        TextView tvSetPosition;
        TextView tvSetState;
        TextView tvNum;
        TextView tvChildSet;

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
            tvChildSet = (TextView) rootView.findViewById(R.id.tv_child_set);
            tvSetState.setOnClickListener(this);
            tvChildSet.setOnClickListener(this);
        }

        @Override
        protected void onBindData(SetManageEntity itemData) {
            /** 设置设备名称*/
            tvSetName.setText(itemData.getTypestr() + "\t" + itemData.getSn());

            /** 设置所属单位*/
            tvSetUnit.setText(itemData.getUnitstr() + "@" + itemData.getBuildstr());

            /** 设置位置*/
            tvSetPosition.setText(itemData.getPosition());

            /** 设置状态*/
//            if (TextUtils.equals(itemData.getStatusid(), "0")) {
//                tvSetState.setText("正常");
//            } else {
//                tvSetState.setText("不正常");
//            }
            tvSetState.setText(itemData.getStatusstr());

            /** 设置item数*/
            tvNum.setText("" + (getAdapterPosition() + 1) + "");
            if (TextUtils.equals(itemData.getTypestr(), "编解码终端")) {
                tvChildSet.setVisibility(View.VISIBLE);
            } else {
                tvChildSet.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_set_state:
                    if (TextUtils.equals(itemData.getTypeid(), "32")) {
                        //主机末端传感器可以添加子设备
                        mContext.startActivity(new Intent(mContext, AddChildSetActivity.class)
                                .putExtra(AddChildSetActivity.LABEL, AddChildSetActivity.LABEL_VALUE_ADD_CHILD)
                                .putExtra("oid", itemData.getOid())
                                .putExtra("buildId", itemData.getBuildid())
                                .putExtra("unitId", itemData.getUnitid())
                        );
                        return;
                    }
                case R.id.tv_child_set:
                    mContext.startActivity(new Intent(mContext, ChildSetActivity.class)
                            .putExtra("oid", itemData.getOid())
                            .putExtra("buildId", itemData.getBuildid())
                            .putExtra("unitId", itemData.getUnitid()));
                    break;
                default:
                    if (InfoManger.getInstance().isPermission("62")) {
                        mContext.startActivity(new Intent(mContext, AddSetActivity.class)
                                .putExtra(AddSetActivity.LABEL, AddSetActivity.LABEL_VALUE_EDIT)
                                .putExtra("oid", itemData.getOid())
                                .putExtra("unitId", itemData.getUnitid())
                        );
                    } else {
                        XToastUtil.showToast("暂不拥有该权限！");
                    }
                    break;
            }
        }
    }
}
