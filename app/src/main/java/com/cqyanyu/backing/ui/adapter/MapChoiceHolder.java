package com.cqyanyu.backing.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.adapter.base.XViewHolder;
import com.cqyanyu.backing.ui.entity.home.MapChoiceBean;

/**
 * 选择地图holder
 * Created by Administrator on 2017/7/17.
 */
public class MapChoiceHolder extends XViewHolder<MapChoiceBean> {

    private TextView tvName;
    private TextView tvDescribe;
    private CheckBox cbChoice;

    public MapChoiceHolder(ViewGroup viewGroup) {
        super(viewGroup, R.layout.item_map_choice);
    }

    @Override
    public void onBindView(View rootView) {
        tvName = (TextView) rootView.findViewById(R.id.tv_name);
        tvDescribe = (TextView) rootView.findViewById(R.id.tv_describe);
        cbChoice = (CheckBox) rootView.findViewById(R.id.cb_choice);
        cbChoice.setOnClickListener(this);
    }

    @Override
    public void onBindData(MapChoiceBean itemEntity) {
        tvName.setText(itemEntity.getName());
        tvDescribe.setText(itemEntity.getDescribe());
        cbChoice.setChecked(itemEntity.isChecked());
    }

    @Override
    public void onClick(View view) {
        if (mList != null && itemEntity != null) {
            for (MapChoiceBean bean : mList) {
                bean.setChecked(false);
            }
            itemEntity.setChecked(true);
            adapter.notifyDataSetChanged();
            if (listener != null) listener.onClick(position);
        }
    }
}
