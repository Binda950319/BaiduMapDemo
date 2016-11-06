package com.wbh.day25_baidumapdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;

public class MainActivity extends AppCompatActivity {

    private MapView mapView;
    private BaiduMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        mapView = (MapView) findViewById(R.id.bmapView);
        map = mapView.getMap();
        //卫星地图
//        map.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
        //热力地图
//        map.setBaiduHeatMapEnabled(true);
        //交通地图
//        map.setTrafficEnabled(true);
        //室内地图
        map.setIndoorEnable(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
