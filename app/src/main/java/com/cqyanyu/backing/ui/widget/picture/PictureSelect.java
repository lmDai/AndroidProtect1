package com.cqyanyu.backing.ui.widget.picture;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.net.XHttpUtils;
import com.cqyanyu.mvpframework.utils.camera.RxUtils;
import com.pictureselect.camerapicker.camera.Mode;
import com.pictureselect.camerapicker.camera.OnPhotoPickFinsh;
import com.pictureselect.photopicker.PhotoPreview;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片选择
 * Created by Administrator on 2017/7/20.
 */
public class PictureSelect extends RecyclerView {
    private String rootURL;
    private PictureAdapter adapter;
    private Activity activity;
    private List<String> mList;
    private Dialog dialog;
    //各种属性设置
    private int model;
    private int maxSelect;
    private int column;
    private int addIconRes;
    private int defaultIconRes;
    private int deleteIconRes;
    private int errorIconRes;
    private int showLayoutRes;

    public PictureSelect(Context context) {
        this(context, null);
    }

    public PictureSelect(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        analysis();
        initView();
    }

    //初始化属性
    private void initAttrs(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.PictureSelect);
            model = array.getInt(R.styleable.PictureSelect_x_model, 12);
            maxSelect = array.getInt(R.styleable.PictureSelect_x_maxNumber, 9);
            column = array.getInt(R.styleable.PictureSelect_x_column, 3);
            addIconRes = array.getResourceId(R.styleable.PictureSelect_x_add_icon, 0);
            defaultIconRes = array.getResourceId(R.styleable.PictureSelect_x_default_icon, 0);
            deleteIconRes = array.getResourceId(R.styleable.PictureSelect_x_delete_icon, 0);
            errorIconRes = array.getResourceId(R.styleable.PictureSelect_x_error_icon, 0);
            showLayoutRes = array.getResourceId(R.styleable.PictureSelect_x_show_layout, 0);
            array.recycle();
        }
    }

    //分析属性
    private void analysis() {
        if (showLayoutRes == 0) {
            throw new IllegalStateException("must has layout!");
        }
        if (addIconRes == 0) {
            throw new IllegalStateException("must has add icon resource!");
        }
        if (errorIconRes == 0) {
            throw new IllegalStateException("must has error icon resource!");
        }
    }

    //初始化视图
    private void initView() {
        //初始化Activity
        if (getContext() instanceof Activity) {
            activity = (Activity) getContext();
        }
        //初始化数据
        if (mList == null) mList = new ArrayList<>();
        //创建默认的线性GridLayoutManager
        this.setLayoutManager(new GridLayoutManager(getContext(), column));
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        this.setHasFixedSize(true);
        //创建并设置Adapter
        adapter = new PictureAdapter();
        this.setAdapter(adapter);
    }

    /**
     * 获取真实的图片地址
     *
     * @param source 源地址
     * @return 真实地址
     */
    private String getTrueUrl(String source) {
        return TextUtils.isEmpty(source) ? "" : ((source.startsWith("http") || source.startsWith("/storage")) ? source : (rootURL + source));
    }

    /**
     * 点击选择
     */
    private void clickSelect() {
        if (activity != null && mList != null && mList.size() >= 0) {
            switch (model) {
                case 11:
                    selectPicture();//  selectCamera();
                    break;
                case 12:
                    //暂不支持选择图片不显示相机
                    break;
                case 13:
                    selectPicture();
                    break;
                case 14:
                    selectDialog();
                    break;
            }
        }
    }

    /**
     * 直接调用相机
     */
    private void selectCamera() {
        com.pictureselect.camerapicker.camera.PictureSelector.create
                (activity, Mode.SYSTEM_CAMERA)
                .setListen(new OnPhotoPickFinsh() {
                    @Override
                    public void onPhotoPick(List<File> list) {
                        if (list != null && list.size() > 0) {
                            if (mList.size() < maxSelect) {
                                mList.add(list.get(0).getAbsolutePath());
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                }).start();
//        com.cqyanyu.mvpframework.X.rx().setRxCallback(new RxUtils.RxCallbackMultiple() {
//            @Override
//            public void selectImage(List<String> list) {
//                if (list != null && list.size() == 1) {
//                    if (mList.size() < maxSelect) {
//                        mList.addAll(list);
//                    }
//                    adapter.notifyDataSetChanged();
//                }
//            }
//        });
//        PictureSelector.create(activity)
//                .openCamera(PictureMimeType.ofImage())
//                .forResult(PictureConfig.CHOOSE_REQUEST);
//        if (cameraUtil != null) {
//            cameraUtil.setOnResultListener(new CameraUtil.OnResultListener() {
//                @Override
//                public void onResult(String path, int requestCode) {
//                    if (requestCode == 0xe03 && !TextUtils.isEmpty(path)) {
//                        if (mList.size() < maxSelect) {
//                            mList.add(path);
//                        }
//                        adapter.notifyDataSetChanged();
//                    }
//                }
//            });
//            cameraUtil.callCamera().setCompress(false).setCrop(false).start(0xe03);
//        }
    }

    /**
     * 选择图片
     */
    private void selectPicture() {
        com.cqyanyu.mvpframework.X.rx().selectMultiple(activity, maxSelect, mList, new RxUtils.RxCallbackMultiple() {
            @Override
            public void selectImage(List<String> list) {
                if (list != null && list.size() > 0) {
                    //处理选择图片的结果
                    mList = list;
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * 选择图片使用弹窗
     */
    private void selectDialog() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    /**
     * 删除图片
     *
     * @param position 位置
     */
    private void deletePicture(int position) {
        if (mList != null && mList.size() > 0 && mList.size() > position) {
            mList.remove(position);
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 设置弹窗选择图片
     *
     * @param dDialog   Dialog
     * @param cameraId  主要用来做camera点击事件
     * @param pictureId 主要用来做picture点击事件
     */
    public void setDialog(Dialog dDialog, int cameraId, int pictureId) {
        this.dialog = dDialog;
        Window window = dialog.getWindow();
        if (window != null) {
            if (cameraId > 0) {
                try {
                    window.findViewById(cameraId).setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            selectCamera();
                        }
                    });
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
            if (pictureId > 0) {
                try {
                    window.findViewById(pictureId).setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            selectPicture();
                        }
                    });
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
        }
    }

    /**
     * 设置网络图片的跟地址
     *
     * @param rootURL 跟地址
     */
    public void setRootURL(String rootURL) {
        this.rootURL = rootURL;
    }

    /**
     * 获取选中集合
     *
     * @return 集合
     */
    public List<String> getSelectList() {
        if (mList == null) mList = new ArrayList<>();
        return mList;
    }

    /**
     * 获取网络图片集
     *
     * @return 网络图片
     */
    public List<String> getNetList() {
        List<String> netList = new ArrayList<>();
        if (mList != null) {
            for (String path : mList) {
                if (!TextUtils.isEmpty(path)) {
                    if (!path.startsWith("/storage")) {
                        netList.add(path);
                    }
                }
            }
        }
        return netList;
    }

    /**
     * 获取本地图片
     *
     * @return 本地图片
     */
    public List<String> getLocalList() {
        List<String> localList = new ArrayList<>();
        if (mList != null) {
            for (String path : mList) {
                if (!TextUtils.isEmpty(path)) {
                    if (path.startsWith("/storage")) {
                        localList.add(path);
                    }
                }
            }
        }
        return localList;
    }

    /**
     * 设置图片
     *
     * @param list 数据
     */
    public void setList(List<String> list) {
        if (list != null && list.size() > 0) {
            if (list.size() > maxSelect) {
                for (int i = 0; i < list.size(); i++) {
                    /**移除掉多余的图片*/
                    if (i > maxSelect) mList.remove(i);
                }
            }
            mList = list;
        }
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 打开图片查看器
     *
     * @param position
     * @param urls2
     */
    private void imageBrower(int position, ArrayList<String> urls2) {
        PhotoPreview.builder()
                .setPhotos(urls2)
                .setCurrentItem(position)
                .start(activity);

    }

    public void clearData() {
        if (mList != null) {
            mList.clear();
        }
        adapter.notifyDataSetChanged();
    }

    private class PictureAdapter extends Adapter<PictureHolder> {

        @Override
        public PictureHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            /**初始化layout*/
            View view = null;
            if (showLayoutRes > 0) {
                view = LayoutInflater.from(activity).inflate(
                        activity.getResources().getLayout(showLayoutRes),
                        parent,
                        false);
            }
            return new PictureHolder(view);
        }

        @Override
        public void onBindViewHolder(PictureHolder holder, int position) {
            holder.onBindData(position);
        }

        @Override
        public int getItemCount() {
            return mList == null ? 1 : mList.size() < maxSelect ? (mList.size() + 1) : maxSelect;
        }
    }

    class PictureHolder extends ViewHolder implements OnClickListener,
            OnLongClickListener {
        private ImageView ivIcon;
        private ImageView ivDelete;
        private String itemData;
        private int position;
        private Context mContext;

        public PictureHolder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            /**这里显示图片的控件必须是ImageView，并且控件的id必须是:x_iv_icon*/
            ivIcon = (ImageView) itemView.findViewById(mContext.getResources().getIdentifier("x_iv_icon", "id", mContext.getPackageName()));
            /**这里显示删除图片的控件必须是ImageView，并且控件的id必须是:x_iv_delete*/
            ivDelete = (ImageView) itemView.findViewById(mContext.getResources().getIdentifier("x_iv_delete", "id", mContext.getPackageName()));
            if (ivIcon != null) {
                ivIcon.setOnClickListener(this);
            }
            if (ivDelete != null) {
                /**控件删除*/
                ivDelete.setOnClickListener(this);
                if (deleteIconRes > 0) {
                    /**设置删除图片*/
                    ivDelete.setImageResource(deleteIconRes);
                }
            } else {
                /**长按删除*/
                if (ivIcon != null) ivIcon.setOnLongClickListener(this);
            }
        }

        /**
         * 绑定数据
         *
         * @param position item项
         */
        public void onBindData(int position) {
            this.position = position;
            if (position == mList.size() && mList.size() != maxSelect) {
                /**显示添加图片*/
                Glide.with(mContext).load(addIconRes).into(ivIcon);
                if (ivDelete != null) ivDelete.setVisibility(GONE);
            } else {
                if (defaultIconRes > 0) {
                    /**图片加载过程中自定义显示的图片*/
                    ivIcon.setImageResource(defaultIconRes);
                }
                if (ivDelete != null) ivDelete.setVisibility(VISIBLE);
                /**显示真正的图片*/
                itemData = mList.get(position);
                /**显示本地图片*/
                if (itemData.startsWith("/storage")) {
                    Glide.with(mContext).load(itemData)
                            .thumbnail(0.1f)
                            .fitCenter()
                            .placeholder(errorIconRes)
                            .error(errorIconRes)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(ivIcon);
                } else {
                    /**显示网络图片，添加cookie*/
                    Glide.with(mContext).load(XHttpUtils.getGlideUrl(getTrueUrl(itemData)))
                            .thumbnail(0.1f)
                            .fitCenter()
                            .placeholder(errorIconRes)
                            .error(errorIconRes)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(ivIcon);
                }
            }
        }

        @Override
        public void onClick(View view) {
            if (view == ivIcon) {
                if (position == mList.size()) {
                    //选择图片
                    if (adapter != null) clickSelect();
                } else {
                    ArrayList<String> photoPaths = new ArrayList<>();
                    for (String str : mList) {
                        if (itemData.startsWith("/storage")) {
                            photoPaths.add(str);
                        } else {
                            photoPaths.add(getTrueUrl(str));
                        }
                    }
                    imageBrower(position, photoPaths);
                }
            } else if (view == ivDelete) {
                //删除图片
                if (adapter != null) deletePicture(position);
            }

        }

        @Override
        public boolean onLongClick(View view) {
            //删除图片
            if (adapter != null) deletePicture(position);
            return true;
        }
    }

}