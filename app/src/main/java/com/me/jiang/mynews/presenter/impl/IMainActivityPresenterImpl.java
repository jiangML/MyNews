package com.me.jiang.mynews.presenter.impl;

import android.content.Context;
import android.util.Log;

import com.me.jiang.mynews.bean.ChannelBean;
import com.me.jiang.mynews.bean.NewsBean;
import com.me.jiang.mynews.db.DbManager;
import com.me.jiang.mynews.model.impl.ChannelModelImpl;
import com.me.jiang.mynews.model.impl.NewsModelImpl;
import com.me.jiang.mynews.presenter.IMainActivityPresenter;
import com.me.jiang.mynews.view.fragment.NewsFragment;
import com.me.jiang.mynews.view.iview.IMainActivityView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.LoggingMXBean;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/5/6.
 * 首页 MainActivity 的presenter
 */
public class IMainActivityPresenterImpl implements IMainActivityPresenter,ChannelModelImpl.ChannelOnListener{
    private String TAG="IMainActivityPresenterImpl";
    private IMainActivityView iMainActivityView;// view
    private ChannelModelImpl channelModel; //model
    private static Set<String> channelIds=new HashSet<>();
    private static Map<String,NewsFragment> fragmentMap=new HashMap<>();

    public IMainActivityPresenterImpl(IMainActivityView iMainActivityView)
    {
        this.iMainActivityView=iMainActivityView;
        channelModel=new ChannelModelImpl(this);
    }

    @Override
    public void getAllChannel() {
         // iMainActivityView.showProgressDialog(null,"正在获取频道数据...");
          channelModel.setContext(context);
          channelModel.getAllChannel();
    }
    @Override
    public void getNews(String channelId, String page) {
            if(channelIds.contains(channelId))
            {
                iMainActivityView.showNewsFragment(fragmentMap.get(channelId));
                return;
            }
             channelIds.add(channelId);
             NewsFragment fragment=NewsFragment.getInstance(channelId,page);
             fragmentMap.put(channelId, fragment);
             iMainActivityView.showNewsFragment(fragment);
    }


    @Override
    public void onSuccess(ChannelBean channelBean) {
          iMainActivityView.hideDialog();
          iMainActivityView.showChannel(channelBean);
    }

    @Override
    public void onFailure(Throwable e) {
          iMainActivityView.hideDialog();
          Log.d(TAG, "获取频道数据错误--->" + e.getMessage());
    }
    private Context context;
    public void setContext(Context context)
   {
       this.context=context;
   }


}
