package com.me.jiang.mynews.view.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.me.jiang.mynews.R;
import com.me.jiang.mynews.bean.NewsBean;
import com.me.jiang.mynews.presenter.impl.INewsFragmentPresenterImpl;
import com.me.jiang.mynews.view.iview.INewsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/6.
 * 显示新闻的fragment
 */
public class NewsFragment extends BaseFragment implements INewsFragment{

  private static final  String CHANNEL_ID="CHANNEL_ID";
  private static final String  NEWS_PAGE="PAGE";

  private String channelId;
  private String  page;
  private INewsFragmentPresenterImpl presenter;

  private ProgressDialog dialog;
  private Toast toast;

  private View view;


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
        presenter=new INewsFragmentPresenterImpl(this);
        presenter.getNews(channelId,page);
        return view;
    }

    @Override
    public void showNews(NewsBean newsBean) {
       showToast("获取新闻数据成功："+newsBean.toString());
    }

    @Override
    public void loadMoreNews(NewsBean newsBean) {

    }

    @Override
    public void refreshNews(NewsBean newsBean) {

    }

    @Override
    public void showToast(String message) {
      if(toast==null)
      {
          toast=Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT);
      }else {
          toast.setText(message);
      }
      toast.show();
    }

    @Override
    public void showDialog(String title, String message) {

    }

    @Override
    public void showProgressDialog(String title, String message) {
        if(dialog==null)
        {
            dialog=ProgressDialog.show(getActivity(),title,message);
        }else {
            dialog.setTitle(title);
            dialog.setMessage(message);
            dialog.show();
        }
    }

    @Override
    public void hideDialog() {
         if(dialog!=null&&dialog.isShowing())
         {
             dialog.dismiss();
         }
    }
}
