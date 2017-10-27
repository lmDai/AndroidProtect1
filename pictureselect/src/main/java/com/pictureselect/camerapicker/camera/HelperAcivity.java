package com.pictureselect.camerapicker.camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Lin on 2016/12/19.
 */

public class HelperAcivity extends Activity {


    public final static String PUT_FILES="files";
    public final static String PUT_MODE="mode";

    public final static String PUT_LISTEN_ID="listen";
    public final static String PUT_MAX_FILE_COUNT="MaxFileCount";
    public final static String PUT_CUT="isCut";

    public final static Map<String,OnPhotoPickFinsh> map=new HashMap<String,OnPhotoPickFinsh>();
    private PhotoPickManger pickManger;
    private OnPhotoPickFinsh listen;
    private int maxFileCount;
    private boolean isCut;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        pickManger.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        isCut=Boolean.parseBoolean(getIntent().getStringExtra(PUT_CUT));
        pickManger.setCut(isCut);
        pickManger.onSaveInstanceState(outState);

    }

    //当销毁时
    @Override
    protected void onDestroy() {
        super.onDestroy();
        map.remove(getIntent().getStringExtra(PUT_LISTEN_ID));
    }
//    /**返回时销毁该UI*/
//    int resumeCount=0;
//    @Override
//    protected void onResume() {
//        super.onResume();
//        resumeCount++;
//        if(isCut==null)
//        if(resumeCount>1)
//        {
//            map.remove(getIntent().getStringExtra(PUT_LISTEN_ID));
//            finish();
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pickManger = new PhotoPickManger("pick", this, savedInstanceState, new OnPhotoPickFinsh() {
            @Override
            public void onPhotoPick(List<File> list) {
                if(listen!=null)listen.onPhotoPick(list);
                finish();
            }
        });
        if(savedInstanceState==null){
            //如果当前作为首次正常启动
            List<File> files= (List<File>) getIntent().getSerializableExtra(PUT_FILES);
            if(files!=null&&!files.isEmpty()) {
                pickManger.getSelectsPhotos().addAll(files);
            }
            listen=map.get(getIntent().getStringExtra(PUT_LISTEN_ID));
            pickManger.start((Mode) getIntent().getSerializableExtra(PUT_MODE));
            maxFileCount=getIntent().getIntExtra(PUT_MAX_FILE_COUNT,1);
        }
    }

    public static void start(Context context, Mode mode, ArrayList<File> list, OnPhotoPickFinsh listen, int maxFileCount,boolean isCut){
        Intent intent=new Intent(context,HelperAcivity.class);
        intent.putExtra(HelperAcivity.PUT_FILES,list);
        intent.putExtra(HelperAcivity.PUT_MODE, mode);
        intent.putExtra(HelperAcivity.PUT_MAX_FILE_COUNT, maxFileCount);
        intent.putExtra(HelperAcivity.PUT_CUT,String.valueOf(isCut));
        String id= UUID.randomUUID().toString();
        HelperAcivity.map.put(id,listen);
        intent.putExtra(HelperAcivity.PUT_LISTEN_ID,id);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void start(Context context, Mode mode,OnPhotoPickFinsh listen,boolean isCut){
        Intent intent=new Intent(context,HelperAcivity.class);
        intent.putExtra(HelperAcivity.PUT_FILES,new ArrayList<File>());
        intent.putExtra(HelperAcivity.PUT_MODE, mode);
        intent.putExtra(HelperAcivity.PUT_CUT,String.valueOf(isCut));
        String id= UUID.randomUUID().toString();
        HelperAcivity.map.put(id,listen);
        intent.putExtra(HelperAcivity.PUT_LISTEN_ID,id);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    };
}
