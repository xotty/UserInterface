/**本例演示了android所有WebView的主要用法和流程：
 * 1)在xml中定义一个WebView控件
 * 2)在java代码中设置webview的相关属性
 * 3)加载网页或自定义的html数据
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Sept，2017
 * @author xottys@163.com
 * @version 1.0
 */

package org.xottys.userinterface;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends Activity {
    
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        
        setContentView(R.layout.webview);
        
        WebView wv;
        
        wv = (WebView) findViewById(R.id.wv);

        //WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
        wv.setWebChromeClient(new WebChromeClient());
        wv.getSettings().setJavaScriptEnabled(true);

        //WebViewClient主要帮助WebView处理各种通知、请求事件的,希望点击链接继续在当前browser中响应，必须覆盖 WebViewClient对象
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

        //加载html数据
        //final String mimeType = "text/html";
        //wv.loadData("<a href='x'>Hello World! - 1</a>", mimeType, null);

        //加载网页
        wv.loadUrl("http://www.baidu.com");

    }
}
