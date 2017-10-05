package com.asange.recyclerviewadapter.demo;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.asange.recyclerviewadapter.BaseRecyclerAdapter;
import com.asange.recyclerviewadapter.BaseViewHolder;
import com.asange.recyclerviewadapter.OnItemClickListener;
import com.asange.recyclerviewadapter.SelectableBaseAdapter;

/**
 * Description
 * Company Beijing icourt
 * author  youxuan  E-mail:xuanyouwu@163.com
 * date createTime：2017/10/4
 * version 2.1.0
 */
public class UserAdapter extends SelectableBaseAdapter<User> implements OnItemClickListener {
    public UserAdapter() {
        this.setOnItemClickListener(this);
    }

    @Override
    public int bindView(int viewType) {
        return R.layout.item_user;
    }

    @Override
    public int getViewType(int index) {
        return super.getViewType(index);
    }

    @Override
    public void onBindHolder(BaseViewHolder holder, @Nullable User user, int index) {
        if (user == null) return;
        TextView userNameTv = holder.obtainView(R.id.userNameTv);
        TextView userAgeTv = holder.obtainView(R.id.userAgeTv);
        Button updateBtn = holder.obtainView(R.id.updateBtn);
        Button delBtn = holder.obtainView(R.id.delBtn);
        Button clickBtn = holder.obtainView(R.id.clickBtn);
        userNameTv.setText("name:" + user.getName());
        userAgeTv.setText("age:" + user.getAge());
        holder.bindChildClick(updateBtn)
                .bindChildClick(delBtn)
                .bindChildClick(clickBtn);

        CheckBox userCb = holder.obtainView(R.id.userCb);
        userCb.setChecked(user.isItemSelected());
    }

    @Override
    public void onItemClick(BaseRecyclerAdapter adapter, BaseViewHolder holder, View itemView, int index) {

    }
}
