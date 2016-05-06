package com.me.jiang.mynews.api;

import com.me.jiang.mynews.bean.ChannelBean;
import com.me.jiang.mynews.bean.NewsBean;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by Administrator on 2016/5/6.
 */
public class ApiManager {

    private static  final String BASE_URL="http://apis.baidu.com/";

    private static final Retrofit retrofit=new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();

    private static  final  ApiService service=retrofit.create(ApiService.class);


    /**
     * 获取所有试问频道数据
     * @return
     */
    public static Observable<ChannelBean> getAllChannel()
    {
        return service.getAllChannel();
    }

    /**
     * 获取频道新闻
     * @param channelId
     * @param page
     * @return
     */
    public static  Observable<NewsBean> getnews(String channelId,String page)
    {
        return service.getNews(channelId,page);
    }



}
