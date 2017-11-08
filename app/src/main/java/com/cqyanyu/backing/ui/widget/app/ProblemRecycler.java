package com.cqyanyu.backing.ui.widget.app;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.cqyanyu.backing.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择问题
 * Created by Administrator on 2017/7/22.
 */
public class ProblemRecycler extends RecyclerView {
    private ProblemAdapter adapter;
    private List<String> problemNames;
    private List<String> problemIds;
    private int selectPosition;
    private OnMyClickListener listener;

    public ProblemRecycler(Context context) {
        this(context, null);
    }

    public ProblemRecycler(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        //初始化数据
        if (problemNames == null) problemNames = new ArrayList<>();
        if (problemIds == null) problemIds = new ArrayList<>();
        //创建默认的线性GridLayoutManager
        this.setLayoutManager(new LinearLayoutManager(getContext()));
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        this.setHasFixedSize(true);
        //创建并设置Adapter
        adapter = new ProblemAdapter();
        this.setAdapter(adapter);
    }

    /**
     * 设置数据
     *
     * @param listName 名称列表
     * @param listId   id列表
     */
    public void setDataList(List<String> listName, List<String> listId) {
        if (listName != null && listName.size() > 0 &&
                listId != null && listId.size() > 0 &&
                listName.size() == listId.size()) {
            problemNames = listName;
            problemIds = listId;
        }
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public void clearData() {
        if (problemNames != null && problemIds != null) {
            problemNames.clear();
            problemIds.clear();
            selectPosition = 0;
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 获取选中的id
     *
     * @return id
     */
    public String getSelectId() {
        if (problemIds != null && problemIds.size() > selectPosition) {
            return problemIds.get(selectPosition);
        } else {
            return "";
        }
    }

    /**
     * 设置监听
     *
     * @param listener 监听
     */
    public void setOnMyClickListener(OnMyClickListener listener) {
        this.listener = listener;
    }

    public interface OnMyClickListener {
        void onClick(String id, String name);
    }

    private class ProblemAdapter extends Adapter<ProblemHolder> {

        @Override
        public ProblemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_problem, parent, false);
            return new ProblemHolder(view);
        }

        @Override
        public void onBindViewHolder(ProblemHolder holder, int position) {
            holder.onBindData(position);
        }

        @Override
        public int getItemCount() {
            return problemNames == null ? 0 : problemNames.size();
        }
    }

    class ProblemHolder extends ViewHolder implements OnClickListener {
        private CheckBox cbSelect;
        private int position;

        public ProblemHolder(View itemView) {
            super(itemView);
            cbSelect = (CheckBox) itemView.findViewById(R.id.cb_select);
            itemView.setOnClickListener(this);
            cbSelect.setOnClickListener(this);
        }

        /**
         * 绑定数据
         *
         * @param position item项
         */
        public void onBindData(int position) {
            this.position = position;
            cbSelect.setChecked(selectPosition == position);
            cbSelect.setText(problemNames.get(position));
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.cb_select:
                    break;
                default:
                    if (!cbSelect.isChecked()) {
                        cbSelect.setChecked(true);
                    }
                    break;
            }
            selectPosition = position;
            if (listener != null) {
                if (problemIds != null && problemIds.size() > selectPosition &&
                        problemNames != null && problemNames.size() > selectPosition) {
                    listener.onClick(problemIds.get(selectPosition), problemNames.get(selectPosition));
                }
            }
            adapter.notifyDataSetChanged();
        }
    }
}
