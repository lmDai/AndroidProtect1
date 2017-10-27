package com.cqyanyu.backing.ui.activity.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.cqyanyu.backing.Constant;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.base.BaseActivity;
import com.cqyanyu.backing.ui.adapter.AcountAdapter;
import com.cqyanyu.backing.ui.adapter.CallBackClick;
import com.cqyanyu.backing.ui.entity.login.UserData;
import com.cqyanyu.backing.ui.mvpview.login.LoginView;
import com.cqyanyu.backing.ui.presenter.login.LoginPresenter;
import com.cqyanyu.backing.ui.server.MyServer;
import com.cqyanyu.backing.ui.socket.SocketServer;
import com.cqyanyu.backing.ui.widget.app.ClearableEditText;
import com.cqyanyu.mvpframework.utils.StatusBarUtil;
import com.cqyanyu.mvpframework.utils.XLog;
import com.cqyanyu.mvpframework.utils.XPreferenceUtil;
import com.cqyanyu.mvpframework.utils.XScreenUtils;
import com.cqyanyu.mvpframework.utils.XToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 登陆页
 * Created by Administrator on 2017/5/15 0015.
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginView {

    private ClearableEditText etUsername;
    private ClearableEditText etPassword;
    private Button btnLogin;
    private CheckBox cbRemember;
    private TextView tvForget;
    private boolean autoLogin;
    private AcountAdapter adapter;
    private List<UserData> lists;
    private String writeAcount;
    private String writePasswd;
    private PopupWindow window;
    private ListView listView;
    private ImageView muti;
    private Gson gson;
    private FileOutputStream out;
    private BufferedWriter writer;
    private FileInputStream in;
    private BufferedReader reader;

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        StatusBarUtil.transparencyBar(this);
        etUsername = (ClearableEditText) findViewById(R.id.et_username);
        etPassword = (ClearableEditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        cbRemember = (CheckBox) findViewById(R.id.cb_remember);
        tvForget = (TextView) findViewById(R.id.tv_forget);
        muti = (ImageView) findViewById(R.id.muti);
        listView = new ListView(mContext);
        lists = new ArrayList<>();
        gson = new Gson();
        try {
            //以防止没有创建时读取错误
            out = openFileOutput("user", Context.MODE_APPEND);
            in = openFileInput("user");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        writer = new BufferedWriter(new OutputStreamWriter(out));
        reader = new BufferedReader(new InputStreamReader(in));
        try {
            out.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initListener() {
        muti.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        tvForget.setOnClickListener(this);
        cbRemember.setChecked(XPreferenceUtil.getInstance().getBoolean(Constant.KEY_REMEMBER));
        cbRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //修改记住密码的状态
                XPreferenceUtil.getInstance().setBoolean(Constant.KEY_REMEMBER, b);
            }
        });
    }

    @Override
    protected void initData() {
        //先看下有没有以前保存的密码
        String data = openFile();
        if (!TextUtils.isEmpty(data) && !data.equals("[]")) {
            lists = gson.fromJson(data, new TypeToken<List<UserData>>() {
            }.getType());
            writeAcount = lists.get(0).getAcount();
            writePasswd = lists.get(0).getPasswd();
        }
        if (getIntent() != null) autoLogin = getIntent().getBooleanExtra("autoLogin", true);
        //初始化
        if (mPresenter != null) mPresenter.init(autoLogin);
        //开启定位服务
        openLocal();
        //设置用户名和密码
        String account = XPreferenceUtil.getInstance().getString(Constant.KEY_USERNAME);
        if (!TextUtils.isEmpty(account)) {
            etUsername.setText(account);

            if (XPreferenceUtil.getInstance().getBoolean(Constant.KEY_REMEMBER)) {
                String password = XPreferenceUtil.getInstance().getString(account);
                etPassword.setText(password);
            }
        }

        adapter = new AcountAdapter(mContext, R.layout.simple_item, lists, new CallBackClick() {
            @Override
            public void onClick(View v) {
                int index = (int) v.getTag();
                UserData user = lists.get((Integer) v.getTag());
                switch (v.getId()) {
                    case R.id.muti_acount:
                        writeAcount = user.getAcount();
                        writePasswd = user.getPasswd();
                        etUsername.setText(writeAcount);
                        etPassword.setText(writePasswd);
                        window.dismiss();
                        break;
                    case R.id.image_delete:
                        lists.remove(index);
                        //进行实时存储
                        nowStoge();
                        //通知适配器，数据发生改变了
                        adapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
            }
        });
        listView.setAdapter(adapter);
        window = new PopupWindow(listView, XScreenUtils.getScreenWidth(mContext) / 3 * 2, WindowManager.LayoutParams.WRAP_CONTENT, true);
        window.setTouchable(true);
        window.setOutsideTouchable(true);
        window.setBackgroundDrawable(new ColorDrawable());
    }

    /**
     * 进行密码的存储
     */
    public void stogeUser() {
        //判断用户是否输入了新的账号和密码
        if (lists.size() > 0) {
            for (int i=0;i<lists.size();i++){
                if (TextUtils.equals(lists.get(i).getAcount(), getUsername())) {
                    lists.remove(lists.get(i));
                }
            }
        }
        if (!lists.contains(new UserData(getUsername(), getPassword()))) {
            lists.add(new UserData(getUsername(), getPassword()));
            String data = gson.toJson(lists);
            try {
                //先进行数据清空
                out = openFileOutput("user", MODE_PRIVATE);
                writer = new BufferedWriter(new OutputStreamWriter(out));
                writer.write(data);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将缓存在lists中的数据进行存储
     */
    public void nowStoge() {
        try {
            out = openFileOutput("user", MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        writer = new BufferedWriter(new OutputStreamWriter(out));
        String data = gson.toJson(lists);
        try {
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login://登录
                mPresenter.requestLoin();
                break;

            case R.id.tv_forget://忘记密码
                startActivity(new Intent(mContext, ForgetPasswordActivity.class));
                break;
            case R.id.muti:
                window.showAsDropDown(v, muti.getWidth(), 0, Gravity.CENTER);
                break;
        }
    }

    /**
     * 读取文件
     */
    public String openFile() {
        StringBuilder builder = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return builder.toString();
    }

    @Override
    public String getUsername() {
        return etUsername.getText().toString();
    }

    @Override
    public String getPassword() {
        return etPassword.getText().toString();
    }

    @Override
    public void requestSuccess() {
        //开启通讯服务
        startService(new Intent(this, SocketServer.class));
        startService(new Intent(this, MyServer.class));
        CloudPushService cloudPushService = PushServiceFactory.getCloudPushService();
        cloudPushService.turnOnPushChannel(new CommonCallback() {
            @Override
            public void onSuccess(String s) {
                XLog.i("turnOnPushChannel-->阿里推送");
            }

            @Override
            public void onFailed(String s, String s1) {

            }
        });
        //初始化数据
        sendBroadcast(new Intent().setAction("com.backing.broad_call_service")
                .putExtra(MyServer.FLAG, MyServer.FLAG_VALUE_INIT)
        );
        //保存用户名和密码
        XPreferenceUtil.getInstance().setString(Constant.KEY_USERNAME, getUsername());
        if (XPreferenceUtil.getInstance().getBoolean(Constant.KEY_REMEMBER)) {
            XPreferenceUtil.getInstance().setString(getUsername(), getPassword());
            stogeUser();
        }
        //前往首页
        startActivity(new Intent(mContext, MainActivity.class));
        finish();
    }

    @Override
    public void loginFail(String info) {
        XToastUtil.showToast(info);
    }
}
