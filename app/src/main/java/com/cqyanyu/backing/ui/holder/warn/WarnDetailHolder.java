package com.cqyanyu.backing.ui.holder.warn;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.entity.warn.WarningEntity;
import com.cqyanyu.backing.utils.MyDate;
import com.cqyanyu.mvpframework.view.recyclerView.IViewHolder;
import com.cqyanyu.mvpframework.view.recyclerView.XViewHolder;


/**
 * 预警item view
 * Created by Administrator on 2017/6/11 0011.
 */
public class WarnDetailHolder extends IViewHolder {

    @Override
    protected XViewHolder create(View view, RecyclerView.Adapter adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public int getLayout() {
        return R.layout.item_alarm_warn_detail;
    }

    private class ViewHolder extends XViewHolder<WarningEntity> {
        TextView tvDate;
        TextView tvVal;

        ViewHolder(View itemView, RecyclerView.Adapter adapter) {
            super(itemView, adapter);
        }

        @Override
        protected void initView(View rootView) {
            tvDate = (TextView) rootView.findViewById(R.id.tv_date);
            tvVal = (TextView) rootView.findViewById(R.id.tv_val);
        }

        @Override
        protected void onBindData(WarningEntity itemData) {
            /**设置时间*/
            tvDate.setText(MyDate.getFormatDate(itemData.getReportdate()));
            /**设置位置加设置建筑名称*/

            /**设置水压状态*/
            //setWaterPress();
            /***/
            /**设置是否处理*/
            //setHandle();
            //tvLocal.setOnClickListener(this);
        }

        /**
         * 设置是否处理==> 判断是否是首页（如果是首页才有可能显示按钮,不是首页就隐藏按钮）
         */
        private void setHandle() {
            /**是否已经处理 0：未处理 1：已处理*/
//            if (mContext instanceof MainActivity) {
//                if (itemData.getIshandle() == 0 && itemData.getReporteventid() != 0) {
//                    ivGoNext.setVisibility(View.VISIBLE);
//                } else {
//                    ivGoNext.setVisibility(View.GONE);
//                }
//            } else {
//                ivGoNext.setVisibility(View.GONE);
//            }
        }

        //设置水压状态
        private void setWaterPress() {
//            switch (itemData.getReporteventid()) {
//                case -1:
//                    tvWaterPress.setEnabled(true);
//                    break;
//                case 0:
//                    tvWaterPress.setEnabled(false);
//                    break;
//                case 1:
//                    tvWaterPress.setEnabled(true);
//                    break;
//            }
        }

        @Override
        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.tv_water_press:
//                    //处理
//                    if (InfoManger.getInstance().isPermission("71")) {
//                        if (ivGoNext.getVisibility() == View.VISIBLE) {
//                            mContext.startActivity(new Intent(mContext, HandleActivity.class)
//                                    .putExtra(HandleActivity.LABEL, HandleActivity.LABEL_VALUE_WARNING)
//                                    .putExtra(HandleActivity.KEY_ENTITY, itemData)
//                            );
//                        }
//                    } else {
//                        XToastUtil.showToast("暂不拥有该权限！");
//                    }
//                    break;
//                case R.id.tv_local:
//                    if (mContext instanceof MainActivity) {
//                        mContext.startActivity(new Intent(mContext, PositionActivity.class)
//                                .putExtra("pid", itemData.getPid())
//                                .putExtra("oid", itemData.getBuildid()));
//
//                    }
//                    break;
//            }
        }
    }
}
