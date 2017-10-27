package com.pictureselect.camerapicker.camera;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lin on 2016/12/20.
 */

public class PictureSelector {

    private PictureSelector(){};
    public static PictureSelector create(Context context, Mode mode){
        return new PictureSelector().setContext(context).setMode(mode).setCut(false);
    }
    public static PictureSelector create(Context context, Mode mode,boolean isCut){
        return new PictureSelector().setContext(context).setMode(mode).setCut(isCut);
    }

    public  PictureSelector start(){
        //因为List不是Serializable 用ArrayList来传递
        ArrayList<File> fileArrayList=new ArrayList<>();
        if(files!=null) {
            fileArrayList.addAll(files);
        }
        HelperAcivity.start(context,mode,fileArrayList,listen,maxFileCount,isCut);
        return this;
    }

    private Context context;
    private Mode mode;
    private List<File> files;
    private OnPhotoPickFinsh listen;
    private int maxFileCount;
    private boolean isCut;

    public Context getContext() {
        return context;
    }

    public PictureSelector setContext(Context context) {
        this.context = context;
        return this;
    }

    public Mode getMode() {
        return mode;
    }

    public PictureSelector setMode(Mode mode) {
        this.mode = mode;
        return this;
    }

    public List<File> getFiles() {
        return files;
    }

    public PictureSelector setFiles(List<File> files) {
        this.files = files;
        return this;
    }

    public OnPhotoPickFinsh getListen() {
        return listen;
    }

    public PictureSelector setListen(OnPhotoPickFinsh listen) {
        this.listen = listen;
        return this;
    }

    public int getMaxFileCount() {
        return maxFileCount;
    }

    public PictureSelector setMaxFileCount(int maxFileCount) {
        this.maxFileCount = maxFileCount;
        return this;
    }

    public boolean isCut() {
        return isCut;
    }

    public PictureSelector setCut(boolean cut) {
        isCut = cut;
        return this;
    }
}
