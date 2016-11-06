package com.wbh.day25_baidumapdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;

public class LocalActivity extends AppCompatActivity {

    private MapView lmapView;
    private BaiduMap map;
    private  LocationClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local);
        lmapView = (MapView) findViewById(R.id.lmapView);
        initLocal();
    }

    private void initLocal() {
        map = lmapView.getMap();

        map.setMyLocationEnabled(true);
        client = new LocationClient(this);

        MyBDListener listener = new MyBDListener();
        client.registerLocationListener(listener);

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd0911");
        option.setScanSpan(1000);
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        client.setLocOption(option);
        client.start();
    }

    class MyBDListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            MyLocationData data = new MyLocationData.Builder().latitude(bdLocation.getLatitude()).longitude(bdLocation.getLongitude()).direction(bdLocation.getDirection()).build();
            map.setMyLocationData(data);
        }
    }
}
