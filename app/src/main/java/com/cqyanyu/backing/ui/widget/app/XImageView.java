package com.cqyanyu.backing.ui.widget.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.net.XHttpUtils;

/**
 * 项目扩展ImageView
 * Created by Administrator on 2017/7/5.
 */
@SuppressLint("AppCompatCustomView")
public class XImageView extends ImageView {

    public XImageView(Context context) {
        this(context, null);
    }

    public XImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 加载头像
     *
     * @param path 路径
     */
    public void setAvatar(String path) {
        Glide.with(getContext()).load(XHttpUtils.getGlideUrl(CommonInfo.getInstance().getImageUrl(path)))
                .thumbnail(0.1f)
                .fitCenter()
                .placeholder(R.mipmap.zzz)
                .error(R.mipmap.zzz)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(this);
    }
}
