package com.me.jiang.mynews.view.iview;

import com.me.jiang.mynews.bean.NewsBean;

/**
 * Created by Administrator on 2016/5/6.
 */
public interface INewsFragment extends ICommonView {

    void showNews(NewsBean newsBean);
    void loadMoreNews(NewsBean newsBean);
    void refreshNews(NewsBean newsBean);
}
