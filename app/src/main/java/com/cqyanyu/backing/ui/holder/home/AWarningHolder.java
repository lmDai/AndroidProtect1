package com.cqyanyu.backing.ui.holder.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.entity.warn.WarningEntity;
import com.cqyanyu.backing.ui.widget.app.XTextView;
import com.cqyanyu.mvpframework.view.recyclerView.IViewHolder;
import com.cqyanyu.mvpframework.view.recyclerView.XViewHolder;

/**
 * 火警
 * Created by Administrator on 2017/7/18.
 */
public class AWarningHolder extends IViewHolder {

    @Override
    protected XViewHolder create(View view, RecyclerView.Adapter adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public int getLayout() {
        return R.layout.item_home_warn;
    }

    private class ViewHolder extends XViewHolder<WarningEntity> {

        XTextView tvBuild;
        XTextView tvLocal;
        TextView tvWaterPress;
        TextView tvWaterPressStatus;

        ViewHolder(View itemView, RecyclerView.Adapter adapter) {
            super(itemView, adapter);
        }

        @Override
        protected void initView(View rootView) {
            tvBuild = (XTextView) rootView.findViewById(R.id.tv_build);
            tvLocal = (XTextView) rootView.findViewById(R.id.tv_local);
            tvWaterPress = (TextView) rootView.findViewById(R.id.tv_water_press);
            tvWaterPressStatus = (TextView) rootView.findViewById(R.id.tv_water_press_status);
        }

        @Override
        protected void onBindData(WarningEntity itemData) {

        }

        @Override
        public void onClick(View v) {

        }
    }
}

