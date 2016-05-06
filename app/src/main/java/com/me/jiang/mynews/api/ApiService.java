package com.me.jiang.mynews.api;

import com.me.jiang.mynews.bean.ChannelBean;
import com.me.jiang.mynews.bean.NewsBean;


import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/5/6.
 * 网络接口
 */
public interface ApiService {
    //http://apis.baidu.com/showapi_open_bus/channel_news/channel_news

    /**
     * 获取所有频道数据
     * @return
     */
    @Headers("apikey:0c4ae770989e665c5b64bf6b209189cd")
    @GET("showapi_open_bus/channel_news/channel_news")
    Observable<ChannelBean> getAllChannel();

    /**
     * 获取频道新闻
     * @param channelId
     * @param page
     * @return
     */
    @Headers("apikey:0c4ae770989e665c5b64bf6b209189cd")
    @GET("showapi_open_bus/channel_news/search_news")
    Observable<NewsBean> getNews(@Query("channelId") String channelId,@Query("page") String page);

}
