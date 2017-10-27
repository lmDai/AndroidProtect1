package com.cqyanyu.backing.ui.holder.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.entity.home.DetailsResourceEntity;
import com.cqyanyu.backing.utils.NumberUtils;
import com.cqyanyu.mvpframework.view.recyclerView.IViewHolder;
import com.cqyanyu.mvpframework.view.recyclerView.XViewHolder;

/**
 * 资源item
 * Created by Administrator on 2017/7/18.
 */
public class ResourceHolder extends IViewHolder {

    @Override
    protected XViewHolder create(View view, RecyclerView.Adapter adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public int getLayout() {
        return R.layout.item_resource;
    }

    private class ViewHolder extends XViewHolder<DetailsResourceEntity> {

        TextView tvDistance;
        TextView tvName;
        TextView tvLocal;

        ViewHolder(View itemView, RecyclerView.Adapter adapter) {
            super(itemView, adapter);
        }

        @Override
        protected void initView(View rootView) {
            tvDistance = (TextView) rootView.findViewById(R.id.tv_distance);
            tvName = (TextView) rootView.findViewById(R.id.tv_name);
            tvLocal = (TextView) rootView.findViewById(R.id.tv_local);
        }

        @Override
        protected void onBindData(DetailsResourceEntity itemData) {
            //距离
            tvDistance.setText("" + NumberUtils.setDecimalDouble(itemData.getDistance()) + "km");
            //名字
            tvName.setText(itemData.getNames());
            //地区
            tvLocal.setText(itemData.getLocal());
        }

        @Override
        public void onClick(View v) {

        }
    }
}
