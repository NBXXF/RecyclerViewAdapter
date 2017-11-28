package com.asange.recyclerviewadapter.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.asange.recyclerviewadapter.BaseRecyclerAdapter;
import com.asange.recyclerviewadapter.BaseViewHolder;
import com.asange.recyclerviewadapter.OnItemChildClickListener;
import com.asange.recyclerviewadapter.OnItemClickListener;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnItemClickListener, OnItemChildClickListener {

    private RecyclerView mRecyclerView;
    /**
     * add
     */
    private Button mBtAdd;
    /**
     * addFirst
     */
    private Button mBtAddFirst;
    /**
     * remove
     */
    private Button mBtRemove;
    /**
     * refresh
     */
    private Button mBtRefresh;
    UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void log(String charSequence) {
        Log.d("----------->", charSequence);
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mBtAdd = (Button) findViewById(R.id.btAdd);
        mBtAdd.setOnClickListener(this);
        mBtAddFirst = (Button) findViewById(R.id.btAddFirst);
        mBtAddFirst.setOnClickListener(this);
        mBtRemove = (Button) findViewById(R.id.btRemove);
        mBtRemove.setOnClickListener(this);
        mBtRefresh = (Button) findViewById(R.id.btRefresh);
        mBtRefresh.setOnClickListener(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(userAdapter = new UserAdapter());
        userAdapter.addHeader(R.layout.header, mRecyclerView);
        userAdapter.addFooter(R.layout.footer, mRecyclerView);
        userAdapter.bindData(true, UserFactory.createUsers());
        userAdapter.setOnItemClickListener(this);
        userAdapter.setOnItemChildClickListener(this);

        userAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                log("onChanged");
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                super.onItemRangeChanged(positionStart, itemCount);
                log(String.format("onItemRangeChanged(%s, %s)", positionStart, itemCount));
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
                super.onItemRangeChanged(positionStart, itemCount, payload);
                log(String.format("onItemRangeChanged(%s,%s,%s)", positionStart, itemCount, payload));
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                log(String.format("onItemRangeInserted(%s,%s)", positionStart, itemCount));
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                log(String.format("onItemRangeRemoved(%s,%s)", positionStart, itemCount));
            }

            @Override
            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount);
                log(String.format("onItemRangeMoved(%s,%s,%s)", fromPosition, toPosition, itemCount));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btAdd:
                userAdapter.addItem(UserFactory.createUser());
                break;
            case R.id.btAddFirst:
                userAdapter.addItem(0, UserFactory.createUser());
                break;
            case R.id.btRemove:
                userAdapter.removeItem(0);
                break;
            case R.id.btRefresh:
                userAdapter.bindData(true, UserFactory.createUsers());
                break;
        }
    }


    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onItemClick(BaseRecyclerAdapter adapter, BaseViewHolder holder, View itemView, int index) {
        if (userAdapter.isSelectable()) {
            userAdapter.toogleItemSelect(index);
        }
        showToast("onItemClick:" + index);
    }

    @Override
    public void onItemChildClick(BaseRecyclerAdapter adapter, BaseViewHolder holder, View childView, int index) {
        switch (childView.getId()) {
            case R.id.clickBtn:
                showToast("onItemChildClick:" + index);
                break;
            case R.id.updateBtn:
                User item = userAdapter.getItem(index);
                item.setAge(item.getAge() + 1);
                userAdapter.updateItem(item);
                break;
            case R.id.delBtn:
                userAdapter.removeItem(index);
                break;
        }
    }
}
