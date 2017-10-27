package com.cqyanyu.backing.ui.holder.home;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.entity.home.TaskDetailsEntity;
import com.cqyanyu.backing.ui.net.XHttpUtils;
import com.cqyanyu.mvpframework.view.ItemOptionView;
import com.cqyanyu.mvpframework.view.recyclerView.IViewHolder;
import com.cqyanyu.mvpframework.view.recyclerView.XViewHolder;
import com.pictureselect.photopicker.PhotoPreview;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的任务
 * Created by Administrator on 2017/7/18.
 */
public class TaskDetailsHolder extends IViewHolder {

    @Override
    protected XViewHolder create(View view, RecyclerView.Adapter adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public int getLayout() {
        return R.layout.item_task_details;
    }

    private class ViewHolder extends XViewHolder<TaskDetailsEntity> {

        TextView iovSetName;
        TextView iovSetPosition;
        ItemOptionView iovInspectionState;
        ItemOptionView iovSetState;
        ImageView ivFirst;
        ImageView ivSecond;
        ImageView ivThird;

        ViewHolder(View itemView, RecyclerView.Adapter adapter) {
            super(itemView, adapter);
        }

        @Override
        protected void initView(View rootView) {
            iovSetName = (TextView) rootView.findViewById(R.id.iov_set_name);
            //iovBelongBuild = (ItemOptionView) rootView.findViewById(R.id.iov_belong_build);
            iovSetPosition = (TextView) rootView.findViewById(R.id.iov_set_position);
            //iovInspection = (ItemOptionView) rootView.findViewById(R.id.iov_inspection);
            iovInspectionState = (ItemOptionView) rootView.findViewById(R.id.iov_inspection_state);
            iovSetState = (ItemOptionView) rootView.findViewById(R.id.iov_set_state);
            ivFirst = (ImageView) rootView.findViewById(R.id.iv_first);
            ivSecond = (ImageView) rootView.findViewById(R.id.iv_second);
            ivThird = (ImageView) rootView.findViewById(R.id.iv_third);
            itemView.setClickable(false);
        }

        @Override
        protected void onBindData(TaskDetailsEntity itemData) {
            //设备名称
            iovSetName.setText(itemData.getTypestr() + "\t" + itemData.getSn());
            //所属建筑
            //iovBelongBuild.setContent();
            //设备位置
            iovSetPosition.setText(itemData.getPosition());

            //巡检人
            //iovInspection.setContent(InfoManger.getInstance().getUserName(mContext, itemData.getInspectionmanid()));
            //巡检状态
            iovInspectionState.setContent(itemData.getIsinspection() == 0 ? "未巡检" : "已巡检");
            //设备状态
            iovSetState.setContent(itemData.getProblemstr());
            //设置图片
            initPicture(itemData.getPicpath());
            ivFirst.setOnClickListener(this);
            ivSecond.setOnClickListener(this);
            ivSecond.setOnClickListener(this);
        }

        /**
         * 初始化图片
         *
         * @param picture 图片路径集
         */
        private void initPicture(String picture) {
            ivFirst.setVisibility(View.GONE);
            ivSecond.setVisibility(View.GONE);
            ivThird.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(picture)) {
                /**解析图片地址*/
                String[] split = picture.split(";");
                List<String> mList = new ArrayList<>();
                for (String path : split) {
                    if (!TextUtils.isEmpty(path)) {
                        mList.add(path);
                    }
                }
                /**加载图片地址*/
                if (mList.size() > 0) {
                    for (int i = 0; i < mList.size(); i++) {
                        if (i == 0) {
                            ivFirst.setVisibility(View.VISIBLE);
                            showIcon(ivFirst, mList.get(i));
                        } else if (i == 1) {
                            ivSecond.setVisibility(View.VISIBLE);
                            showIcon(ivSecond, mList.get(i));
                        } else if (i == 2) {
                            ivThird.setVisibility(View.VISIBLE);
                            showIcon(ivThird, mList.get(i));
                        }
                    }
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
                    case R.id.iv_first:
                        imageBrower(0, mList);
                        break;
                    case R.id.iv_second:
                        imageBrower(1, mList);
                        break;
                    case R.id.iv_third:
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
    }
}

