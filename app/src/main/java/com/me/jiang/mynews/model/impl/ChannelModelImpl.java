package com.me.jiang.mynews.model.impl;

import android.content.Context;
import android.os.Looper;

import com.me.jiang.mynews.api.ApiManager;
import com.me.jiang.mynews.bean.ChannelBean;
import com.me.jiang.mynews.config.Config;
import com.me.jiang.mynews.db.DbManager;
import com.me.jiang.mynews.util.ACache;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/5/6.
 */
public class ChannelModelImpl  implements IChannelModel{

    public interface ChannelOnListener
    {
        void onSuccess(ChannelBean channelBean);
        void onFailure(Throwable e);
    }
    private ACache cache;
    private ChannelOnListener listener;
    public ChannelModelImpl(ChannelOnListener listener)
    {
        this.listener=listener;
    }

    @Override
    public void getAllChannel() {
        cache=ACache.get(context,Config.CACHE_FILE_NAME);
        Observable.concat(getChannelFromDisk(),ApiManager.getAllChannel().doOnNext(new Action1<ChannelBean>() {
                    @Override
                    public void call(ChannelBean channelBean) {
                        DbManager.getInstance(context).insertChannel(channelBean);
                        cache.put(Config.HAS_CACHE_CHANNEL_KEY,Config.HAS_CHANNEL);
                    }
                }))
                .first()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ChannelBean>() {
                    @Override
                    public void call(ChannelBean channelBean) {
                        if (listener != null)
                        {
                            listener.onSuccess(channelBean);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (listener != null)
                        {
                            listener.onFailure(throwable);
                        }
                    }
                });
    }


    /**
     * 从数据库中获取频道数据
     * @return
     */
    private Observable<ChannelBean> getChannelFromDisk()
    {
          return  Observable.create(new Observable.OnSubscribe<ChannelBean>() {
                @Override
                public void call(Subscriber<? super ChannelBean> subscriber) {
                    if(context==null)
                    {
                        System.out.println("context为空");
                        subscriber.onCompleted();
                        return;
                    }
                    if(cache.getAsString(Config.HAS_CACHE_CHANNEL_KEY)==null||!Config.HAS_CHANNEL.equals(cache.getAsString(Config.HAS_CACHE_CHANNEL_KEY)))
                    {
                         subscriber.onCompleted();
                         return;
                    }
                    ChannelBean bean=DbManager.getInstance(context).getAllChannel();
                    if(bean!=null&&bean.getShowapi_res_body().getChannelList()!=null&&bean.getShowapi_res_body().getChannelList().size()>0)
                    {
                        subscriber.onNext(bean);
                    }else{
                        subscriber.onCompleted();
                    }
                }
            });
    }

    private Context context;
    public void setContext(Context context)
    {
        this.context=context;
    }


}
