package com.hoyden.tallybook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hoyden.bean.RecordsBean;

/**
 * Created by nhd on 2017/4/7.
 */

public class InputActivity extends AppCompatActivity implements View.OnClickListener {
    private CoordinatorLayout topPanel;
    private TextView right;
    private SwitchCompat type;
    private EditText content, price;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        initViews();
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("记账本");
        setSupportActionBar(toolbar);
        right = (TextView) findViewById(R.id.right);
        type = (SwitchCompat) findViewById(R.id.type);
        content = (EditText) findViewById(R.id.content);
        price = (EditText) findViewById(R.id.price);
        topPanel = (CoordinatorLayout) findViewById(R.id.topPanel);
        right.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right:
                if (TextUtils.isEmpty(content.getText()) || TextUtils.isEmpty(price.getText())) {
                    Snackbar.make(topPanel, "信息不能为空", Snackbar.LENGTH_LONG).show();
                } else {
                    submit();
                }
                break;
        }
    }

    private void submit() {
        RecordsBean bean = new RecordsBean();
        bean.setDate(System.currentTimeMillis());
        bean.setType(type.isChecked() ? "支出" : "收入");
        bean.setContent(content.getText().toString().trim());
        bean.setPrice(Float.parseFloat(price.getText().toString().trim()) + "");
        Intent intent = new Intent();
        intent.putExtra("cls", bean);
        setResult(RESULT_OK, intent);
        finish();
    }
}
