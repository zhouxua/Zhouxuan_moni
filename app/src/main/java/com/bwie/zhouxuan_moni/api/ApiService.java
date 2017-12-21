package com.bwie.zhouxuan_moni.api;

import com.bwie.zhouxuan_moni.bean.NewsBean;
import com.bwie.zhouxuan_moni.bean.XiangqingBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by dream on 2017/12/20.
 */

public interface ApiService {
    @GET("/front/homePageApi/homePage.do")
    Observable<NewsBean> getUrl();
//    http://api.svipmovie.com/front/videoDetailApi/videoDetail.do?mediaId=70cddbf9d84b4c72bd4311952f03b6d4
     @GET("/front/videoDetailApi/videoDetail.do")
    Observable<XiangqingBean> getXiangQing(@Query("mediaId") String mediaId);
}
