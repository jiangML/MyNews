package com.me.jiang.mynews.presenter.impl;

import com.me.jiang.mynews.bean.ChannelBean;
import com.me.jiang.mynews.bean.NewsBean;
import com.me.jiang.mynews.model.impl.ChannelModelImpl;
import com.me.jiang.mynews.model.impl.NewsModelImpl;
import com.me.jiang.mynews.presenter.IMainActivityPresenter;
import com.me.jiang.mynews.view.fragment.NewsFragment;
import com.me.jiang.mynews.view.iview.IMainActivityView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016/5/6.
 * 首页 MainActivity 的presenter
 */
public class IMainActivityPresenterImpl implements IMainActivityPresenter,ChannelModelImpl.ChannelOnListener{

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
          iMainActivityView.showProgressDialog(null,"正在获取频道数据...");
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
             fragmentMap.put(channelId,fragment);
             iMainActivityView.showNewsFragment(fragment);
    }




    @Override
    public void onSuccess(ChannelBean channelBean) {
          iMainActivityView.hideDialog();
          iMainActivityView.showToast("获取频道数据成功;"+channelBean.toString());
          iMainActivityView.showChannel(channelBean);
          String id=channelBean.getShowapi_res_body().getChannelList().get(0).getChannelId();
         // getNews(id,"1");
    }

    @Override
    public void onFailure(Throwable e) {
          iMainActivityView.hideDialog();
          iMainActivityView.showToast("获取频道数据错误："+e.getMessage());
    }





}
