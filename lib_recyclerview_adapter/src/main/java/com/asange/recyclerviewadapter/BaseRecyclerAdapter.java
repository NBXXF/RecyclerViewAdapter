package com.asange.recyclerviewadapter;

import android.databinding.ViewDataBinding;
import android.support.annotation.CheckResult;
import android.support.annotation.IdRes;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName BaseRecyclerAdapter
 * Description index:相对于List集合的位置,position:表示在adapter中的位置
 * Company
 * author  youxuan  E-mail:xuanyouwu@163.com
 * date createTime：2015/9/10 10:05
 * version
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerAdapter.BaseViewHolder> {
    public static final View inflaterView(@LayoutRes int id, RecyclerView recyclerView) {
        return LayoutInflater.from(recyclerView.getContext())
                .inflate(id, recyclerView, false);
    }

    private static final int HEADER_VIEW_TYPE = -10000;
    private static final int FOOTER_VIEW_TYPE = -20000;
    private final List<View> mHeaders = new ArrayList<View>();
    private final List<View> mFooters = new ArrayList<View>();
    private List<T> dataList = new ArrayList<T>();

    public List<T> getData() {
        return dataList;
    }


    public int getHeaderCount() {
        return mHeaders.size();
    }


    public int getFooterCount() {
        return mFooters.size();
    }

    public BaseRecyclerAdapter(@Nullable List<T> data) {
        this.dataList = data == null ? new ArrayList<T>() : data;
    }

    public BaseRecyclerAdapter() {
        this(new ArrayList<T>());
    }

    public void addHeader(@NonNull View view) {
        if (view == null) {
            throw new IllegalArgumentException("You can't have a null header!");
        }
        mHeaders.add(view);
        notifyDataSetChanged();
    }

    public void addHeader(@LayoutRes int id, @NonNull RecyclerView recyclerView) {
        addHeader(inflaterView(id, recyclerView));
    }

    public void addFooter(@NonNull View view) {
        if (view == null) {
            throw new IllegalArgumentException("You can't have a null footer!");
        }
        mFooters.add(view);
        notifyDataSetChanged();
    }

    public void addFooter(@LayoutRes int id, @NonNull RecyclerView recyclerView) {
        addFooter(inflaterView(id, recyclerView));
    }

    @Nullable
    @CheckResult
    public View getHeader(int index) {
        return index < mHeaders.size() ? mHeaders.get(index) : null;
    }

    @Nullable
    @CheckResult
    public View getFooter(int index) {
        return index < mFooters.size() ? mFooters.get(index) : null;
    }

    private boolean isHeader(int viewType) {
        return viewType >= HEADER_VIEW_TYPE && viewType < (HEADER_VIEW_TYPE + mHeaders.size());
    }

    public boolean isHeader(View view) {
        return mHeaders.contains(view);
    }

    public boolean removeHeader(View view) {
        if (getHeaderCount() > 0) {
            if (mHeaders.contains(view)) {
                boolean remove = mFooters.remove(view);
                notifyDataSetChanged();
                return remove;
            }
        }
        return false;
    }

    public boolean removeFooter(View view) {
        if (getFooterCount() > 0) {
            if (mFooters.contains(view)) {
                boolean remove = mFooters.remove(view);
                notifyDataSetChanged();
                return remove;
            }
        }
        return false;
    }

    private boolean isFooter(int viewType) {
        return viewType >= FOOTER_VIEW_TYPE && viewType < (FOOTER_VIEW_TYPE + mFooters.size());
    }


    public boolean bindData(boolean isRefresh, @NonNull List<T> datas) {
        if (checkList(datas)) {
            if (isRefresh) {
                getData().clear();
                getData().addAll(datas);
                notifyDataSetChanged();
                return true;
            } else if (!getData().contains(datas)) {
                getData().addAll(datas);
                notifyDataSetChanged();
                return true;
            }
        }
        return false;
    }

    public void clearData() {
        getData().clear();
        notifyDataSetChanged();
    }

    /**
     * 检查index是否有效
     *
     * @param index
     * @return true 有效 false 无效
     */
    private boolean checkIndex(@IntRange(from = 0) int index) {
        return index >= 0 && index < dataList.size();
    }

    /**
     * 检查list是否有效
     *
     * @param datas
     * @return true 不空
     */
    private boolean checkList(List<? extends T> datas) {
        return datas != null && !datas.isEmpty();
    }

    /**
     * 检查对象是否有效
     *
     * @return true 不空
     */
    private boolean checkItem(T t) {
        return t != null;
    }

    /**
     * @param index 相对于List的位置
     * @return
     */
    public T getItem(@IntRange(from = 0) int index) {
        if (checkIndex(index)) {
            return getData().get(index);
        }
        return null;
    }

    /**
     * 获取角标
     *
     * @param t
     * @return
     */
    public int getIndex(@NonNull T t) {
        return getData().indexOf(t);
    }

    /**
     * @param index 相对于List的位置
     * @param t
     * @return
     */
    public final boolean addItem(@IntRange(from = 0) int index, @Nullable T t) {
        if (checkIndex(index)
                && checkItem(t)
                && !getData().contains(t)) {
            getData().add(index, t);
            int internalPosition = index + getHeaderCount();
            notifyItemInserted(internalPosition);
            return true;
        }
        return false;
    }

    public final boolean addItems(@IntRange(from = 0) int index, @NonNull List<? extends T> datas) {
        if (checkList(datas)
                && checkIndex(index)
                && !getData().containsAll(datas)) {
            if (getData().addAll(index, datas)) {
                int internalPosition = index + getHeaderCount();
                notifyItemRangeInserted(internalPosition, datas.size());
                return true;
            }
        }
        return false;
    }

    public final boolean addItems(@NonNull List<? extends T> datas) {
        if (checkList(datas)
                && !getData().containsAll(datas)) {
            int internalPosition = getData().size() + getHeaderCount();
            if (getData().addAll(datas)) {
                notifyItemRangeInserted(internalPosition, datas.size());
                return true;
            }
        }
        return false;
    }

    public final boolean addItem(@NonNull T t) {
        if (checkItem(t)
                && !getData().contains(t)) {
            int internalPosition = getData().size() + getHeaderCount();
            if (getData().add(t)) {
                notifyItemInserted(internalPosition);
            }
        }
        return false;
    }

    /**
     * 更新item
     *
     * @param t
     * @return
     */
    public final boolean updateItem(@NonNull T t) {
        if (checkItem(t)) {
            int index = getIndex(t);
            if (index >= 0) {
                getData().set(index, t);
                int internalPosition = index + getHeaderCount();
                notifyItemChanged(internalPosition);
                return true;
            }
        }
        return false;
    }

    /**
     * @param index 相对于List的位置
     * @return
     */
    public final boolean removeItem(@IntRange(from = 0) int index) {
        if (checkIndex(index)) {
            getData().remove(index);
            int internalPosition = index + getHeaderCount();
            notifyItemRemoved(internalPosition);
            return true;
        }
        return false;
    }


    /**
     * 移除item
     *
     * @param t
     * @return
     */
    public final boolean removeItem(@NonNull T t) {
        return removeItem(getIndex(t));
    }


    /**
     * 初始化布局
     *
     * @param viewType
     * @return
     */
    @LayoutRes
    public abstract int bindView(int viewType);

    /**
     * 初始化item
     *
     * @param holder
     * @param t
     * @param index  相对于List的位置
     */
    public abstract void onBindHolder(BaseViewHolder holder, @Nullable T t, int index);

    @Override
    public final BaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (isHeader(viewType)) {
            int whichHeader = Math.abs(viewType - HEADER_VIEW_TYPE);
            View headerView = mHeaders.get(whichHeader);
            return new BaseViewHolder(headerView, false);
        } else if (isFooter(viewType)) {
            int whichFooter = Math.abs(viewType - FOOTER_VIEW_TYPE);
            View footerView = mFooters.get(whichFooter);
            return new BaseViewHolder(footerView, false);
        } else {
            return onCreateHolder(viewGroup, viewType);
        }
    }

    /**
     * 创建viewHolder
     *
     * @param viewGroup
     * @param viewType
     * @return
     */
    public BaseViewHolder onCreateHolder(ViewGroup viewGroup, int viewType) {
        BaseViewHolder viewHolder = new BaseViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(bindView(viewType), viewGroup, false), true);
        return viewHolder;
    }


    /**
     * 每条布局的type
     *
     * @param index 相对于List的位置
     * @return
     */
    public int getViewType(int index) {
        return 0;
    }

    @Override
    public final int getItemViewType(int position) {
        if (position < getHeaderCount()) {
            return HEADER_VIEW_TYPE + position;
        } else if (position < (getHeaderCount() + getData().size())) {
            return getViewType(position - getHeaderCount());
        } else {
            return FOOTER_VIEW_TYPE + position - getHeaderCount() - getData().size();
        }
    }

    @Override
    public int getItemCount() {
        return getHeaderCount() + getData().size() + getFooterCount();
    }

    @Override
    public final void onBindViewHolder(BaseRecyclerAdapter.BaseViewHolder holder, int position) {
        if (position >= getHeaderCount() && position < getHeaderCount() + getData().size()) {
            int index = position - getHeaderCount();
            onBindHolder(holder, getItem(index), index);
        }
    }


    public class BaseViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {
        private SparseArray<View> holder = null;
        private ViewDataBinding binding;

        @Nullable
        public ViewDataBinding getBinding() {
            return binding;
        }

        public void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }

        public BaseViewHolder(View itemView, boolean bindItemClick) {
            super(itemView);
            if (bindItemClick) {
                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);
            }
        }

        /**
         * @return 相对于List容器的位置
         */
        public int getIndex() {
            return getAdapterPosition() - getHeaderCount();
        }

        /**
         * 获取子控件
         *
         * @param id
         * @param <T>
         * @return
         */
        @Nullable
        public <T extends View> T obtainView(@IdRes int id) {
            if (null == holder) holder = new SparseArray<>();
            View view = holder.get(id);
            if (null != view) return (T) view;
            view = itemView.findViewById(id);
            if (null == view) return null;
            holder.put(id, view);
            return (T) view;
        }

        @Nullable
        public <T> T obtainView(@IdRes int id, Class<T> viewClazz) {
            View view = obtainView(id);
            if (null == view) return null;
            return (T) view;
        }


        public BaseViewHolder bindChildClick(@IdRes int id) {
            View view = obtainView(id);
            return bindChildClick(view);
        }

        /**
         * 子控件绑定局部点击事件
         *
         * @param v
         * @return
         */
        public BaseViewHolder bindChildClick(@NonNull View v) {
            if (v == null) return this;
            if (v == itemView) {
                throw new IllegalArgumentException("bindChildClick 不能传递item根布局!");
            }
            v.setOnClickListener(this);
            return this;
        }


        public BaseViewHolder bindChildLongClick(@IdRes int id) {
            View view = obtainView(id);
            return bindChildLongClick(view);
        }

        public BaseViewHolder bindChildLongClick(@NonNull View v) {
            if (v == null) return this;
            if (v == itemView) {
                throw new IllegalArgumentException("bindChildLongClick 不能传递item根布局");
            }
            v.setOnLongClickListener(this);
            return this;
        }

        /**
         * 文本控件赋值
         *
         * @param id
         * @param text
         */
        public BaseViewHolder setText(@IdRes int id, CharSequence text) {
            View view = obtainView(id);
            if (view instanceof TextView) {
                ((TextView) view).setText(text);
            }
            return this;
        }


        @Override
        public boolean onLongClick(View v) {
            if (onItemLongClickListener != null && v.getId() == this.itemView.getId()) {
                return onItemLongClickListener.onItemLongClick(BaseRecyclerAdapter.this, this, v, getIndex());
            } else if (onItemChildLongClickListener != null && v.getId() != this.itemView.getId()) {
                return onItemChildLongClickListener.onItemChildLongClick(BaseRecyclerAdapter.this, this, v, getIndex());
            }
            return false;
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null && v.getId() == this.itemView.getId()) {
                onItemClickListener.onItemClick(BaseRecyclerAdapter.this, this, v, getIndex());
            } else if (onItemChildClickListener != null && v.getId() != this.itemView.getId()) {
                onItemChildClickListener.onItemChildClick(BaseRecyclerAdapter.this, this, v, getIndex());
            }
        }

    }

    protected OnItemClickListener onItemClickListener;
    protected OnItemLongClickListener onItemLongClickListener;
    protected OnItemChildClickListener onItemChildClickListener;
    protected OnItemChildLongClickListener onItemChildLongClickListener;

    public void setOnItemClickListener(OnItemClickListener l) {
        this.onItemClickListener = l;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener l) {
        this.onItemLongClickListener = l;
    }

    public void setOnItemChildLongClickListener(OnItemChildLongClickListener l) {
        this.onItemChildLongClickListener = l;
    }

    public void setOnItemChildClickListener(OnItemChildClickListener l) {
        this.onItemChildClickListener = l;
    }

}

