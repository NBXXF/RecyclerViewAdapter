package com.asange.recyclerviewadapter.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.asange.recyclerviewadapter.BaseRecyclerAdapter;
import com.asange.recyclerviewadapter.OnItemChildClickListener;
import com.asange.recyclerviewadapter.OnItemClickListener;

import static com.asange.recyclerviewadapter.demo.R.id;
import static com.asange.recyclerviewadapter.demo.R.layout;


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
        setContentView(layout.activity_main);
        initView();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(id.recyclerView);
        mBtAdd = (Button) findViewById(id.btAdd);
        mBtAdd.setOnClickListener(this);
        mBtAddFirst = (Button) findViewById(id.btAddFirst);
        mBtAddFirst.setOnClickListener(this);
        mBtRemove = (Button) findViewById(id.btRemove);
        mBtRemove.setOnClickListener(this);
        mBtRefresh = (Button) findViewById(id.btRefresh);
        mBtRefresh.setOnClickListener(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(userAdapter = new UserAdapter());
        userAdapter.bindData(true, UserFactory.createUsers());
        userAdapter.setOnItemClickListener(this);
        userAdapter.setOnItemChildClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case id.btAdd:
                userAdapter.addItem(UserFactory.createUser());
                break;
            case id.btAddFirst:
                userAdapter.addItem(0, UserFactory.createUser());
                break;
            case id.btRemove:
                userAdapter.removeItem(0);
                break;
            case id.btRefresh:
                userAdapter.bindData(true, UserFactory.createUsers());
                break;
        }
    }


    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemChildClick(BaseRecyclerAdapter adapter, BaseRecyclerAdapter.BaseViewHolder holder, View childView, int index) {
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

    @Override
    public void onItemClick(BaseRecyclerAdapter adapter, BaseRecyclerAdapter.BaseViewHolder holder, View itemView, int index) {
        showToast("onItemClick:" + index);
    }
}
