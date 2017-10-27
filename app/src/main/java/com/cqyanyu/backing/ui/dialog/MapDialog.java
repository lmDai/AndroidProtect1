package com.cqyanyu.backing.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.adapter.MapChoiceHolder;
import com.cqyanyu.backing.ui.adapter.base.XBaseAdapter;
import com.cqyanyu.backing.ui.entity.home.MapChoiceBean;
import com.cqyanyu.mvpframework.utils.XScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 地图弹窗
 * Created by Administrator on 2017/7/14.
 */
public class MapDialog extends Dialog {

    private ListView listView;
    private List<MapChoiceBean> mList;
    private XBaseAdapter adapter;

    public MapDialog(@NonNull Context context) {
        this(context, R.style.DialogThemeNoTitle);
    }

    private MapDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        adapter = new XBaseAdapter(MapChoiceHolder.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_map);
        initView();
        initParams();
        initListener();
        setAttribute(getWindow());
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.list_view);
    }

    private void initParams() {
        listView.setAdapter(adapter);
        if (mList != null) adapter.setList(mList);
    }

    private void initListener() {

    }

    //显示
    public void show(List<PoiInfo> mList) {
        setList(mList);
        super.show();
    }

    public XBaseAdapter getAdapter() {
        return adapter;
    }

    public List<MapChoiceBean> getList() {
        return mList;
    }

    //获取需要类型的集合
    private void setList(List<PoiInfo> list) {
        if (list != null) {
            mList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                PoiInfo p = list.get(i);
                if (p != null && p.location != null) {
                    if (i == 0) {
                        //设置默认选项
                        mList.add(new MapChoiceBean(p.location.latitude, p.location.longitude, p.name, p.address, true));
                    } else {
                        mList.add(new MapChoiceBean(p.location.latitude, p.location.longitude, p.name, p.address, false));
                    }
                }
            }
            if (listView != null && adapter != null) {
                adapter.setList(mList);
            }
        }
    }

    /**
     * 设置窗体属性
     *
     * @param window Window
     */
    private void setAttribute(@NonNull Window window) {
        WindowManager.LayoutParams wmParams = window.getAttributes();
        wmParams.format = PixelFormat.TRANSPARENT;  //内容全透明
        //wmParams.format = PixelFormat.TRANSLUCENT;//内容半透明
        wmParams.alpha = 1.0f;//调节透明度，1.0最大
        wmParams.width = XScreenUtils.getScreenWidth(getContext()); //宽度设置为屏幕
        //dialog设置各种属性
        window.setAttributes(wmParams);
        window.setDimAmount(0f);
        window.setWindowAnimations(R.style.mystyle);
        window.setGravity(Gravity.BOTTOM);
        window.setBackgroundDrawable(new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{Color.WHITE, Color.WHITE}));
    }
}
