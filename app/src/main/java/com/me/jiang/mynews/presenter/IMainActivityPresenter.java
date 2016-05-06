package com.me.jiang.mynews.presenter;

/**
 * Created by Administrator on 2016/5/6.
 */
public interface IMainActivityPresenter {
    void getAllChannel();
    void getNews(String channelId,String page);
}
