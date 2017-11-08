package com.cqyanyu.backing.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baidu.mapapi.search.sug.SuggestionResult;
import com.cqyanyu.backing.R;

import java.util.List;

/**
 * 百度地图在线建议查询
 * 适配器
 * Created by personal on 2017/10/9.
 */

public class SuggestionAdapter extends BaseAdapter {
    private Context context;
    private List<SuggestionResult.SuggestionInfo> poiData;

    public SuggestionAdapter(Context context, List<SuggestionResult.SuggestionInfo> poiData) {
        this.context = context;
        this.poiData = poiData;
    }

    @Override
    public int getCount() {
        return poiData.size();
    }

    /**
     * 设置数据
     *
     * @param mList 集合
     */
    public void setList(@NonNull List mList) {
        this.poiData = mList;
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        return poiData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        SuggestionResult.SuggestionInfo poiInfo = poiData.get(position);
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.address_item, null);
            holder = new ViewHolder();
            holder.poiName = (TextView) convertView.findViewById(R.id.poi_name);
            holder.poiAddress = (TextView) convertView.findViewById(R.id.poi_address);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.poiName.setText(poiInfo.key);
        holder.poiAddress.setText(poiInfo.city);
        return convertView;
    }

    static class ViewHolder {
        TextView poiName;
        TextView poiAddress;
    }
}
