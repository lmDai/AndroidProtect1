package com.cqyanyu.backing.ui.holder.home;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.entity.home.SetWaterEntity;
import com.cqyanyu.backing.ui.net.XHttpUtils;
import com.cqyanyu.mvpframework.utils.XDateUtil;
import com.cqyanyu.mvpframework.view.recyclerView.IViewHolder;
import com.cqyanyu.mvpframework.view.recyclerView.XViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 水系统设备 view
 * Created by Administrator on 2017/6/11 0011.
 */
public class WaterSetHolder extends IViewHolder {

    @Override
    protected XViewHolder create(View view, RecyclerView.Adapter adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public int getLayout() {
        return R.layout.item_set_water;
    }

    private class ViewHolder extends XViewHolder<SetWaterEntity> {
        TextView tvCode;
        TextView tvDate;
        TextView tvSetPosition;
        ImageView imgOne, imgTwo, imgThere;
        TextView tvNum;

        ViewHolder(View itemView, RecyclerView.Adapter adapter) {
            super(itemView, adapter);
        }

        @Override
        protected void initView(View rootView) {
            tvCode = (TextView) rootView.findViewById(R.id.tv_code);
            tvDate = (TextView) rootView.findViewById(R.id.tv_date);
            tvSetPosition = (TextView) rootView.findViewById(R.id.tv_set_position);
            imgOne = (ImageView) rootView.findViewById(R.id.img_one);
            imgTwo = (ImageView) rootView.findViewById(R.id.img_two);
            imgThere = (ImageView) rootView.findViewById(R.id.img_there);
            tvNum = (TextView) rootView.findViewById(R.id.tv_regulatory_num);
        }

        @Override
        protected void onBindData(SetWaterEntity itemData) {
            /** 设置设备名称*/
            if (!TextUtils.isEmpty(itemData.getSn())) {
                tvCode.setText(itemData.getSn());
            }
            if (!TextUtils.isEmpty(itemData.getCreatedate() + "")) {
                /** 设置位置*/
                tvDate.setText(XDateUtil.getStringByFormatFromStr(itemData.getCreatedate(), "yyyy-MM-dd"));
            }
            if (!TextUtils.isEmpty(itemData.getPosition())) {
                tvSetPosition.setText(itemData.getPosition());
            }
            /** 设置item数*/
            tvNum.setText("" + (getAdapterPosition() + 1) + "");
            if (!TextUtils.isEmpty(itemData.getPicpath())) {
                /**解析图片地址*/
                String[] split = itemData.getPicpath().split(";");
                List<String> mList = new ArrayList<>();
                for (String path : split) {
                    if (!TextUtils.isEmpty(path)) {
                        mList.add(path);
                    }
                }
                /**加载图片地址*/
                if (mList.size() > 0) {
                    if (mList.size() == 1) {
                        imgOne.setVisibility(View.VISIBLE);
                        imgTwo.setVisibility(View.GONE);
                        imgThere.setVisibility(View.GONE);
                        showIcon(imgOne, mList.get(0));
                    } else if (mList.size() == 2) {
                        showIcon(imgOne, mList.get(0));
                        showIcon(imgTwo, mList.get(1));
                        imgOne.setVisibility(View.VISIBLE);
                        imgTwo.setVisibility(View.VISIBLE);
                        imgThere.setVisibility(View.GONE);
                    } else {
                        showIcon(imgOne, mList.get(0));
                        showIcon(imgTwo, mList.get(1));
                        showIcon(imgThere, mList.get(2));
                        imgOne.setVisibility(View.VISIBLE);
                        imgTwo.setVisibility(View.VISIBLE);
                        imgThere.setVisibility(View.VISIBLE);
                    }
                }
            } else {
                imgOne.setVisibility(View.GONE);
                imgTwo.setVisibility(View.GONE);
                imgThere.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {

        }

        /**
         * 显示图片
         *
         * @param imageView
         * @param path
         */
        private void showIcon(ImageView imageView, String path) {
            Glide.with(mContext).load(XHttpUtils.getGlideUrl(CommonInfo.getInstance().getImageUrl(path)))
                    .thumbnail(0.1f)
                    .fitCenter()
                    .placeholder(R.mipmap.zzz)
                    .error(R.mipmap.zzz)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }
    }
}
