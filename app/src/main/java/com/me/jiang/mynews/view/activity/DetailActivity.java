package com.me.jiang.mynews.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.me.jiang.mynews.R;

/**
 * Created by Administrator on 2016/5/11.
 */
public class DetailActivity extends BaseActivity {

    private Toolbar toolbar;
    private WebView webView;
    private String tite;
    private String url;
    public static final String TITLE="title";
    public static final String URL="url";
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
    }

    private void init()
    {
        toolbar=(Toolbar)findViewById(R.id.tb);
        webView=(WebView)findViewById(R.id.wv);
        progressBar=(ProgressBar)findViewById(R.id.progress);
        Intent intent=getIntent();
        if(intent!=null)
        {
            tite=intent.getStringExtra(TITLE);
            url=intent.getStringExtra(URL);
        }
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setTitle(tite);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
        });
        webView.loadUrl(url);

    }

    public static void   startActiv(Activity activity,String tite,String url)
    {
            Intent intent=new Intent(activity,DetailActivity.class);
            intent.putExtra(TITLE, tite);
            intent.putExtra(URL, url);
            activity.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                this.finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
