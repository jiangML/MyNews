package com.me.jiang.mynews.view.activity;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.me.jiang.mynews.R;
import com.me.jiang.mynews.api.ApiManager;
import com.me.jiang.mynews.bean.ChannelBean;
import com.me.jiang.mynews.db.DbManager;
import com.me.jiang.mynews.presenter.impl.IMainActivityPresenterImpl;
import com.me.jiang.mynews.view.adapter.NewsFragmentAdapter;
import com.me.jiang.mynews.view.fragment.NewsFragment;
import com.me.jiang.mynews.view.iview.IMainActivityView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements IMainActivityView{

    private Toolbar toolbar;
    private IMainActivityPresenterImpl presenter;
    private ProgressDialog dialog;
    private Toast toast;
    private NewsFragment currentFragment;
    private TabLayout tab_layout;
    private ViewPager vp;
    private  List<ChannelBean.ShowapiResBodyBean.ChannelListBean> channelListBean;
    private List<NewsFragment> fragmentList;
    private NewsFragmentAdapter adapter;
    private List<String> title=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init()
    {
         presenter=new IMainActivityPresenterImpl(this);
          presenter.setContext(MainActivity.this);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
         tab_layout=(TabLayout)findViewById(R.id.tab_layout);
         vp=(ViewPager)findViewById(R.id.vpager);
         tab_layout.setTabTextColors(Color.BLACK, Color.RED);
         tab_layout.setTabMode(TabLayout.MODE_SCROLLABLE);
         setSupportActionBar(toolbar);
         if(getSupportActionBar()!=null)
         {
              getSupportActionBar().setDisplayHomeAsUpEnabled(true);
              getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon);
         }

        fragmentList=new ArrayList<>();
        presenter.getAllChannel();
    }



    @Override
    public void showChannel(final ChannelBean channelBean) {
        if(channelBean==null) return;
        channelListBean=channelBean.getShowapi_res_body().getChannelList();
        for(int i=0;i<channelListBean.size();i++)
        {
            ChannelBean.ShowapiResBodyBean.ChannelListBean bean=channelListBean.get(i);
            fragmentList.add(NewsFragment.getInstance(bean.getChannelId(), "1"));
            title.add(bean.getName());
        }
        adapter=new NewsFragmentAdapter(fragmentList,this,title,getSupportFragmentManager());
        vp.setAdapter(adapter);
        tab_layout.setupWithViewPager(vp);
    }

    @Override
    public void showNewsFragment(NewsFragment fragment) {
//        if (fragmentList.contains(fragment))    return;
//        fragmentList.add(fragment);
//        adapter.notifyDataSetChanged();
    }

    @Override
    public void showToast(String message) {
        if(toast==null)
        {
            toast=Toast.makeText(this,message,Toast.LENGTH_SHORT);
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
            dialog=ProgressDialog.show(this,title,message);
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
