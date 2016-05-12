package com.me.jiang.mynews.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.me.jiang.mynews.bean.ChannelBean;
import com.me.jiang.mynews.bean.NewsBean;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/10.
 *
 */
public class DbManager {

    private SQLiteDatabase db;
    private DbHelper helper;
    private static Context context;
    private static  DbManager instance;
    private static class DbManageHelper{
        static final DbManager manager=new DbManager(context);
    }

    private DbManager(Context context)
    {
        helper=new DbHelper(context);
    }
    public static  DbManager getInstance(Context c)
    {
//        context=c;
//        return DbManageHelper.manager;
        if(instance==null)
    {
        synchronized (DbManager.class)
        {
            if(instance==null)
            {
                instance=new DbManager(c);
            }
        }
    }
        return instance;
    }

    /**
     * 获取所有的频道数据
     * @return
     */
    public synchronized ChannelBean getAllChannel()
    {
        List<ChannelBean.ShowapiResBodyBean.ChannelListBean> list=new ArrayList<>();
        ChannelBean  bean=new ChannelBean();
        ChannelBean.ShowapiResBodyBean sp=new ChannelBean.ShowapiResBodyBean();

        db=helper.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from channel",null);
        while (cursor.moveToNext())
        {
            ChannelBean.ShowapiResBodyBean.ChannelListBean c=new ChannelBean.ShowapiResBodyBean.ChannelListBean();
            c.setName(cursor.getString(cursor.getColumnIndex("channel_name")));
            c.setChannelId(cursor.getString(cursor.getColumnIndex("channel_id")));
            list.add(c);
        }
        sp.setChannelList(list);
        bean.setShowapi_res_body(sp);
        cursor.close();
        db.close();
        return bean;
    }


    /**
     * 插入频道数据
     * @param channelId
     * @param name
     * @return
     */
    public synchronized long insertChannel(String channelId,String name)
    {
         db=helper.getWritableDatabase();
         ContentValues values=new ContentValues();
         values.put("channel_id", channelId);
         values.put("channel_name", name);
         long i=db.insert("channel",null,values);
         db.close();
         return i;
    }

    /**
     * 批量插入频道数据
     * @param bean
     */
    public synchronized  void insertChannel(ChannelBean bean)
    {
            db=helper.getWritableDatabase();
            List<ChannelBean.ShowapiResBodyBean.ChannelListBean> b=bean.getShowapi_res_body().getChannelList();

            db.beginTransaction();
            for(ChannelBean.ShowapiResBodyBean.ChannelListBean be:b)
            {
                ContentValues values=new ContentValues();
                values.put("channel_id",be.getChannelId());
                values.put("channel_name", be.getName());
                System.out.println("channel_name:"+be.getName()+" channel_id:"+be.getChannelId());
                db.insert("channel", null, values);
            //    values.clear();
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
    }


    /**
     * 插入新闻数据
     * @param channelId
     * @param page
     * @param content
     * @return
     */
    public long insertNews(String channelId,String page,String content)
    {
        db=helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("news_content", content);
        values.put("news_page", page);
        values.put("news_channel", channelId);
        long i=db.insert("news",null,values);
        db.close();
        return i;
    }


    /**
     * 根据频道is获取新闻数据
     * @param channelID
     * @return
     */
    public synchronized  NewsBean getNews(String channelID)
    {
        db=helper.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from news where news_channel=?",new String[]{channelID});
        NewsBean bean=new NewsBean();
        if(cursor!=null&&cursor.getCount()>0)
        {
            cursor.moveToNext();
            String content=cursor.getString(cursor.getColumnIndex("news_content"));
            System.out.println(channelID+"====查询的新闻数据-->"+content);
            if(content!=null)
            {
                Gson gson=new Gson();
                Type type=new TypeToken<NewsBean>(){}.getType();
                bean=gson.fromJson(content,type);
            }
            cursor.close();
            db.close();
            return bean;
        }else {
            System.out.println(channelID+"====查询的新闻数据为空");
            db.close();
            return null;
        }

    }

    /**
     * 插入新闻数据
     * @param bean
     * @param page
     * @param channelId
     * @return
     */
    public synchronized  void insertNews(NewsBean bean,String page,String channelId)
    {
        db=helper.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from news where news_channel=?",new String[]{channelId});
        if(cursor==null||cursor.getCount()<0)
        {
            db.close();
            db=helper.getWritableDatabase();
            Gson gson=new Gson();
            String g=gson.toJson(bean);
            ContentValues values=new ContentValues();
            values.put("news_content",g);
            values.put("news_page",page);
            values.put("news_channel", channelId);
            db.insert("news", null, values);
            db.close();
            System.out.println(channelId + "====插入新闻数据");
        }else{
            cursor.close();
            db.close();
            db=helper.getWritableDatabase();
            Gson gson=new Gson();
            String g=gson.toJson(bean);
            ContentValues values=new ContentValues();
            values.put("news_content",g);
            values.put("news_page",page);
            db.update("news", values, "news_channel=?", new String[]{channelId});
            db.close();
            System.out.println(channelId + "====更新数据库新闻数据");
        }

    }



}
