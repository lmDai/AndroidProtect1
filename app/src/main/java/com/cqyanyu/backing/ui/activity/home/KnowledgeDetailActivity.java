package com.cqyanyu.backing.ui.activity.home;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.base.BaseActivity;
import com.cqyanyu.backing.ui.entity.home.FireKnowledgeEntity;
import com.cqyanyu.backing.ui.net.XHttpUtils;
import com.cqyanyu.backing.ui.net.XIDownCallback;
import com.cqyanyu.backing.ui.widget.app.XTextView;
import com.cqyanyu.mvpframework.presenter.BasePresenter;
import com.cqyanyu.mvpframework.utils.XDateUtil;
import com.lidong.pdf.PDFView;
import com.lidong.pdf.listener.OnDrawListener;
import com.lidong.pdf.listener.OnLoadCompleteListener;
import com.lidong.pdf.listener.OnPageChangeListener;

import java.io.File;

public class KnowledgeDetailActivity extends BaseActivity {
    private String url;
    private PDFView pdfView;
    private FireKnowledgeEntity fireKnowledgeEntity;
    private XTextView tvTitle;
    private XTextView tvTime;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_knowledge_detail;
    }

    @Override
    protected void initView() {
        pdfView = (PDFView) findViewById(R.id.pdfView);
        tvTitle = (XTextView) findViewById(R.id.tv_title);
        tvTime = (XTextView) findViewById(R.id.tv_time);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        setTopTitle("消防安全常识");
        fireKnowledgeEntity = (FireKnowledgeEntity) getIntent().getSerializableExtra("fireknowledge");
        tvTitle.setXText(fireKnowledgeEntity.getFilename());
        tvTime.setXText(XDateUtil.getStringByFormat(fireKnowledgeEntity.getDate() * 1000, "yyyy年MM月dd日"));
        downloadFile(fireKnowledgeEntity.getFilepath());
    }

    /**
     * 获取本地文件地址
     *
     * @param url 网络地址
     */
    private void downloadFile(String url) {
        this.url = url;
        XHttpUtils.onDownloadFile(this, url, "pdf", new XIDownCallback() {
            @Override
            public void onSuccess(String path) {
                if (!TextUtils.isEmpty(path) && new File(path).exists()) {
                    displayFromFile(path);
                }
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
     * 获取打开网络的本地pdf文件
     *
     * @param fileUrl 本地pdf文件地址
     */
    private void displayFromFile(String fileUrl) {
        pdfView.fromFile(new File(fileUrl)).defaultPage(1)         //设置默认显示第1页
                .onPageChange(new OnPageChangeListener() {
                    @Override
                    public void onPageChanged(int page, int pageCount) {

                    }
                })     //设置翻页监听
                .onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {

                    }
                })           //设置加载监听
                .onDraw(new OnDrawListener() {
                    @Override
                    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

                    }
                })            //绘图监听
                .showMinimap(false)     //pdf放大的时候，是否在屏幕的右上角生成小地图
                .swipeVertical(false)  //pdf文档翻页是否是垂直翻页，默认是左右滑动翻页
                .enableSwipe(true)   //是否允许翻页，默认是允许翻
                .load();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (XHttpUtils.downPermission(this, false)) {
            downloadFile(url);
        }
    }
}
