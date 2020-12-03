package com.tideit.webview;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 不显示是程序的标题栏
        setContentView(R.layout.activity_main);
        //WebView
        WebView browser=(WebView)findViewById(R.id.Toweb);
        //此方法可以在webview中打开链接而不会跳转到外部浏览器
        browser.setWebViewClient(new WebViewClient());
        //此方法可以启用html5页面的javascript
        browser.getSettings().setJavaScriptEnabled(true);
        browser.loadUrl("http://puladuo.vip/");

        //设置可自由缩放网页
        browser.getSettings().setSupportZoom(true);
        browser.getSettings().setBuiltInZoomControls(true);
        browser.getSettings().getAllowContentAccess();
        browser.getSettings().getAllowFileAccess();
        browser.getSettings().getAllowFileAccessFromFileURLs();
        // 如果页面中链接，如果希望点击链接继续在当前browser中响应，
        // 而不是新开Android的系统browser中响应该链接，必须覆盖webview的WebViewClient对象
        browser.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                try{
                    if(url.startsWith("snssdk1128://")){
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        MainActivity.this.startActivity(intent);
                        return true;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    return false;
                }
                view.loadUrl(url);
                return true;
            }

        });
    }
    //go back
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        WebView browser=(WebView)findViewById(R.id.Toweb);
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && browser.canGoBack()) {
            browser.goBack();
            return true;
        }
        //  return true;
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        WebView browser=(WebView)findViewById(R.id.Toweb);
        //释放资源
        browser.destroy();
    }
}