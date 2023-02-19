package com.bidfrail.provider;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.library.utilities.statusbar.StatusBarPlus;


/**
 * @author: DamonJiang
 * @date: 2018/10/12 0012
 * @description:
 */
public class StateActivity extends AppCompatActivity implements View.OnClickListener {
    private PageLayout pageLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state);
        initStatusBar();

        findViewById(R.id.id_btn_lodding).setOnClickListener(this);
        findViewById(R.id.id_btn_content).setOnClickListener(this);
        findViewById(R.id.id_btn_empty).setOnClickListener(this);
        findViewById(R.id.id_btn_errow).setOnClickListener(this);

        pageLayout = new PageLayout.Builder(this)
                .initPage(findViewById(R.id.id_image))
                .setCustomView(LayoutInflater.from(this).inflate(R.layout.layout_empty, null))
                .setOnRetryListener(new PageLayout.OnRetryClickListener() {
                    @Override
                    public void onRetry() {
                        loadData();
                    }


                })
                .create();
        pageLayout.hide();
    }
    protected void initStatusBar() {
        StatusBarPlus.setColor(this, getResources().getColor(com.library.utilities.R.color.green));
        StatusBarPlus.setStatusBarMode(this, false);
    }

    private void loadData() {
        pageLayout.showLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pageLayout.hide();
            }
        },2000);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_btn_lodding:
                pageLayout.showLoading();
                break;
            case R.id.id_btn_content:
                pageLayout.hide();
                break;
            case R.id.id_btn_empty:
                pageLayout.showEmpty();
                break;
            case R.id.id_btn_errow:
                pageLayout.showError();
                break;
        }
    }
}
