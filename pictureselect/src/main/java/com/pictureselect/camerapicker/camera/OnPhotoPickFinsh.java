package com.pictureselect.camerapicker.camera;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Lin on 2016/12/19.
 */

public interface OnPhotoPickFinsh extends Serializable {
    public void onPhotoPick(List<File> list);

}
