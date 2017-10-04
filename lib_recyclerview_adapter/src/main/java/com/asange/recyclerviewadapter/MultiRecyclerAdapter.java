package com.asange.recyclerviewadapter;

/**
 * Description 多布局的适配器
 * Company Beijing icourt
 * author  youxuan  E-mail:xuanyouwu@163.com
 * date createTime：2017/10/4
 * version 2.1.0
 */
public abstract class MultiRecyclerAdapter<T extends MultiViewEntity> extends BaseRecyclerAdapter<T> {
    @Override
    public final int getViewType(int index) {
        T item = getItem(index);
        return item != null ? item.getViewType() : super.getViewType(index);
    }
}
