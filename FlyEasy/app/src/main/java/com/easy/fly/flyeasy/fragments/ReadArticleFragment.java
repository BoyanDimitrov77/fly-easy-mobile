package com.easy.fly.flyeasy.fragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.easy.fly.flyeasy.R;
import com.easy.fly.flyeasy.di.Injectable;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReadArticleFragment extends Fragment implements Injectable {


    @BindView(R.id.web_view)
    WebView webView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private String url;

    public ReadArticleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_read_article, container, false);
        ButterKnife.bind(this,inflate);

        initKey();

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon){
                // Do something on page loading started
                // Visible the progressbar
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url){
                // Do something when page loading finished
                progressBar.setVisibility(View.GONE);
//                Toast.makeText(getContext(),"Page Loaded.",Toast.LENGTH_SHORT).show();
            }

        });

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);

                if(newProgress == 100){
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);

        return inflate;
    }

    private void initKey(){
        url = (String)getArguments().get("URL");
    }

}
