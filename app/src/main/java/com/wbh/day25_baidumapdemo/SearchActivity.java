package com.wbh.day25_baidumapdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.wbh.day25_baidumapdemo.overlayutil.PoiOverlay;

public class SearchActivity extends AppCompatActivity {


    private EditText search_et;
    private MapView mapView;
    private BaiduMap map;
    private PoiSearch poiSearch;
    private int pager = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        search_et = (EditText) findViewById(R.id.search_et);
        mapView = (MapView) findViewById(R.id.mapView);
        map = mapView.getMap();
        poiSearch = PoiSearch.newInstance();
        poiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                if (poiResult != null && poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    map.clear();
                    MyPoiOverlay myPoi = new MyPoiOverlay(map);
                    myPoi.setData(poiResult);
                    myPoi.addToMap();
                    myPoi.zoomToSpan();
                    map.setOnMarkerClickListener(myPoi);
                }

            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

                if (poiDetailResult != null && poiDetailResult.error == SearchResult.ERRORNO.NO_ERROR){
                    String detailUrl = poiDetailResult.getDetailUrl();
                    Intent intent = new Intent(SearchActivity.this, DetailSearchActivity.class);
                    intent.putExtra("url", detailUrl);
                    startActivity(intent);
                }
            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        });
    }

    class MyPoiOverlay extends PoiOverlay {

        /**
         * 构造函数
         *
         * @param baiduMap 该 PoiOverlay 引用的 BaiduMap 对象
         */
        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int i) {
            PoiInfo poiInfo = getPoiResult().getAllPoi().get(i);
            poiSearch.searchPoiDetail(new PoiDetailSearchOption().poiUid(poiInfo.uid));
            return super.onPoiClick(i);

        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search:
                poiSearch.searchInCity(new PoiCitySearchOption().city("北京").keyword(search_et.getText().toString()).pageNum(pager));
                break;
            case R.id.next_bt:
                pager++;
                poiSearch.searchInCity(new PoiCitySearchOption().city("北京").keyword(search_et.getText().toString()).pageNum(pager));
                break;
        }
    }
}
