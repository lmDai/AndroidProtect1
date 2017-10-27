package com.cqyanyu.backing.ui.holder.my;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.entity.my.CheckRecordsEntity;
import com.cqyanyu.backing.ui.widget.app.XTextView;
import com.cqyanyu.mvpframework.utils.XDateUtil;
import com.cqyanyu.mvpframework.view.recyclerView.IViewHolder;
import com.cqyanyu.mvpframework.view.recyclerView.XViewHolder;

/**
 * 查岗记录item view
 * Created by Administrator on 2017/6/11 0011.
 */
public class CheckRecordsHolder extends IViewHolder {

    @Override
    protected XViewHolder create(View view, RecyclerView.Adapter adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public int getLayout() {
        return R.layout.item_check_records;
    }

    private class ViewHolder extends XViewHolder<CheckRecordsEntity> {

        XTextView tvCheckedDate;
        XTextView tvIsAnswered;
        XTextView tvAnsweredPosition;
        LinearLayout llReplay, llPosition;

        ViewHolder(View itemView, RecyclerView.Adapter adapter) {
            super(itemView, adapter);
        }

        @Override
        protected void initView(View rootView) {
            tvCheckedDate = (XTextView) rootView.findViewById(R.id.tv_checked_date);
            tvIsAnswered = (XTextView) rootView.findViewById(R.id.tv_isanswered);
            llPosition = (LinearLayout) rootView.findViewById(R.id.ll_position);
            llReplay = (LinearLayout) rootView.findViewById(R.id.ll_replay);
            tvAnsweredPosition = (XTextView) rootView.findViewById(R.id.tv_answered_position);
        }

        @Override
        protected void onBindData(CheckRecordsEntity itemData) {
            tvCheckedDate.setXText(XDateUtil.getStringByFormat(itemData.getLaunchdate() * 1000, "yyyy年MM月dd日 HH:mm"));
            tvAnsweredPosition.setXText(itemData.getAnswerposition());
            if (itemData.getIsanswer() == 0) {
                tvIsAnswered.setXText("是");
                llPosition.setVisibility(View.VISIBLE);
                llReplay.setVisibility(View.GONE);
            } else if (itemData.getIsanswer() == 1) {
                tvIsAnswered.setXText("否");
                llPosition.setVisibility(View.GONE);
                llReplay.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onClick(View v) {
        }
    }
}
