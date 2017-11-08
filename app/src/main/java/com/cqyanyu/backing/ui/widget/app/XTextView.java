package com.cqyanyu.backing.ui.widget.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import com.cqyanyu.backing.R;


/**
 * 项目扩展TextView
 * Created by Administrator on 2017/7/5.
 */
@SuppressLint("AppCompatCustomView")
public class XTextView extends TextView {
    private String frontValue;
    private int frontColor;
    private String afterValue;
    private int afterColor;
    private String nullShow;

    public XTextView(Context context) {
        this(context, null);
    }

    public XTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.XTextView);
            frontValue = array.getString(R.styleable.XTextView_x_front_value);
            frontColor = array.getColor(R.styleable.XTextView_x_front_color, 0);
            afterValue = array.getString(R.styleable.XTextView_x_after_value);
            afterColor = array.getColor(R.styleable.XTextView_x_after_color, 0);
            nullShow = array.getString(R.styleable.XTextView_x_null_show);
            array.recycle();
        }
        if (TextUtils.isEmpty(frontValue)) frontValue = "";
        if (TextUtils.isEmpty(afterValue)) afterValue = "";
        setXText(getText().toString());
    }

    public void setXText(String str) {
        if (TextUtils.isEmpty(str) && !TextUtils.isEmpty(nullShow)) {
            str = nullShow;
        }
        if (!TextUtils.isEmpty(str)) {
            if (frontColor != 0 && afterColor != 0) {
                super.setText(Html.fromHtml("<font color=" + frontColor + ">" + frontValue + "</font><font color=" + afterColor + ">" + afterValue + "</font>" + str + ""));
            } else if (frontColor != 0) {
                super.setText(Html.fromHtml("<font color=" + frontColor + ">" + frontValue + "</font>" + str + ""));
            } else if (afterColor != 0) {
                super.setText(Html.fromHtml("<font color=" + afterColor + ">" + afterValue + "</font>" + str + ""));
            } else {
                super.setText(frontValue + str + afterValue + "");
            }
        }
    }

}
