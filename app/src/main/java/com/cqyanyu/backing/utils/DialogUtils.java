package com.cqyanyu.backing.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.listener.OnSelectListener;
import com.cqyanyu.backing.ui.entity.home.ListBean;
import com.cqyanyu.backing.ui.widget.picker.XTimePicker;
import com.cqyanyu.mvpframework.utils.XScreenUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对话框工具类
 * Created by Administrator on 2017/4/21.
 */
public class DialogUtils {

    /**
     * 获取通用的Dialog
     *
     * @param context  Context
     * @param title    标题
     * @param msg      信息
     * @param btnLeft  左边的按钮文字内容
     * @param btnRight 右边的按钮文字内容
     * @param listener 监听
     * @return
     */
    public static Dialog getCommonDialog(Context context, String title, String msg, String btnLeft, String btnRight, final OnDialogOperationListener listener) {
        final Dialog dialog = new Dialog(context, R.style.DialogThemeNoTitle);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setContentView(R.layout.dialog_common);
            TextView tvTitle = (TextView) window.findViewById(R.id.tv_title);
            TextView tvMsg = (TextView) window.findViewById(R.id.tv_msg);
            Button btnSure = (Button) window.findViewById(R.id.btn_right);
            Button btnCancel = (Button) window.findViewById(R.id.btn_left);
            if (!TextUtils.isEmpty(btnLeft)) btnCancel.setText(btnLeft);
            if (!TextUtils.isEmpty(btnRight)) btnSure.setText(btnRight);
            if (!TextUtils.isEmpty(title)) tvTitle.setText(title);
            if (!TextUtils.isEmpty(msg)) tvMsg.setText(msg);
            btnSure.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dialog.dismiss();
                    if (listener != null)
                        listener.onDialogOperation(Operation.SURE);
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    if (listener != null)
                        listener.onDialogOperation(Operation.CANCEL);
                }
            });
        } else {
            dialog.dismiss();
        }
        return dialog;
    }

    /**
     * @return
     */
    public static Dialog getListDialog(Context context, String title, int titleColor, int BackgroundColor, final List<ListBean> mList, final OnDialogChoiceListener listener) {
        final Dialog dialog = new Dialog(context, R.style.DialogThemeNoTitle);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setContentView(R.layout.dialog_list);
            TextView tvTitle = (TextView) window.findViewById(R.id.tv_title);
            ListView listView = (ListView) window.findViewById(R.id.list_view);
            //加载title
            if (!TextUtils.isEmpty(title)) tvTitle.setText(title);
            if (titleColor != 0) tvTitle.setTextColor(titleColor);
            if (BackgroundColor != 0) tvTitle.setBackgroundColor(BackgroundColor);
            if (mList != null) {
                //加载列表
                List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
                for (int i = 0; i < mList.size(); i++) {
                    Map<String, Object> listItem = new HashMap<String, Object>();
                    listItem.put("tv_content", mList.get(i).getName());
                    listItems.add(listItem);
                }
                SimpleAdapter adapter = new SimpleAdapter(context, listItems,
                        R.layout.item_list, new String[]{"tv_content"},
                        new int[]{R.id.tv_content});
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        dialog.dismiss();
                        if (listener != null) {
                            listener.onDialogChoice(i, mList.get(i).getId());
                        }
                    }
                });
            }
        } else {
            dialog.dismiss();
        }
        return dialog;
    }

    /**
     * 获取日期的Dialog
     *
     * @param context  Context
     * @param listener 监听
     * @return Dialog
     */
    public static Dialog getDateDialog(Context context, String value, final OnSelectListener listener) {
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        if (window != null) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            window.setContentView(R.layout.dialog_date);
            initAttribute(context, window);
            final XTimePicker timePicker = (XTimePicker) window.findViewById(R.id.time_picker);
            TextView tvCancel = (TextView) window.findViewById(R.id.tv_cancel);
            TextView tvSure = (TextView) window.findViewById(R.id.tv_sure);
            if (!TextUtils.isEmpty(value)) {
                timePicker.setDate(value, "yyyy-MM-dd");
            }
            tvSure.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dialog.dismiss();
                    if (listener != null) {
                        listener.onSelect((timePicker.getDate().getTime() / 1000) + "");
                    }
                }
            });
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        } else {
            dialog.dismiss();
        }
        return dialog;
    }

    /**
     * 设置窗体属性
     *
     * @param context Context
     * @param window  Window
     */
    private static void initAttribute(Context context, Window window) {
        if (window != null) {
            WindowManager.LayoutParams wmParams = window.getAttributes();
            wmParams.format = PixelFormat.TRANSPARENT;  //内容全透明
            //wmParams.format = PixelFormat.TRANSLUCENT;//内容半透明
            wmParams.alpha = 1.0f;//调节透明度，1.0最大
            wmParams.width = XScreenUtils.getScreenWidth(context); //宽度设置为屏幕
            //dialog设置各种属性
            window.setAttributes(wmParams);
            window.setGravity(Gravity.BOTTOM);
            window.setBackgroundDrawable(new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                    new int[]{Color.WHITE, Color.WHITE}));
        }
    }

    public enum Operation {
        SURE, CANCEL, BACK
    }

    /**
     * 回调
     */
    public interface OnDialogOperationListener {
        void onDialogOperation(Operation operation);
    }

    /**
     * 回调
     */
    public interface OnDialogChoiceListener {
        void onDialogChoice(int position, String choice);
    }

    /**
     * 回调
     */
    public interface OnDialogInputListener {
        void onDialogInput(String input);
    }
}
