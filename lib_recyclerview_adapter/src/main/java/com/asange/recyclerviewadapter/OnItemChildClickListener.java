package com.asange.recyclerviewadapter;

import android.view.View;

public interface OnItemChildClickListener {

    /**
     *
     * @param adapter
     * @param holder
     * @param childView
     * @param index 相对于List容器的位置
     */
    void onItemChildClick(BaseRecyclerAdapter adapter, BaseRecyclerAdapter.BaseViewHolder holder, View childView, int index);
}