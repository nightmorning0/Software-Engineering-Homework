package com.example.xjtuhelper.ui.Map;

import com.amap.api.navi.AMapNaviListener;

interface Route_Activity extends AMapNaviListener {
    //路线规划成功处理（驾车、骑行、步行）
    void onCalculateRouteSuccess();
}
