package com.me.jiang.mynews.presenter.impl;

import com.me.jiang.mynews.bean.NewsBean;
import com.me.jiang.mynews.model.impl.NewsModelImpl;
import com.me.jiang.mynews.presenter.INewsFragmentPresenter;
import com.me.jiang.mynews.view.iview.INewsFragment;

/**
 * Created by Administrator on 2016/5/6.
 */
public class INewsFragmentPresenterImpl implements INewsFragmentPresenter,NewsModelImpl.NewsOnOnListener {

    private INewsFragment iNewsFragment;//view
    private NewsModelImpl newsModel;
    public INewsFragmentPresenterImpl(INewsFragment iNewsFragment)
    {
        this.iNewsFragment=iNewsFragment;
        newsModel=new NewsModelImpl(this);
    }


    @Override
    public void getNews(String channelId, String page) {
      iNewsFragment.showProgressDialog(null,"正在加载新闻数据...");
      newsModel.getNews(channelId,page);
    }

    @Override
    public void onSuccess(NewsBean newsBean) {
        iNewsFragment.showNews(newsBean);
        iNewsFragment.hideDialog();
    }

    @Override
    public void onFailuer(Throwable throwable) {
      iNewsFragment.hideDialog();
      iNewsFragment.showToast("获取新闻数据错误："+throwable.getMessage());
    }


}
