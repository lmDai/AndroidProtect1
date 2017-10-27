package com.pictureselect.camerapicker.tools;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import java.io.File;

/**
 * Created by Lin on 2017/7/26.  UriUtils.file2Uri
 */
public class UriUtils {

    public static Uri file2Uri(Context context, File file){
        Uri uri=null;
        if (Build.VERSION.SDK_INT > 23) {
            try {
                /**Android 7.0以上的方式**/
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
                uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
//          uri = Uri.  fromFile(file);
            String url =  "file://" + file.getAbsolutePath();
            uri=Uri.parse(url);
      }
        return uri;
    }
}
