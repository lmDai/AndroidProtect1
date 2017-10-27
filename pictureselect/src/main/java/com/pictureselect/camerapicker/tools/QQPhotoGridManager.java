package com.pictureselect.camerapicker.tools;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridView;

import java.io.File;
import java.util.List;

import com.pictureselect.R;
import com.pictureselect.camerapicker.view.SquareImageView;

/**
 * Created by Lin on 16/5/18.
 */

public class QQPhotoGridManager extends PhotoGridManager {

    /**
     * xianghuan
     */
    private boolean isShakeAnim = false;

    //
    private int drawableAdd;
    private int drawableRest;
    private int drawableDel;

    private OnItemAction onItemAddAction;
    private OnItemAction onItemDelAction;
//
//    private List<File> files;

    public QQPhotoGridManager(GridView gridView, int maxCount, int numColumns) {
        this(gridView, null, maxCount, numColumns);
    }

    public QQPhotoGridManager(GridView gridView, List<File> files, int maxCount, int numColumns) {
        super(gridView, files, maxCount, numColumns);
        initAdaper();
        gridView.setAdapter(adapter);

//        this.gridView = gridView;
//        this.maxCount = maxCount;
//        this.numColumns = numColumns;
//        gridView.setNumColumns(numColumns);
//        this.context = gridView.getContext();
//        this.files = files;
//        if(this.files==null) {
//            this.files = new ArrayList<>();
//        }
//        galleryDialog = new GalleryDialog(context);
//        galleryDialog.setOnDeleteFileisten(new GalleryDialog.OnDeleteFileListen() {
//            @Override
//            public void onDeleteFile(List<File> files, File file, int position) {
//                adapter.notifyDataSetChanged();
//            }
//        });
//        initAdaper();
//        gridView.setAdapter(adapter);


    }

    private void initAdaper() {
        adapter = new SimpleGenericAdapter<Uri>(context, urls, layoutView) {

            @Override
            public int getCount() {
                int temp = urls.size() + 1;
                return temp > maxCount ? maxCount : temp;
            }


            @Override
            public ViewHolder getViewHolder(final Uri uri) {
                return new ViewHolder(uri) {
                    View view;
                    SquareImageView siv;
                    Button del;

                    @Override
                    public ViewHolder getHolder(View view) {
                        this.view = view;
                        siv = (SquareImageView) view.findViewById(R.id.image);
                        del = (Button) view.findViewById(R.id.btn_del);
                        del.setVisibility(View.GONE);
                        del.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (getPositopn() < urls.size()) {
                                    urls.remove(getPositopn());
                                    notifyDataSetChanged();
                                    if (onItemDelAction != null) {
                                        onItemDelAction.onItemAciton(QQPhotoGridManager.this);
                                    }
                                }
                            }
                        });
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (getPositopn() >= urls.size() && getPositopn() < maxCount) {
                                    if (onItemAddAction != null) {
                                        onItemAddAction.onItemAciton(QQPhotoGridManager.this);
                                    }
                                } else {
                                    if (!isShakeAnim) {
                                        galleryDialog.startUri(urls, getPositopn(), true);
                                    }
                                }
                            }
                        });
                        view.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                isShakeAnim = true;
                                notifyDataSetChanged();
                                return false;
                            }
                        });
                        return this;
                    }

                    @Override
                    public void show() {
                        if (getPositopn() == urls.size()) {
                            SimpleImageLoader.displayFromDrawable(drawableAdd, siv);
                        } else if (getPositopn() > urls.size()) {
                            SimpleImageLoader.displayFromDrawable(drawableDel, siv);
                        } else {
                            if (getPositopn() < urls.size()) {
                                SimpleImageLoader.displayImage(urls.get(getPositopn()).toString(), siv);
                            }
                        }
                        if (isShakeAnim) {
                            if (getPositopn() < urls.size()) {
                                Animation anim = AnimationUtils.loadAnimation(context, R.anim.shake);
                                view.startAnimation(anim);
                                del.setVisibility(View.VISIBLE);
                                del.setBackgroundResource(drawableDel);

                            } else {
                                del.setVisibility(View.GONE);
                                view.clearAnimation();
                            }
                        } else {
                            del.setVisibility(View.GONE);
                            view.clearAnimation();
                        }

                    }
                };
            }
        };
    }

    public int getMaxCount() {
        return maxCount;
    }

    public QQPhotoGridManager setMaxCount(int maxCoun) {
        this.maxCount = maxCoun;
        return this;
    }

    public Context getContext() {
        return context;
    }

    public QQPhotoGridManager setContext(Context context) {
        this.context = context;
        return this;
    }

    public GridView getGridView() {
        return gridView;
    }

    public QQPhotoGridManager setGridView(GridView gridView) {
        this.gridView = gridView;
        return this;
    }

    public int getLayoutView() {
        return layoutView;
    }

    public QQPhotoGridManager setLayoutView(int layoutView) {
        this.layoutView = layoutView;
        return this;
    }

    public SimpleGenericAdapter<Uri> getAdapter() {
        return adapter;
    }

    public QQPhotoGridManager setAdapter(SimpleGenericAdapter<Uri> adapter) {
        this.adapter = adapter;
        return this;
    }

    public GalleryDialog getGalleryDialog() {
        return galleryDialog;
    }

    public QQPhotoGridManager setGalleryDialog(GalleryDialog galleryDialog) {
        this.galleryDialog = galleryDialog;
        return this;
    }

    public boolean isShakeAnim() {
        return isShakeAnim;
    }

    public QQPhotoGridManager setShakeAnim(boolean isShakeAnim) {
        if (this.isShakeAnim() != isShakeAnim) {
            getAdapter().notifyDataSetChanged();
        }
        this.isShakeAnim = isShakeAnim;
        return this;
    }


    public int getNumColumns() {
        return numColumns;
    }

    public QQPhotoGridManager setNumColumns(int numColumns) {
        this.numColumns = numColumns;
        return this;
    }


    public int getDrawableAdd() {
        return drawableAdd;
    }

    public QQPhotoGridManager setDrawableAdd(int drawableAdd) {
        this.drawableAdd = drawableAdd;
        return this;
    }

    public int getDrawableRest() {
        return drawableRest;
    }

    public QQPhotoGridManager setDrawableRest(int drawableRest) {
        this.drawableRest = drawableRest;
        return this;
    }

    public int getDrawableDel() {
        return drawableDel;
    }

    public QQPhotoGridManager setDrawableDel(int drawableDel) {
        this.drawableDel = drawableDel;
        return this;
    }

    public OnItemAction getOnItemAddAction() {
        return onItemAddAction;
    }

    public QQPhotoGridManager setOnItemAddAction(OnItemAction onItemAddAction) {
        this.onItemAddAction = onItemAddAction;
        return this;
    }

    public OnItemAction getOnItemDelAction() {
        return onItemDelAction;
    }

    public QQPhotoGridManager setOnItemDelAction(OnItemAction onItemDelAction) {
        this.onItemDelAction = onItemDelAction;
        return this;
    }
}

