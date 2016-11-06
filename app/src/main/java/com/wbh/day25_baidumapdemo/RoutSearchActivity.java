package com.wbh.day25_baidumapdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.wbh.day25_baidumapdemo.overlayutil.TransitRouteOverlay;

public class RoutSearchActivity extends AppCompatActivity {


    private MapView rmapView;
    private EditText start_et;
    private EditText end_et;
    private BaiduMap map;
    private RoutePlanSearch routePlanSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rout_search);
        rmapView = (MapView) findViewById(R.id.rmapView);
        start_et = (EditText) findViewById(R.id.start_et);
        end_et = (EditText) findViewById(R.id.end_et);
        initRoutPlanSearch();
    }

    private void initRoutPlanSearch() {
        map = rmapView.getMap();
        routePlanSearch = RoutePlanSearch.newInstance();
        routePlanSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {
            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
            
            }

            @Override
            public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {
                    if (transitRouteResult != null && transitRouteResult.error == SearchResult.ERRORNO.NO_ERROR){
                        map.clear();
                        TransitRouteOverlay overlay = new TransitRouteOverlay(map);
                        overlay.setData(transitRouteResult.getRouteLines().get(0));
                        overlay.addToMap();
                        overlay.zoomToSpan();
                    }
            }

            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

            }

            @Override
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

            }
        });
    }

    public void onClick(View view) {
        PlanNode planStartNode = PlanNode.withCityNameAndPlaceName("北京", start_et.getText().toString());
        PlanNode planEndNode = PlanNode.withCityNameAndPlaceName("北京", end_et.getText().toString());

        routePlanSearch.transitSearch(new TransitRoutePlanOption().city("北京").from(planStartNode).to(planEndNode));
    }
}
