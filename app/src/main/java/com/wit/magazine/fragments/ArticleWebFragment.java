package com.wit.magazine.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wit.magazine.R;

public class ArticleWebFragment extends Fragment {

    private WebView webView;

    public ArticleWebFragment(){

    }

    public static ArticleWebFragment newInstance(Bundle articleURL) {
        ArticleWebFragment fragment = new ArticleWebFragment();
        fragment.setArguments(articleURL);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_articlewebpage, container, false);
        webView=(WebView) v.findViewById(R.id.articleWebView);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(getArguments().getString("url"));
        return v;
    }
}
