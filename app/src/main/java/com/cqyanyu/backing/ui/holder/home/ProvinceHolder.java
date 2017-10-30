package com.cqyanyu.backing.ui.holder.home;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.home.ProvinceActivity;
import com.cqyanyu.backing.ui.entity.home.UnitEntity;
import com.cqyanyu.mvpframework.view.recyclerView.IViewHolder;
import com.cqyanyu.mvpframework.view.recyclerView.XViewHolder;

/**
 * 省市item view
 * Created by Administrator on 2017/7/12.
 */
public class ProvinceHolder extends IViewHolder {

    @Override
    protected XViewHolder create(View view, RecyclerView.Adapter adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public int getLayout() {
        return R.layout.item_province;
    }

    private class ViewHolder extends XViewHolder<UnitEntity> {
        TextView tvName;
        TextView tvType;
        ImageView ivIcon;
        ProvinceActivity activity;

        ViewHolder(View itemView, RecyclerView.Adapter adapter) {
            super(itemView, adapter);
        }

        @Override
        protected void initView(View rootView) {
            tvName = (TextView) rootView.findViewById(R.id.tv_name);
            tvType = (TextView) rootView.findViewById(R.id.tv_type);
            ivIcon = (ImageView) rootView.findViewById(R.id.iv_icon);
            ivIcon.setOnClickListener(this);
            if (mContext instanceof ProvinceActivity) {
                activity = (ProvinceActivity) ProvinceHolder.this.mContext;
            }
        }

        @Override
        protected void onBindData(UnitEntity itemData) {
            /**设置省*/
            tvName.setText(TextUtils.isEmpty(itemData.getUnitstr()) ? itemData.getOid() : itemData.getUnitstr());
            /**设置单位类型*/
            tvType.setText("单位类型：" + itemData.getTypestr() + "");
            /**父类的位置*/
            ivIcon.setVisibility(TextUtils.equals(itemData.getChildunitcount(), "0") && TextUtils.equals(itemData.getChildbuildcount(), "0")
                    ? View.GONE : View.VISIBLE);
        }

        @Override
        public void onClick(View v) {
            if (activity != null) {
                switch (v.getId()) {
                    case R.id.iv_icon:
                        activity.gotoNext(itemData, true, itemData.getChildunitcount(),itemData.getChildbuildcount());
                        break;
                    default:
                        activity.gotoNext(itemData, false, itemData.getChildunitcount(),itemData.getChildbuildcount());
                        break;
                }


            }
        }
    }
}
