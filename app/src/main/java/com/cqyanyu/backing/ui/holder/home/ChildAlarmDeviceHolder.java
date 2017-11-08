package com.cqyanyu.backing.ui.holder.home;

import android.app.Activity;
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
import com.cqyanyu.backing.ui.entity.home.ChildAlarmDetailsEntity;
import com.cqyanyu.backing.ui.net.XHttpUtils;
import com.cqyanyu.mvpframework.view.recyclerView.IViewHolder;
import com.cqyanyu.mvpframework.view.recyclerView.XViewHolder;
import com.pictureselect.photopicker.PhotoPreview;

import java.util.ArrayList;

/**
 * 巡检设备 view
 * Created by Administrator on 2017/6/11 0011.
 */
public class ChildAlarmDeviceHolder extends IViewHolder {

    @Override
    protected XViewHolder create(View view, RecyclerView.Adapter adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public int getLayout() {
        return R.layout.item_child_alarm_device;
    }

    private class ViewHolder extends XViewHolder<ChildAlarmDetailsEntity> {
        TextView tvCode;
        TextView tvSetPosition;
        ImageView imgOne, imgTwo, imgThere;
        TextView tvNum;
        LinearLayout llModel;

        ViewHolder(View itemView, RecyclerView.Adapter adapter) {
            super(itemView, adapter);
        }

        @Override
        protected void initView(View rootView) {
            tvCode = (TextView) rootView.findViewById(R.id.tv_code);
            tvSetPosition = (TextView) rootView.findViewById(R.id.tv_set_position);
            imgOne = (ImageView) rootView.findViewById(R.id.img_one);
            imgTwo = (ImageView) rootView.findViewById(R.id.img_two);
            imgThere = (ImageView) rootView.findViewById(R.id.img_there);
            tvNum = (TextView) rootView.findViewById(R.id.tv_regulatory_num);
            imgOne.setOnClickListener(this);
            imgTwo.setOnClickListener(this);
            imgThere.setOnClickListener(this);
        }

        @Override
        protected void onBindData(ChildAlarmDetailsEntity itemData) {
            /** 设置设备名称*/
            if (!TextUtils.isEmpty(itemData.getSn())) {
                tvCode.setText(itemData.getSn());
            }
            if (!TextUtils.isEmpty(itemData.getPosition())) {
                tvSetPosition.setText(itemData.getPosition());
            }
            /** 设置item数*/
            tvNum.setText("" + (getAdapterPosition() + 1) + "");
//            if (!TextUtils.isEmpty(itemData.getPicpath())) {
//                /**解析图片地址*/
//                String[] split = itemData.getPicpath().split(";");
//                List<String> mList = new ArrayList<>();
//                for (String path : split) {
//                    if (!TextUtils.isEmpty(path)) {
//                        mList.add(path);
//                    }
//                }
//                /**加载图片地址*/
//                if (mList.size() > 0) {
//                    for (int i = 0; i < mList.size(); i++) {
//                        if (i == 0) {
//                            imgOne.setVisibility(View.VISIBLE);
//                            showIcon(imgOne, mList.get(i));
//                        } else if (i == 1) {
//                            imgTwo.setVisibility(View.VISIBLE);
//                            showIcon(imgTwo, mList.get(i));
//                        } else if (i == 2) {
//                            imgThere.setVisibility(View.VISIBLE);
//                            showIcon(imgThere, mList.get(i));
//                        }
//                    }
//                }
//            }
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
                }

            }
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
