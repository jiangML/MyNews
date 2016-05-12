package com.me.jiang.mynews.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.style.TtsSpan;

/**
 * Created by Administrator on 2016/5/9.
 * 一些简单的基本工具
 */
public class Baseutil {

    /**
     * 半角转全角
     * @param input
     * @return
     */
    public static String ToDBC(String input)
    {
        char[] ch=input.toCharArray();
        for(int i=0 ,len=ch.length;i<len;i++)
        {
            if(ch[i]==12288)
            {
                ch[i]=(char)32;
                continue;
            }
            if(ch[i]>65280&&ch[i]<65375)
            {
                ch[i]=(char)(ch[i]-65248);
            }
        }
        return new String(ch);
    }

    public static final int NETTYPE_WIFI=0x100;
    public static final int NETTYPE_CMWAP=0x101;
    public static final int NETTYPE_CMNET=0x102;
    public static final int NOT_NET=0X103;

    /**
     * 获取网络状态
     * @param context
     * @return 0x100 wifi,0x101 wap, 0x102 net, 0x103 无网
     */
   public static  int getNetType(Context context)
   {
       int type=NOT_NET;
       ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
       NetworkInfo  info=connectivityManager.getActiveNetworkInfo();
       if(info==null)
       {
           return type;
       }
       int tp=info.getType();
       if(tp==ConnectivityManager.TYPE_MOBILE)
       {
           String extrainfo=info.getExtraInfo();
           if("cmnet".equals(extrainfo))
           {
               type=NETTYPE_CMWAP;
           }else{
               type=NETTYPE_CMNET;
           }
       }else{
           type=NETTYPE_WIFI;
       }

       return type;
   }






}
