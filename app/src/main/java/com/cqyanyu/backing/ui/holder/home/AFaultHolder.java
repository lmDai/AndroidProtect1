package com.cqyanyu.backing.ui.holder.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.entity.warn.WarnEntity;
import com.cqyanyu.backing.ui.widget.app.XTextView;
import com.cqyanyu.mvpframework.view.recyclerView.IViewHolder;
import com.cqyanyu.mvpframework.view.recyclerView.XViewHolder;

/**
 * 火警
 * Created by Administrator on 2017/7/18.
 */
public class AFaultHolder extends IViewHolder {

    @Override
    protected XViewHolder create(View view, RecyclerView.Adapter adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public int getLayout() {
        return R.layout.item_fire;
    }

    private class ViewHolder extends XViewHolder<WarnEntity> {

        XTextView tvTitle;
        TextView tvLocal;
        TextView tvDate;
        XTextView tvSubtitle;
        View viewSubtitle;
        TextView tvTimes;

        ViewHolder(View itemView, RecyclerView.Adapter adapter) {
            super(itemView, adapter);
        }

        @Override
        protected void initView(View rootView) {
            tvTitle = (XTextView) rootView.findViewById(R.id.tv_title);
            tvDate = (TextView) rootView.findViewById(R.id.tv_date);
            tvLocal = (TextView) rootView.findViewById(R.id.tv_local);
            tvSubtitle = (XTextView) rootView.findViewById(R.id.tv_subtitle);
            viewSubtitle = rootView.findViewById(R.id.view_subtitle);
            tvTimes = (TextView) rootView.findViewById(R.id.tv_times);
        }

        @Override
        protected void onBindData(WarnEntity itemData) {

        }

        @Override
        public void onClick(View v) {

        }
    }
}

