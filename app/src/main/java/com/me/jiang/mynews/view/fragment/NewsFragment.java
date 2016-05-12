package com.me.jiang.mynews.view.fragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.me.jiang.mynews.R;
import com.me.jiang.mynews.bean.NewsBean;
import com.me.jiang.mynews.presenter.impl.INewsFragmentPresenterImpl;
import com.me.jiang.mynews.view.activity.DetailActivity;
import com.me.jiang.mynews.view.adapter.RecycleViewAdapter;
import com.me.jiang.mynews.view.iview.INewsFragment;

import java.security.Permission;


/**
 * Created by Administrator on 2016/5/6.
 * 显示新闻的fragment
 */
public class NewsFragment extends BaseFragment implements INewsFragment{

  public static final  String CHANNEL_ID="CHANNEL_ID";
  public static final String  NEWS_PAGE="PAGE";
  private FloatingActionButton fab;
  private String channelId;
  private String  page;
  private INewsFragmentPresenterImpl presenter;
  private SwipeRefreshLayout swipe_layout;
  private RecyclerView recycle_view;
  private RecycleViewAdapter adapter;
  private View view;
  private int status=NORMAL;
  private static final int REFRESH=0x100;
  private static final int LOAD_MORE=0x101;
  private static final int NORMAL=0X102;
  private boolean isLoadMore=false;
  private ProgressBar pb;

  public static NewsFragment getInstance(String channelId,String page)
  {
      NewsFragment fragment=new NewsFragment();
      Bundle bundle=new Bundle();
      bundle.putString(CHANNEL_ID,channelId);
      bundle.putString(NEWS_PAGE,page);
      fragment.setArguments(bundle);
      return fragment;
  }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getArguments();
        if(bundle!=null)
        {
            channelId=bundle.getString(CHANNEL_ID);
            page=bundle.getString(NEWS_PAGE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return init(inflater,container,savedInstanceState);
    }


    private View init(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.fragment_news,container,false);
        ((TextView)view.findViewById(R.id.tv)).setText(channelId);
        pb=(ProgressBar)view.findViewById(R.id.pb);
        swipe_layout=(SwipeRefreshLayout)view.findViewById(R.id.swipe_layout);
        recycle_view=(RecyclerView)view.findViewById(R.id.recycle_view);
        swipe_layout.setColorSchemeColors(Color.BLUE, Color.DKGRAY);
        swipe_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1 + "";
                status = REFRESH;
                presenter.refresh(channelId, page);
            }
        });

      final LinearLayoutManager  linearLayoutManager=new LinearLayoutManager(getActivity());
      recycle_view.setLayoutManager(linearLayoutManager);
      recycle_view.setOnScrollListener(new RecyclerView.OnScrollListener() {
          int lastVisibleItem = 0;

          @Override
          public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
              super.onScrollStateChanged(recyclerView, newState);
              if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount() && !isLoadMore) {
                  isLoadMore = true;
                  page = (Integer.valueOf(page) + 1) + "";
                  status = LOAD_MORE;
                  adapter.setStatus(RecycleViewAdapter.LOAD_FINSH);
                  presenter.loadMore(channelId, page);
              }
          }

          @Override
          public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
              super.onScrolled(recyclerView, dx, dy);
              lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
          }
      });
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        presenter=new INewsFragmentPresenterImpl(this);
        presenter.setContext(getActivity());
        presenter.getNews(channelId,page);
        return view;
    }


    @Override
    public void showNews(NewsBean newsBean) {
       if(adapter==null)
       {
           adapter=new RecycleViewAdapter(getActivity(),newsBean);
           adapter.setOnItemChickListener(new RecycleViewAdapter.ItemOnChickListener() {
               @Override
               public void onItemChick(View view, NewsBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean news, int position) {
                   DetailActivity.startActiv(getActivity(),news.getTitle(),news.getLink());
               }
           });
       }
       switch (status)
       {
           case NORMAL:
               recycle_view.setAdapter(adapter);
               break;
           case REFRESH:
               refreshNews(newsBean);
               break;
           case LOAD_MORE:
               loadMoreNews(newsBean);
               break;
           default:
               break;
       }

    }

    @Override
    public void loadMoreNews(NewsBean newsBean) {
        isLoadMore=false;
        adapter.addItem(newsBean);
    }

    @Override
    public void refreshNews(NewsBean newsBean) {
        isLoadMore=false;
        adapter.refresh(newsBean);
        swipe_layout.setRefreshing(false);
    }


    @Override
    public void hideDialog() {
        super.hideDialog();
        pb.setVisibility(View.GONE);
        isLoadMore=false;
        adapter.hideFooterView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.cancel();
    }

}
