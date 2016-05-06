package com.me.jiang.mynews.model.impl;

import com.me.jiang.mynews.api.ApiManager;
import com.me.jiang.mynews.bean.ChannelBean;

import rx.Scheduler;
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

    private ChannelOnListener listener;
    public ChannelModelImpl(ChannelOnListener listener)
    {
        this.listener=listener;
    }


    @Override
    public void getAllChannel() {
        ApiManager.getAllChannel()
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new Action1<ChannelBean>() {
                      @Override
                      public void call(ChannelBean channelBean) {
                        if(listener!=null)
                            listener.onSuccess(channelBean);
                      }
                  }, new Action1<Throwable>() {
                      @Override
                      public void call(Throwable throwable) {
                          if(listener!=null)
                              listener.onFailure(throwable);
                      }
                  });
    }


}
