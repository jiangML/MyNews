package com.me.jiang.mynews.util;

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


}
