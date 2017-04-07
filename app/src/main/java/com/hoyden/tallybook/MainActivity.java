package com.hoyden.tallybook;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hoyden.adapter.RecordsAdapter;
import com.hoyden.bean.RecordsBean;
import com.hoyden.db.DbTally;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private RecordsAdapter adapter;
    private List<RecordsBean> mDatas;
    private RecyclerView mList;
    private SwipeRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initAdapter();
        initDatas();
    }

    private void initDatas() {
        refresh.setRefreshing(true);
        List<RecordsBean> all = DbTally.getInstance().getAll();
        mDatas.addAll(all);
        adapter.refresh(mDatas);
        adapter.notifyDataSetChanged();
        refresh.setRefreshing(false);
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("记账本");
        setSupportActionBar(toolbar);
        mList = (RecyclerView) findViewById(R.id.mList);
        FloatingActionButton add = (FloatingActionButton) findViewById(R.id.add);
        add.setOnClickListener(this);
        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        refresh.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimaryDark,R.color.colorAccent);
        refresh.setOnRefreshListener(this);
    }

    private void initAdapter() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mDatas = new ArrayList<>();
        adapter = new RecordsAdapter(mDatas, this);
        mList.setHasFixedSize(true);
        mList.setLayoutManager(manager);
        mList.setAdapter(adapter);
        adapter.setOnItemLongClickListener(new RecordsAdapter.OnItemLongClickListener() {
            @Override
            public void longClick(final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                CharSequence[] items = new CharSequence[]{"删除","按时间排序","按金额排序","按类别排序"};
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                DbTally.getInstance().delete(mDatas.get(position).getDate() + "");
                                mDatas.remove(position);
                                adapter.refresh(mDatas);
                                adapter.notifyDataSetChanged();
                                break;
                            case 1:
                                List<RecordsBean> date = DbTally.getInstance().getAll("date");
                                mDatas.clear();
                                mDatas.addAll(date);
                                adapter.refresh(mDatas);
                                adapter.notifyDataSetChanged();
                                break;
                            case 2:
                                List<RecordsBean> price = DbTally.getInstance().getAll("price");
                                mDatas.clear();
                                mDatas.addAll(price);
                                adapter.refresh(mDatas);
                                adapter.notifyDataSetChanged();
                                break;
                            case 3:
                                List<RecordsBean> type = DbTally.getInstance().getAll("type");
                                mDatas.clear();
                                mDatas.addAll(type);
                                adapter.refresh(mDatas);
                                adapter.notifyDataSetChanged();
                                break;
                        }
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                Intent intent = new Intent(this, InputActivity.class);
                startActivityForResult(intent, 1001);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1001 && resultCode == RESULT_OK && null != data) {
            RecordsBean bean = data.getParcelableExtra("cls");
            if (null != mDatas) {
                mDatas.add(0, bean);
                if (null != adapter) {
                    adapter.refresh(mDatas);
                    adapter.notifyDataSetChanged();
                }
            }
            DbTally.getInstance().insert(bean);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRefresh() {
        mDatas.clear();
        initDatas();
    }
}
