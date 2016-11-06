package com.wbh.day25_baidumapdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.busline.BusLineResult;
import com.baidu.mapapi.search.busline.BusLineSearch;
import com.baidu.mapapi.search.busline.BusLineSearchOption;
import com.baidu.mapapi.search.busline.OnGetBusLineSearchResultListener;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.wbh.day25_baidumapdemo.overlayutil.BusLineOverlay;

public class BusSearchActivity extends AppCompatActivity {

    private EditText busNum_et;
    private MapView bmapView;
    private BaiduMap map;
    private BusLineSearch busLineSearch;
    private PoiSearch poiSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_search);
        busNum_et = (EditText) findViewById(R.id.busNum_et);
        bmapView = (MapView) findViewById(R.id.bmapView);
        initBusLineSearch();
    }

    private void initBusLineSearch() {
        map = bmapView.getMap();
        poiSearch = PoiSearch.newInstance();
        busLineSearch = BusLineSearch.newInstance();
        busLineSearch.setOnGetBusLineSearchResultListener(new OnGetBusLineSearchResultListener() {
            @Override
            public void onGetBusLineResult(BusLineResult busLineResult) {
                if (busLineResult != null && busLineResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    map.clear();
                    BusLineOverlay overlay = new BusLineOverlay(map);
                    overlay.setData(busLineResult);
                    overlay.addToMap();
                    overlay.zoomToSpan();
                }
            }
        });
        poiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                if (poiResult != null && poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    String uid = poiResult.getAllPoi().get(0).uid;
                    busLineSearch.searchBusLine(new BusLineSearchOption().city("北京").uid(uid));
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        });

    }


    public void onClick(View view) {
        poiSearch.searchInCity(new PoiCitySearchOption().city("北京").keyword(busNum_et.getText().toString()));
    }
}
