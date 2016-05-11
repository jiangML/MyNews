package com.me.jiang.mynews.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.me.jiang.mynews.R;
import com.me.jiang.mynews.bean.NewsBean;
import com.me.jiang.mynews.util.Baseutil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/9.
 */
public class RecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private NewsBean newsBean;
    private static final int TYPE_REFRESH=0x100;//刷新
    private static final int TYPE_LOAD_MORE=0x101;//加载更多
    private static final int TYPE_ITEM=0x103;//item
    public static final int LOADING=0X200;//正在加载更多
    public static final int LOAD_FINSH=0x201;//加载完成
    public static final int LOAD_START=0X202;//开始加载更多
    public static final int HIDE_LOAD_VIEW=0x203;//隐藏加载更多
    private ItemOnChickListener listener;
    private  int load_Staust=LOAD_START;
    private List<NewsBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> news=new ArrayList<>();
    private LayoutInflater inflater;

    public interface ItemOnChickListener
    {
        void onItemChick(View view,NewsBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean news,int position);
    }

    public RecycleViewAdapter(Context context,NewsBean bean)
    {
        this.context=context;
        this.newsBean=bean;
        news=bean.getShowapi_res_body().getPagebean().getContentlist();
        inflater=LayoutInflater.from(context);
    }


    @Override
    public int getItemViewType(int position) {
        if(position+1==getItemCount())
        {
            return TYPE_LOAD_MORE;
        }else{
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return news!=null?news.size()+1:1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(holder  instanceof NewsItemView)
        {
            NewsItemView itemView=(NewsItemView)holder;
            itemView.tv_title.setText(news.get(position).getTitle());
            itemView.tv_from.setText(news.get(position).getSource());
            String content=Baseutil.ToDBC(news.get(position).getDesc());
            content.replaceAll("\r|\n", "").replaceAll(" ","");
            itemView.tv_content.setText(Html.fromHtml(content));
            itemView.tv_pubdata.setText(news.get(position).getPubDate());

            if(news.get(position).getImageurls()!=null&&news.get(position).getImageurls().size()>0)
            {
                Glide.with(context).load(news.get(position).getImageurls().get(0).getUrl()).into(itemView.iv_img);
            }else{
                itemView.iv_img.setVisibility(View.GONE);
            }
            itemView.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  if(listener!=null)
                  {
                    listener.onItemChick(v,news.get(position),position);
                  }
                }
            });

        }else{
            FooterView footerView=(FooterView)holder;
           switch (load_Staust)
           {
               case LOAD_START :
                   footerView.itemView.setVisibility(View.VISIBLE);
                   footerView.tv_load_more.setText("加载更多");
                   break;
               case LOADING:
                   footerView.itemView.setVisibility(View.VISIBLE);
                   footerView.tv_load_more.setText("正在加载更多...");
                   break;
               case LOAD_FINSH:
                   footerView.itemView.setVisibility(View.VISIBLE);
                   footerView.tv_load_more.setText("正在加载更多...");
                   break;
               case HIDE_LOAD_VIEW:
                   footerView.itemView.setVisibility(View.GONE);
                   break;
           }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=null;
        if(TYPE_ITEM==viewType)
        {
            view=inflater.inflate(R.layout.item_news,parent,false);
            return new NewsItemView(view);
        }else
        {
            view=inflater.inflate(R.layout.footer,parent,false);
            return new FooterView(view);
        }
    }



    /**
     * 添加新闻数据
     * @param bean
     */
    public void addItem(NewsBean bean)
    {
        if(bean!=null&&bean.getShowapi_res_body().getPagebean().getContentlist().size()>0)
        {
            news.addAll(bean.getShowapi_res_body().getPagebean().getContentlist());
        }
        load_Staust=LOAD_FINSH;
        notifyDataSetChanged();
    }

    /**
     * 刷新数据
     * @param bean
     */
    public void refresh(NewsBean bean)
    {
        if(bean!=null&&bean.getShowapi_res_body().getPagebean().getContentlist().size()>0)
        {
            news.clear();
            news.addAll(bean.getShowapi_res_body().getPagebean().getContentlist());
        }
        notifyDataSetChanged();
    }


    /**
     * 改变加载状态提示
     * @param status
     */
    public void setStatus(int status)
    {
        load_Staust=status;
        notifyDataSetChanged();
    }

    private static class FooterView extends RecyclerView.ViewHolder
    {
        TextView tv_load_more;
        FooterView(View itemView)
        {
            super(itemView);
            tv_load_more=(TextView)itemView.findViewById(R.id.tv_load_more);
        }
    }

    private static class NewsItemView extends RecyclerView.ViewHolder
    {
        TextView tv_title;
        TextView tv_from;
        TextView tv_content;
        ImageView iv_img;
        TextView tv_pubdata;
        NewsItemView(View itemView)
        {
            super(itemView);
            tv_title=(TextView)itemView.findViewById(R.id.tv_title);
            tv_from=(TextView)itemView.findViewById(R.id.tv_from);
            tv_content=(TextView)itemView.findViewById(R.id.tv_content);
            tv_pubdata=(TextView)itemView.findViewById(R.id.tv_pubdata);
            iv_img=(ImageView)itemView.findViewById(R.id.iv_img);
        }
    }


    /**
     * 隐藏底部加载更多view
     */
    public void hideFooterView()
    {
        load_Staust=HIDE_LOAD_VIEW;
        notifyDataSetChanged();
    }

    /**
     * 设置每项点击监听
     * @param listener
     */
    public void  setOnItemChickListener(ItemOnChickListener listener)
    {
        this.listener=listener;
    }


}
