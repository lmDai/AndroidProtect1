package com.cqyanyu.backing.ui.holder.home;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.alarm.PositionActivity;
import com.cqyanyu.backing.ui.activity.home.ChildAlarmActivity;
import com.cqyanyu.backing.ui.activity.home.SetManageActivity;
import com.cqyanyu.backing.ui.entity.home.SetInspectionEntity;
import com.cqyanyu.backing.ui.net.XHttpUtils;
import com.cqyanyu.mvpframework.utils.XDateUtil;
import com.cqyanyu.mvpframework.view.recyclerView.IViewHolder;
import com.cqyanyu.mvpframework.view.recyclerView.XViewHolder;
import com.pictureselect.photopicker.PhotoPreview;

import java.util.ArrayList;
import java.util.List;

/**
 * 巡检设备 view
 * Created by Administrator on 2017/6/11 0011.
 */
public class InspectionSetHolder extends IViewHolder {

    @Override
    protected XViewHolder create(View view, RecyclerView.Adapter adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public int getLayout() {
        return R.layout.item_set_inspection;
    }

    private class ViewHolder extends XViewHolder<SetInspectionEntity> {
        TextView tvCode;
        TextView tvDate;
        TextView tvSetPosition;
        ImageView imgOne, imgTwo, imgThere;
        TextView tvNum;
        SetManageActivity activity;
        TextView tvModel;
        LinearLayout llModel;


        ViewHolder(View itemView, RecyclerView.Adapter adapter) {
            super(itemView, adapter);
        }

        @Override
        protected void initView(View rootView) {
            llModel = (LinearLayout) rootView.findViewById(R.id.ll_model);
            tvCode = (TextView) rootView.findViewById(R.id.tv_code);
            tvDate = (TextView) rootView.findViewById(R.id.tv_date);
            tvModel = (TextView) rootView.findViewById(R.id.tv_model);
            tvSetPosition = (TextView) rootView.findViewById(R.id.tv_set_position);
            imgOne = (ImageView) rootView.findViewById(R.id.img_one);
            imgTwo = (ImageView) rootView.findViewById(R.id.img_two);
            imgThere = (ImageView) rootView.findViewById(R.id.img_there);
            tvNum = (TextView) rootView.findViewById(R.id.tv_regulatory_num);
            imgOne.setOnClickListener(this);
            imgTwo.setOnClickListener(this);
            imgThere.setOnClickListener(this);
            tvSetPosition.setOnClickListener(this);
        }

        @Override
        protected void onBindData(SetInspectionEntity itemData) {
            activity = (SetManageActivity) mContext;
            if (TextUtils.isEmpty(itemData.getBrand()) && TextUtils.isEmpty(itemData.getModel())) {
                llModel.setVisibility(View.GONE);
            } else {
                tvModel.setText(itemData.getBrand() + itemData.getModel());
            }
            /** 设置设备名称*/
            if (!TextUtils.isEmpty(itemData.getSn())) {
                tvCode.setText(itemData.getSn());
            }
            if (!TextUtils.isEmpty(itemData.getCreatedate() + "")) {
                tvDate.setText(XDateUtil.getStringByFormatFromStr(itemData.getEnddate(), "yyyy-MM-dd"));
            }
            if (!TextUtils.isEmpty(itemData.getPosition()) && CommonInfo.getInstance().getUserInfo().isnode()) {
                tvSetPosition.setText(itemData.getBuildstr() + "@" + itemData.getPosition());
            } else if (CommonInfo.getInstance().getUserInfo().isnode()) {
                tvSetPosition.setText(itemData.getBuildstr());
            } else {
                tvSetPosition.setText(itemData.getUnitstr() + "@" + itemData.getBuildstr() + "@" + itemData.getPosition());
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
            ArrayList<String> mList = new ArrayList<>();
            if (!TextUtils.isEmpty(itemData.getPicpath())) {
                /**解析图片地址*/
                String[] split = itemData.getPicpath().split(";");
                for (String path : split) {
                    if (!TextUtils.isEmpty(path)) {
                        mList.add(CommonInfo.getInstance().getImageUrl(path));
                    }
                }
            }
            switch (v.getId()) {
                case R.id.img_one:
                    imageBrower(0, mList);
                    break;
                case R.id.img_two:
                    imageBrower(1, mList);
                    break;
                case R.id.img_there:
                    imageBrower(2, mList);
                    break;
                case R.id.tv_set_position:
                    mContext.startActivity(new Intent(mContext, PositionActivity.class)
                            .putExtra("buildStr", itemData.getBuildstr())
                            .putExtra("position", itemData.getPosition())
                            .putExtra("latitude", itemData.getLatitude())
                            .putExtra("longitude", itemData.getLongitude()));
                    break;
                default:
                    if (activity.getLabel().equals("68")) {
                        mContext.startActivity(new Intent(mContext, ChildAlarmActivity.class)
                                .putExtra("oid", itemData.getOid()));
                        break;
                    }
            }
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

        /**
         * 打开图片查看器
         *
         * @param position
         * @param urls2
         */
        void imageBrower(int position, ArrayList<String> urls2) {
            PhotoPreview.builder()
                    .setPhotos(urls2)
                    .setCurrentItem(position)
                    .start((Activity) mContext);
        }
    }
}
