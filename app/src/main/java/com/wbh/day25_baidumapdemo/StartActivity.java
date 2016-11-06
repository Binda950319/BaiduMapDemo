package com.wbh.day25_baidumapdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class StartActivity extends AppCompatActivity {

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

    }

    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.search_bt:
                intent = new Intent(this, SearchActivity.class);
                break;
            case R.id.busSearch_bt:
                intent = new Intent(this, BusSearchActivity.class);
                break;
            case R.id.routSearch_bt:
                intent = new Intent(this, RoutSearchActivity.class);
                        break;

            case R.id.local_bt:
                intent = new Intent(this, LocalActivity.class);
                break;
        }
        startActivity(intent);
    }
}
