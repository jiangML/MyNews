package com.me.jiang.mynews.model.impl;

import com.me.jiang.mynews.api.ApiManager;
import com.me.jiang.mynews.bean.NewsBean;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2016/5/6.
 */
public class NewsModelImpl implements INewsModel {

    private Subscription subscription;
    private CompositeSubscription compositeSubscription;
    public interface  NewsOnOnListener
    {
        void onSuccess(NewsBean newsBean);
        void onFailuer(Throwable throwable);
    }
    private NewsOnOnListener listener;

    public NewsModelImpl(NewsOnOnListener listener)
    {
        this.listener=listener;
        compositeSubscription=new CompositeSubscription();
    }

    @Override
    public void getNews(String channelId, String page) {
        subscription=ApiManager.getnews(channelId,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NewsBean>() {
                    @Override
                    public void call(NewsBean newsBean) {
                      if(listener!=null)
                          listener.onSuccess(newsBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if(listener!=null)
                        listener.onFailuer(throwable);
                    }
                });
        compositeSubscription.add(subscription);
    }

    /**
     * 取消所有网络请求
     */
    public void cancel()
    {
       if(compositeSubscription.hasSubscriptions())
       {
           compositeSubscription.unsubscribe();
       }
    }

}
