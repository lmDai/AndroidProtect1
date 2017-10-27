package com.cqyanyu.backing.ui.activity.login;

import android.Manifest;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.event.EventBadgeItem;
import com.cqyanyu.backing.ui.activity.base.BaseActivity;
import com.cqyanyu.backing.ui.activity.home.SetDetailsActivity;
import com.cqyanyu.backing.ui.activity.my.ModifyPasswordActivity;
import com.cqyanyu.backing.ui.entity.home.FireKnowledgeEntity;
import com.cqyanyu.backing.ui.entity.home.VersionEntity;
import com.cqyanyu.backing.ui.fragment.WarnFragment;
import com.cqyanyu.backing.ui.mvpview.login.MainView;
import com.cqyanyu.backing.ui.net.XHttpUtils;
import com.cqyanyu.backing.ui.net.XIDownCallback;
import com.cqyanyu.backing.ui.presenter.login.MainPresenter;
import com.cqyanyu.backing.ui.server.MyServer;
import com.cqyanyu.backing.ui.socket.entity.NotificationEntity;
import com.cqyanyu.backing.utils.DialogUtils;
import com.cqyanyu.backing.utils.DownloadAppUtils;
import com.cqyanyu.backing.utils.Utils;
import com.cqyanyu.mvpframework.adapter.XFragmentPagerAdapter;
import com.cqyanyu.mvpframework.utils.PermissionUtil;
import com.cqyanyu.mvpframework.utils.XLog;
import com.cqyanyu.mvpframework.utils.XPreferenceUtil;
import com.cqyanyu.mvpframework.utils.XToastUtil;
import com.cqyanyu.mvpframework.view.viewPager.XViewPager;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


/**
 * 主页
 * Created by Administrator on 2017/7/6.
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainView, Observer {
    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
            , Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private final int REQ_SCAN = 0x123;
    //页面显示
    private XViewPager viewPager;
    //导航栏
    private BottomNavigationBar navigationBar;
    //定义一个变量，来标识是否退出
    private boolean isExit = false;
    //最后一次按钮位置
    private int lastSelectedPosition;
    //UI更新
    private Handler mHandler;
    //碎片列表
    private ArrayList<Fragment> fList;
    private String url;
    private BadgeItem mBadgeItem;
    private int num = 0;
    Handler itemHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    //完成主界面更新,拿到数据
//                    int data = (int) msg.obj;
                    setBadgeNum(num + 1);
                    break;
                default:
                    break;
            }
        }

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openLocal();
        PermissionUtil.initPermission(mContext, PERMISSIONS);
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        viewPager = (XViewPager) findViewById(R.id.view_pager);
        navigationBar = (BottomNavigationBar) findViewById(R.id.navigation_bar);
    }

    @Override
    protected void initListener() {
        if (TextUtils.equals(CommonInfo.getInstance().getUserInfo().getPhone(), CommonInfo.getInstance().getUserInfo().getPassword())) {
            if (!XPreferenceUtil.getInstance().getBoolean("save_state_is_modify_password")) {
                XPreferenceUtil.getInstance().setBoolean("save_state_is_modify_password", true);
                DialogUtils.getCommonDialog(this, "提示", "你的密码为默认密码！是否修改？", "", "", new DialogUtils.OnDialogOperationListener() {
                    @Override
                    public void onDialogOperation(DialogUtils.Operation operation) {
                        if (operation == DialogUtils.Operation.SURE) {
                            startActivity(new Intent(mContext, ModifyPasswordActivity.class));
                        }
                    }
                }).show();
            }
        }
    }

    @Override
    protected void initData() {
        EventBadgeItem.getInstance().register(this);
        if (mPresenter != null) mPresenter.init();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                isExit = false;
            }
        };
        mPresenter.checkAppUpdate();
        mPresenter.getVoice();
        mPresenter.uploadLog();
        //自动更新
//        AutoUpdate.uiUpdateAction(this, new UICheckUpdateCallback() {
//            @Override
//            public void onCheckComplete() {
//
//            }
//        });
    }

    @Override
    protected void onResume() {
        //初始化数据
        sendBroadcast(new Intent().setAction("com.backing.broad_call_service")
                .putExtra(MyServer.FLAG, MyServer.FLAG_VALUE_LOCAL)
        );
        gotoFragment();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (!isExit) {
            isExit = true;
            XToastUtil.showToast("再按一次退出程序");
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            //System.exit(0);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String result = data.getStringExtra("result");
                startActivity(new Intent(mContext, SetDetailsActivity.class)
                        .putExtra(SetDetailsActivity.KEY_CONTENT, result)
                );
            }
        }
    }

    @Override
    public void setAllInspectionRate(float rate) {
        startActivityForResult(new Intent(mContext, CaptureActivity.class)
                        .putExtra("progress", rate)
                , REQ_SCAN);
    }

    @Override
    public void setViewPager(ArrayList<Fragment> fList) {
        this.fList = fList;
        //初始化XViewPager
        viewPager.setScrollble(false);
        viewPager.setPagingEnabled(false);
        viewPager.setOffscreenPageLimit(fList.size());
        viewPager.setAdapter(new XFragmentPagerAdapter(getFragmentManager(), fList));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (lastSelectedPosition != position) {
                    lastSelectedPosition = position;
                    navigationBar.selectTab(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void setNavigationBar(ArrayList<BottomNavigationItem> bList) {
        for (BottomNavigationItem item : bList) {
            navigationBar.addItem(item);
        }
        mBadgeItem = new BadgeItem()
                .setBorderWidth(4)
                .setAnimationDuration(200)
                .setBackgroundColorResource(R.color.bar1)
                .setHideOnSelect(false)
                .setText(String.valueOf(num));
        mBadgeItem.hide();
        bList.get(1).setBadgeItem(mBadgeItem);
        navigationBar
                .setMode(BottomNavigationBar.MODE_FIXED);
        navigationBar
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        navigationBar
                .setBackgroundResource(R.color.white);
        navigationBar
                .setInActiveColor(R.color.color_hint);
        navigationBar
                .setActiveColor(R.color.colorDark);
        navigationBar
                .setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(int position) {
                        if (lastSelectedPosition != position) {
                            lastSelectedPosition = position;
                            viewPager.setCurrentItem(position);
                        }
                        if (position == 1) {
                            setBadgeNum(0);
                        }
                    }

                    @Override
                    public void onTabUnselected(int position) {

                    }

                    @Override
                    public void onTabReselected(int position) {

                    }
                });
        navigationBar
                .setFirstSelectedPosition(lastSelectedPosition)
                .initialise();
    }

    /**
     * 设置tab数字提示加缩放动画
     */
    private void setBadgeNum(int num) {
        this.num = num;
        mBadgeItem.setText(String.valueOf(num));
        XLog.i("bottom" + num);
        if (num == 0) {
            mBadgeItem.hide();
        } else {
            mBadgeItem.show();
        }
    }

    @Override
    public void returnUpdate(final VersionEntity entity) {
        if (TextUtils.equals("true", entity.getRet())) {
            /**解析bug*/
            String[] split = entity.getExp().split(";");
            String bug = "";
            String fileName = "";
            for (String path : split) {
                if (!TextUtils.isEmpty(path)) {
                    bug = bug + path + ";\n";
                }
            }
            /**
             * 获取安装包名称
             */
            int start = entity.getUrl().lastIndexOf("/");
            if (start != -1) {
                fileName = entity.getUrl().substring(start + 1);
            }
            showDialog(entity.getVer(), bug, entity.getUrl(), fileName);
        }
    }

    @Override
    public void getVoice(FireKnowledgeEntity entity) {
        downloadFile(entity.getFilepath());
    }

    /**
     * 这是兼容的 AlertDialog
     */
    private void showDialog(final String title, String msg, final String url, final String apkName) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogAlertSelf);
        builder.setTitle("发现新版本" + title + ",是否更新?");
        builder.setMessage(msg);
        builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("现在更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (PermissionUtil.isPermission(mContext, PERMISSIONS, "读取存储卡"))
                /**调用系统自带的浏览器去下载最新apk*/

                    DownloadAppUtils.downloadForAutoInstall(mContext, ConstHost.LOCAL_HOST + url, apkName, title);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * 前往二维码页面
     */
    public void gotoScan() {
        if (PermissionUtil.initPermission(mContext, PERMISSIONS)) {
            if (mPresenter != null) mPresenter.getInspectionRate();
        }
    }

    /**
     * 前往哪一个fragment
     */
    private void gotoFragment() {
        NotificationEntity stickyEvent = EventBus.getDefault().getStickyEvent(NotificationEntity.class);
        if (stickyEvent != null) {
            int type = stickyEvent.getType();
            if (type > 0 && type <= 4 && fList.size() > 1) {
                /**前往告警内容页面*/
                Fragment fragment = fList.get(1);
                if (fragment instanceof WarnFragment) {
                    WarnFragment warnFragment = (WarnFragment) fragment;
                    warnFragment.gotoFragment(type - 1);
                }
                /**前往告警页面*/
                lastSelectedPosition = 1;
                viewPager.setCurrentItem(1);
                navigationBar.selectTab(1);
            }
        }
        EventBus.getDefault().removeStickyEvent(NotificationEntity.class);
    }


    /**
     * 获取本地文件地址
     */
    private void downloadFile(String url) {
        this.url = url;
        String names = "down_" + url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
        String realName = null;
        if (!TextUtils.isEmpty(XPreferenceUtil.getInstance().getString("voice"))) {
            realName = XPreferenceUtil.getInstance().getString("voice").substring(
                    XPreferenceUtil.getInstance().getString("voice").lastIndexOf("/") + 1,
                    XPreferenceUtil.getInstance().getString("voice").lastIndexOf("."));
        }
        if (!TextUtils.equals(names, realName) || !Utils.fileIsExists(XPreferenceUtil.getInstance().getString("voice")))
            XHttpUtils.onDownloadFile(this, url, "wav", new XIDownCallback() {
                @Override
                public void onSuccess(String path) {
                    XPreferenceUtil.getInstance().setString("voice", path);
                }

                @Override
                public void onFail(String msg) {

                }

                @Override
                public void onFinished() {

                }
            });
    }

    /**
     * 文件保存权限
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (XHttpUtils.downPermission(this, false)) {
            downloadFile(url);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                //耗时操作，完成之后发送消息给Handler，完成UI更新；
//                itemHandler.sendEmptyMessage(0);
                //需要数据传递，用下面方法；
                Message msg = new Message();
                msg.obj = num;//可以是基本类型，可以是对象，可以是List、map等；
                itemHandler.sendMessage(msg);
            }

        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBadgeItem.getInstance().unregister(this);
    }
}
