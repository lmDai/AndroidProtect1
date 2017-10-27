package com.cqyanyu.backing.ui.holder.home;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.entity.home.DutyStatusEntity;
import com.cqyanyu.backing.utils.MyDate;
import com.cqyanyu.mvpframework.view.ItemOptionView;
import com.cqyanyu.mvpframework.view.recyclerView.IViewHolder;
import com.cqyanyu.mvpframework.view.recyclerView.XViewHolder;

/**
 * 当值状态
 * Created by Administrator on 2017/7/18.
 */
public class DutyStatusHolder extends IViewHolder {

    @Override
    protected XViewHolder create(View view, RecyclerView.Adapter adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public int getLayout() {
        return R.layout.item_duty_status;
    }

    private class ViewHolder extends XViewHolder<DutyStatusEntity> {
        ItemOptionView iovUsername;
        ItemOptionView iovCheckNum;
        ItemOptionView iovReplyNum;

        ItemOptionView iovUnit;
        ItemOptionView iovDate;
        TextView iovPosition, tvRegulatoryNum;


        ViewHolder(View itemView, RecyclerView.Adapter adapter) {
            super(itemView, adapter);
        }

        @Override
        protected void initView(View rootView) {
            iovUsername = (ItemOptionView) rootView.findViewById(R.id.iov_username);
            iovCheckNum = (ItemOptionView) rootView.findViewById(R.id.iov_check_num);
            iovReplyNum = (ItemOptionView) rootView.findViewById(R.id.iov_reply_num);
            iovUnit = (ItemOptionView) rootView.findViewById(R.id.iov_unit);
            iovDate = (ItemOptionView) rootView.findViewById(R.id.iov_date);
            iovPosition = (TextView) rootView.findViewById(R.id.iov_position);
            tvRegulatoryNum = (TextView) rootView.findViewById(R.id.tv_regulatory_num);
            itemView.setClickable(false);
        }

        @Override
        protected void onBindData(DutyStatusEntity itemData) {
            tvRegulatoryNum.setText(getAdapterPosition() + 1 + "");
            // 姓名
            iovUsername.setContent(itemData.getUserstr());
            // 查岗次数
            iovCheckNum.setContent(itemData.getLaunchcount() + "");
            // 回复次数
            iovReplyNum.setContent(itemData.getAnswercount() + "");
            // 单位
            iovUnit.setContent(itemData.getUnitstr());
            // 回复时间
            iovDate.setContent(MyDate.getFormatDate(itemData.getLastoperationdate()));
            // 回复位置
            if (TextUtils.isEmpty(itemData.getLastanswerposition())) {
                iovPosition.setText(itemData.getUnitstr());
            } else {
                iovPosition.setText(itemData.getUnitstr() + "@" + itemData.getLastanswerposition());
            }
        }

        @Override
        public void onClick(View v) {

        }
    }
}
