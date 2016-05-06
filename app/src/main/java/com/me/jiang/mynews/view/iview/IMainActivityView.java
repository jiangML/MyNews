package com.me.jiang.mynews.view.iview;

import com.me.jiang.mynews.bean.ChannelBean;
import com.me.jiang.mynews.view.fragment.NewsFragment;

/**
 * Created by Administrator on 2016/5/6.
 */
public interface IMainActivityView extends ICommonView {

    /**
     * 显示频道
     * @param channelBean
     */
    void showChannel(ChannelBean channelBean);

    /**
     * 显示频道新闻fragment
     * @param fragment
     */
    void showNewsFragment(NewsFragment fragment);


}
