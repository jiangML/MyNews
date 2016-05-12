package com.me.jiang.mynews.model.impl;

import android.content.Context;

import com.me.jiang.mynews.api.ApiManager;
import com.me.jiang.mynews.bean.NewsBean;
import com.me.jiang.mynews.db.DbManager;
import com.me.jiang.mynews.util.Baseutil;

import retrofit2.Call;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2016/5/6.
 */
public class NewsModelImpl implements INewsModel {

    private Subscription subscription;
    private CompositeSubscription compositeSubscription;
    private Context context;


    public interface NewsOnOnListener {
        void onSuccess(NewsBean newsBean);

        void onFailuer(Throwable throwable);
    }

    private NewsOnOnListener listener;

    public NewsModelImpl(NewsOnOnListener listener) {
        this.listener = listener;
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void getNews(final String channelId, final String page) {

        subscription=Observable.concat(getNewsFromDataBase(channelId),ApiManager.getnews(channelId,page).doOnNext(new Action1<NewsBean>() {
                            @Override
                            public void call(NewsBean newsBean) {
                                if(context!=null)
                                {
                                    DbManager.getInstance(context).insertNews(newsBean, page, channelId);
                                    System.out.println("-----保存新闻数据到数据库------");
                                }
                            }
                         }))
//                         .takeFirst(new Func1<NewsBean, Boolean>() {
//                             @Override
//                             public Boolean call(NewsBean newsBean) {
//                                 if (context != null && Baseutil.getNetType(context) == Baseutil.NETTYPE_WIFI) {
//                                     System.out.println("-----当前是wifi 从网络获取------");
//                                     return false;
//                                 } else {
//                                     System.out.println("-----当前不是wifi 从数据库获取------");
//                                     return true;
//                                 }
//                             }
//                         })
                         .first()
                         .subscribeOn(Schedulers.io())
                         .observeOn(AndroidSchedulers.mainThread())
                         .subscribe(new Action1<NewsBean>() {
                             @Override
                             public void call(NewsBean newsBean) {
                                 if (listener != null)
                                     listener.onSuccess(newsBean);
                                 System.out.println("-----获取成功------");
                             }
                         }, new Action1<Throwable>() {
                             @Override
                             public void call(Throwable throwable) {
                                 if(listener!=null)
                                     listener.onFailuer(throwable);
                                 System.out.println("-----获取失败------");
                             }
                         });


//        subscription = ApiManager.getnews(channelId, page)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<NewsBean>() {
//                    @Override
//                    public void call(NewsBean newsBean) {
//                        if (listener != null)
//                            listener.onSuccess(newsBean);
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        if (listener != null)
//                            listener.onFailuer(throwable);
//                    }
//                });
        compositeSubscription.add(subscription);
    }

    /**
     * 取消所有网络请求
     */
    public void cancel() {
        if (compositeSubscription.hasSubscriptions()) {
            compositeSubscription.unsubscribe();
        }
    }


    private Observable<NewsBean> getNewsFromDataBase(final String channelId) {
        return Observable.create(new Observable.OnSubscribe<NewsBean>() {
            @Override
            public void call(Subscriber<? super NewsBean> subscriber) {
                 if(context==null)
                 {
                     subscriber.onCompleted();
                     return;
                 }

                NewsBean news=DbManager.getInstance(context).getNews(channelId);
               // NewsBean news=null;
                if(news!=null&&news.getShowapi_res_body().getPagebean().getContentlist()!=null&&news.getShowapi_res_body().getPagebean().getContentlist().size()>0)
                {
                    System.out.println("-----获取数据库中的新闻数据------");
                    subscriber.onNext(news);
                }else{
                    System.out.println("-----获取数据库中的新闻数据为空------");
                    subscriber.onCompleted();
                }

            }
        });
    }

    public void setContext(Context context)
    {
        this.context=context;
    }



}
