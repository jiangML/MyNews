package com.me.jiang.mynews.view.adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.me.jiang.mynews.view.fragment.NewsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/6.
 */
public class NewsFragmentAdapter extends FragmentPagerAdapter {

    private List<NewsFragment> data;
    private Context context;
    private List<String> title=new ArrayList<>();
    public NewsFragmentAdapter(List<NewsFragment> data,Context context,List<String> title,FragmentManager fm)
    {
        super(fm);
        this.context=context;
        this.data=data;
        this.title=title;
    }


    @Override
    public Fragment getItem(int position) {
        return data!=null?data.get(position):null;
    }

    @Override
    public int getCount() {
        return data!=null?data.size():0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title!=null?title.get(position):"xx";
    }
}
