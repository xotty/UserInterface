/**
 * 本例演示了WebViewFragment的基本用法：
 * 1)在onCreateView中获取WebView实例，然后用其加载网页或html文本
 * 2)加载Fragment(此步骤本例简化成直接用MyWebViewFragment替换android.R.id.content)
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:FragmentWebViewActivity
 * <br/>Date:Sep，2017
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.CommonlyUsedFragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebViewFragment;

public class FragmentWebViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载fragment，并将其作为主显示内容
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new MyWebViewFragment()).commit();
    }


    public static class MyWebViewFragment extends WebViewFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        //获得WebView，并在其中显示百度首页
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle data) {
            WebView wv = (WebView) super.onCreateView(inflater, container, data);

            //设置webview的相关属性
            wv.setWebChromeClient(new WebChromeClient());
            wv.getSettings().setJavaScriptEnabled(true);
            wv.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                }
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                }
            });
            //加载网页，也可以用loadData加载html文本
            wv.loadUrl("http://www.baidu.com");
            return wv;
        }
    }
}
