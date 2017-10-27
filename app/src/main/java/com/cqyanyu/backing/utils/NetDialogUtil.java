package com.cqyanyu.backing.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.mvpframework.utils.XScreenUtils;

/**
 * Created by Administrator on 2017/7/11.
 */

public class NetDialogUtil {

    /**
     * 加载进度框
     *
     * @param context
     * @param msg     提示信息
     * @return
     */
    public static Dialog showLoadDialog(Context context, String msg) {
        Dialog dialog = new Dialog(context, com.cqyanyu.mvpframework.R.style.DialogThemeNoTitle);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_net_loading, null);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (XScreenUtils.getScreenWidth(context) / 5 * 4);
//        lp.height = (int) (XScreenUtils.getScreenHeight(context)*0.3);
        lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);
        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress);
        TextView tv_msg = (TextView) view.findViewById(R.id.message);
        if (!TextUtils.isEmpty(msg)) {
            tv_msg.setText(msg);
        }
        return dialog;
    }

    /**
     * 加载进度框
     *
     * @param context
     * @param msg     提示信息
     * @return
     */
    public static Dialog showLoadDialog(Context context, int msg) {
        Dialog dialog = new Dialog(context, com.cqyanyu.mvpframework.R.style.DialogThemeNoTitle);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_net_loading, null);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (XScreenUtils.getScreenWidth(context) / 5 * 4);
//        lp.height = (int) (XScreenUtils.getScreenHeight(context)*0.3);
        lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);
        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress);
        TextView tv_msg = (TextView) view.findViewById(R.id.message);
        if (msg != 0) {
            String mm = context.getString(msg);
            if (!TextUtils.isEmpty(mm))
                tv_msg.setText(mm);
        }
        return dialog;
    }
}
