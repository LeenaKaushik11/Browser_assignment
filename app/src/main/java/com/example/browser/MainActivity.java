package com.example.browser;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.browser.connection.checkInternet;
import com.example.browser.data.dbhelper_book;
import com.example.browser.data.dbhelper_hist;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.net.URISyntaxException;


public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    BroadcastReceiver broadcastReceiver=new checkInternet();
   private WebView webView;
   private ImageButton backButton;
   private BottomSheetDialog bottomSheetDialog;
   ImageButton rightButton;
   ImageButton home;
   ImageButton cross;
   ImageButton load;
   ProgressBar pb;
   EditText link;
   SwipeRefreshLayout refresh;
   dbhelper_book dbhelperBook;
   dbhelper_hist dbhelperHist;
   ImageButton bookmark;
//   ImageButton menuu;
   ImageButton meu;
   ActivityResultLauncher<Intent> content;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("inreci", "oncreate: ");



//icon color -> black
//        this.getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS);
//icon color -> white
//        this.getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(0, APPEARANCE_LIGHT_STATUS_BARS);
        pb = (ProgressBar) findViewById(R.id.progress);
        link = (EditText) findViewById(R.id.urll);

        webView = (WebView) findViewById(R.id.web_view);
//        webView.setWebChromeClient(new WebChromeClientDemo());
//        webView.setWebViewClient(new WebViewClientDemo());
        webView.loadUrl("https://www.google.com/");

        cross = (ImageButton) findViewById(R.id.cross);
        cross.setOnClickListener(this);
        backButton = (ImageButton) findViewById(R.id.backButton);
        backButton.setOnClickListener(this);
        rightButton = (ImageButton) findViewById(R.id.frontButton);
        rightButton.setOnClickListener(this);
        home = (ImageButton) findViewById(R.id.home);
        home.setOnClickListener(this);
//        menuu = (ImageButton) findViewById(R.id.menu);
//        menuu.setOnClickListener(this);
        meu=(ImageButton)findViewById(R.id.meu);
//        dbhelperBook = new dbhelper_book(this, null, null, 1);
//        dbhelperHist=new dbhelper_hist(this,null,null,1);
        bookmark = (ImageButton) findViewById(R.id.bookmark);
        load = (ImageButton) findViewById(R.id.search);
//        meu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                bottomSheetDialog=new BottomSheetDialog(MainActivity.this,R.style.BottomSheetTheme);
//                View sheetview= LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_sheet_menu,null);
//                sheetview.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        bottomSheetDialog.dismiss();
//                    }
//                });
//                sheetview.findViewById(R.id.bb).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent book=new Intent(MainActivity.this,Bookmarks.class);
//                        content.launch(book);
//                        bottomSheetDialog.dismiss();
//                    }
//                });
//                sheetview.findViewById(R.id.hist).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
////                        Toast.makeText(MainActivity.this,"history pressed",Toast.LENGTH_SHORT).show();
//                        Intent book=new Intent(MainActivity.this,History.class);
//                        content.launch(book);
//                        bottomSheetDialog.dismiss();
//                    }
//                });
//                sheetview.findViewById(R.id.set).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
////                        Toast.makeText(MainActivity.this,"setting pressed",Toast.LENGTH_SHORT).show();
//                        Intent set=new Intent(MainActivity.this,Settings.class);
////                        startActivity(set);
//                        content.launch(set);
//                        bottomSheetDialog.dismiss();
//                    }
//                });
//                bottomSheetDialog.setContentView(sheetview);
//                bottomSheetDialog.show();
//            }
//        });

//        ////to deal with cross visible/invisible
//        link.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                if (hasWindowFocus()) {
//                    cross.setVisibility(View.VISIBLE);
//                }
//            }
//        });

        ////to makke crooss visible and invisible
        TextWatcher textWatcher = new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    cross.setVisibility(View.GONE);
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                link.setSelectAllOnFocus(true);

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                cross.setVisibility(View.VISIBLE);

            }
        };
        link.addTextChangedListener(textWatcher);
//        //this is for keyboard enter
//        link.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    search(link);
//                    return true;
//                }
//                return false;
//            }
////        });
//        load.setOnClickListener(this);
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        webSettings.setJavaScriptEnabled(sharedPref.getBoolean("javas",false));
//        CookieManager.getInstance().setAcceptCookie(sharedPref.getBoolean("switch2",false));
//        Log.i("javasss", "onCreate: "+sharedPref.getBoolean("javas",false));
//        //webSettings.setJavaScriptEnabled(true);
//        //to zoom in
//        webView.getSettings().setSupportZoom(true);
//        webView.getSettings().setBuiltInZoomControls(true);
//        webView.getSettings().setDisplayZoomControls(false);
//        //this line elow helped to solve the smooth scroll horizontal in top stories
//        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//        ////this line solved udacity site problem
//        webSettings.setDomStorageEnabled(true);
//        Log.i("bfffcd", "onCreate: " + webView.getUrl() + "             " + link.toString());
//        //prog();
//        refresh = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
//        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                urlFinished="";
//                webView.reload();
//
//            }
//        });
//        bookmark.setOnClickListener(this);
//        content = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
//            @Override
//            public void onActivityResult(ActivityResult result) {
//                if (result != null && result.getResultCode() == RESULT_OK) {
//                    if (result.getData() != null && result.getData().getStringExtra(Bookmarks.KEY_NAME) != null) {
//                        webView.loadUrl(result.getData().getStringExtra(Bookmarks.KEY_NAME));
//                    }
//                }
//                if (result != null && result.getResultCode() == RESULT_OK) {
//                    if (result.getData() != null && result.getData().getStringExtra(History.KEY_NAME) != null) {
//                        urlFinished="";
//                        webView.loadUrl(result.getData().getStringExtra(History.KEY_NAME));
//                    }
//                }
//                if (result != null && result.getResultCode() == RESULT_OK) {
//                    if (result.getData() != null && result.getData().getStringExtra(Bookmarks.KEY_NAM) != null) {
//                        urlFinished="";
//                        webView.reload();
//                    }
//                }
//                if (result != null && result.getResultCode() == RESULT_OK) {
//                    if (result.getData() != null && result.getData().getStringExtra(History.KEY_NM) != null) {
//                        Log.i("whatthehh", "backh: ");
////                        urlFinished="";
////                        webView.reload();
//                    }
//                }
//                if (result != null && result.getResultCode() == RESULT_OK) {
//                    if (result.getData() != null && result.getData().getStringExtra(Settings.KE) != null) {
//                    }
//                }
//            }
//        });
//        checkInternet broadcastReceiver=new checkInternet();
//        IntentFilter intentFilter = new
//                IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
//
//
//        registerReceiver(broadcastReceiver, intentFilter);

//        broadcastReceiver.setConnectionChangeCallback(this::onConnectionChange);

        //wifi check
//        broadcastReceiver=new checkInternet();
//        Internetstaus();

    }
    public void Internetstaus(){
        registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }


//    @Override
//    public void onConnectionChange(boolean isConnected) {
//        if(isConnected){
//            webView.reload();
//        }
//            // will be called when internet is back
//        else{ }}
//            // will be called when internet is gone.

    @Override
    protected void onStart() {
        Log.i("inreci", "onStart:");
        broadcastReceiver=new checkInternet();
        Internetstaus();
//        SharedPreferences sharedPref = getSharedPreferences("A", Context.MODE_PRIVATE);
        webView.setWebChromeClient(new WebChromeClientDemo());
        webView.setWebViewClient(new WebViewClientDemo());
//        webView.loadUrl("https://www.google.com/");
        dbhelperBook = new dbhelper_book(this, null, null, 1);
        dbhelperHist=new dbhelper_hist(this,null,null,1);
        meu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog=new BottomSheetDialog(MainActivity.this,R.style.BottomSheetTheme);
                View sheetview= LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_sheet_menu,null);
                sheetview.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.dismiss();
                    }
                });
                sheetview.findViewById(R.id.bb).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent book=new Intent(MainActivity.this,Bookmarks.class);
                        content.launch(book);
                        bottomSheetDialog.dismiss();
                    }
                });
                sheetview.findViewById(R.id.hist).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Toast.makeText(MainActivity.this,"history pressed",Toast.LENGTH_SHORT).show();
                        Intent book=new Intent(MainActivity.this,History.class);
                        content.launch(book);
                        bottomSheetDialog.dismiss();
                    }
                });
                sheetview.findViewById(R.id.set).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Toast.makeText(MainActivity.this,"setting pressed",Toast.LENGTH_SHORT).show();
                        Intent set=new Intent(MainActivity.this,Settings.class);
//                        startActivity(set);
                        content.launch(set);
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.setContentView(sheetview);
                bottomSheetDialog.show();
            }
        });
//        //this is for keyboard enter
//        link.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    search(link);
//                    return true;
//                }
//                return false;
//            }
//        });
//        ////to deal with cross visible/invisible
//        link.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                if (hasWindowFocus()) {
//                    cross.setVisibility(View.VISIBLE);
//                }
//            }
//        });

//        load.setOnClickListener(this);
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        webSettings.setJavaScriptEnabled(sharedPref.getBoolean("javas",false));
//        CookieManager.getInstance().setAcceptCookie(sharedPref.getBoolean("switch2",false));
//        Log.i("javasss", "onCreate: "+sharedPref.getBoolean("javas",false));
//        //webSettings.setJavaScriptEnabled(true);
//        //to zoom in
//        webView.getSettings().setSupportZoom(true);
//        webView.getSettings().setBuiltInZoomControls(true);
//        webView.getSettings().setDisplayZoomControls(false);
//        //this line elow helped to solve the smooth scroll horizontal in top stories
//        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//        ////this line solved udacity site problem
//        webSettings.setDomStorageEnabled(true);
        Log.i("bfffcd", "onCreate: " + webView.getUrl() + "             " + link.toString());
        //prog();
        refresh = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                urlFinished="";
                webView.reload();

            }
        });
        bookmark.setOnClickListener(this);
        content = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result != null && result.getResultCode() == RESULT_OK) {
                    if (result.getData() != null && result.getData().getStringExtra(Bookmarks.KEY_NAME) != null) {
                        webView.loadUrl(result.getData().getStringExtra(Bookmarks.KEY_NAME));
                    }
                }
                if (result != null && result.getResultCode() == RESULT_OK) {
                    if (result.getData() != null && result.getData().getStringExtra(History.KEY_NAME) != null) {
                        urlFinished="";
                        webView.loadUrl(result.getData().getStringExtra(History.KEY_NAME));
                    }
                }
                if (result != null && result.getResultCode() == RESULT_OK) {
                    if (result.getData() != null && result.getData().getStringExtra(Bookmarks.KEY_NAM) != null) {
                        Log.i("hereidie", "onActivityResult:book ");
                        webView.reload();
                    }
                }
                if (result != null && result.getResultCode() == RESULT_OK) {
                    if (result.getData() != null && result.getData().getStringExtra(History.KEY_NM) != null) {
                        Log.i("whatthehhistory", "backh: ");
                        Log.i("hereidie", "onActivityResult:his ");
//                        urlFinished="";
                        webView.stopLoading();
//                        webView.reload();
                    }
                }
                Log.i("hereidie", "onActivityResult:sett "+ result+result.getResultCode());
                if (result != null && result.getResultCode() == RESULT_OK) {
                    Log.i("hereidie", "onActivityResult:settin1 ");

                    if (result.getData() != null && result.getData().getStringExtra(Settings.KE) != null) {
                        Log.i("whatthehh", "backh: ");
                        Log.i("hereidie", "onActivityResult:sett ");

                        webView.stopLoading();
                    }
                }
            }
        });
        super.onStart();
//        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
//        registerReceiver(broadcastReceiver,intentFilter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("inreci", "onStop: ");
//        unregisterReceiver(broadcastReceiver);

//        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);

        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("inreci", "onDestroy:");
//        unregisterReceiver(broadcastReceiver);

//        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);

//        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        unregisterReceiver(broadcastReceiver);
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
//        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onResume() {
        Log.i("inreci", "onResume: ");
        super.onResume();
        SharedPreferences sharedPref = getSharedPreferences("A", Context.MODE_PRIVATE);
        //this is for keyboard enter
        link.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search(link);
                    return true;
                }
                return false;
            }
        });
        load.setOnClickListener(this);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        webSettings.setJavaScriptEnabled(false);
//        CookieManager.getInstance().setAcceptCookie(false);
        Log.i("javasss", "onCreate: "+sharedPref.getBoolean("javas",false));
        //webSettings.setJavaScriptEnabled(true);
        //to zoom in
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        //this line elow helped to solve the smooth scroll horizontal in top stories
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        ////this line solved udacity site problem
        webSettings.setDomStorageEnabled(true);
        ////to deal with cross visible/invisible
        link.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (hasWindowFocus()) {
                    cross.setVisibility(View.VISIBLE);
                }
            }
        });
        Log.i("inreci", "onResume: "+sharedPref.getBoolean("javas",true));
        webSettings.setJavaScriptEnabled(sharedPref.getBoolean("javas",false));
        boolean sw2=sharedPref.getBoolean("switch2",false);
        CookieManager.getInstance().setAcceptCookie(sw2);
//        broadcastReceiver=new checkInternet();
//        Internetstaus();
//        registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

//        IntentFilter intentFilter = new
//                IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
//        registerReceiver(broadcastReceiver,intentFilter);

//        WebSettings webSettings = webView.getSettings();
//        SharedPreferences sharedPref = getSharedPreferences("A", Context.MODE_PRIVATE);
//        SharedPreferences sharedPref1 = getSharedPreferences("B", Context.MODE_PRIVATE);
        if(Settings.c==true){
            Log.i("inreci", "onResume: c "+Settings.c);
            urlFinished="";
        webView.reload();
//        Settings.c=false;
            Log.i("inreci", "onResume: c down "+Settings.c);
        }
        if(Settings.c1==true){
            Log.i("inreci", "onResume: c1 "+Settings.c1);

//            CookieManager.getInstance().setAcceptThirdPartyCookies(webView,sw2);
//            if(sw2==true){
////                urlFinished="";
//            webView.reload();
//                Log.i("inreci", "onResume: sw2"+sw2);
//            }
//            else {
//                urlFinished="";
//                CookieManager.getInstance().flush();
//                webView.reload();
//            }
//            Settings.c1=false;
            Log.i("inreci", "onResume: c1 down"+Settings.c1);

        }


    }



    //    private void onBookPressed() {
//        Websites web=new Websites(webView.getUrl().toString());
//        dbhelperBook.addURL(web);
//    }
    private void saveData(){
        Websites web=new Websites(webView.getUrl(),webView.getTitle());
            int t=dbhelperHist.checkifsecond(webView.getTitle());
        int w=dbhelperHist.checklast(webView.getTitle());
        if (t!=-7){
            Log.i("lemmee", "saveData tt: "+t);
            dbhelperHist.deleteURL(t);
            dbhelperHist.addURL(web);
        }
        if(w!=-1 && w!=-7){
            Log.i("lemmee", "saveData w: "+t);
            dbhelperHist.deleteURL(w);
            dbhelperHist.addURL(web);
        }
        if((w==-7||w==-1)&&t==-7) {
            Log.i("lemmee", "saveData: "+t+"t"+w);
            dbhelperHist.addURL(web);
        }}
       //for history
    private String urlFinished = "";
    private  class WebViewClientDemo extends  WebViewClient{
        @Override
        public void onLoadResource(WebView view, String url) {

            Log.i("bftrf", "shouldLoadResource: "+webView.getUrl()+"             "+link.toString()+"       url-   "+ url);
            String s=webView.getUrl();
            if(s.contains("youtube.com/watch?")){
                Log.i("bffxxcd", "shouldLoadResourceinnn: youtube "+webView.getUrl()+"          "+view.getUrl()+"             "+webView.getTitle()+"       url-   "+ url);
                link.setText(webView.getUrl());
                if(backButton.isPressed()){
                    onClick(backButton);
                }

            }
            if(s.contains("/amp/s/") ){
                Log.i("bffxxcd", "shouldLoadResourceinnn: "+webView.getUrl()+"             "+link.toString()+"       url-   "+ url);
                if(dbhelperBook.checkAlreadyExist(webView.getUrl().toString()) ){
                    bookmark.setImageResource(R.drawable.ic_book_fill);
                }
                else{
                    bookmark.setImageResource(R.drawable.ic_bookmarki);
                }

//                int uu=url.indexOf("amp");
//                int end=url.length();
//                String finall=url.substring(uu+6,end);
                link.setText(webView.getUrl());
                if(backButton.isPressed()){
                    onClick(backButton);
                }

            }

            super.onLoadResource(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.i("bfffcd", "shouldOverride: "+webView.getUrl()+"             "+link.toString()+"       url-   "+ url);
            if (url == null || url.startsWith("http://") || url.startsWith("https://")) return false;
            ///////this below is to check if the hyperlink is phone number or not
            if(url.contains("mailto:")||url.contains("sms:")||url.contains("tel:")){
                webView.stopLoading();
                Intent i=new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                webView.reload();
            }
            ////if it is google map
            if (url.startsWith("intent"))
                try {
                    Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);

                    String fallbackUrl = intent.getStringExtra("browser_fallback_url");
                    if (fallbackUrl != null) {
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(fallbackUrl));
                        startActivity(intent);
                        webView.reload();
                        //this can be used instead of all above lines to make it in-webview map opening
                        //webView.loadUrl(fallbackUrl);
                        return true;
                    }}

                catch (URISyntaxException e) {

                    Log.i("tum", "shouldOverrideUrlLoading: ");
                    //not an intent uri
                }
                return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.i("bfffcd", "onPagefini:outtt "+webView.getUrl()+"             "+link.toString()+"         url     "+url);

//            if(!urlFinished.equals(url)){
            Log.i("bfffcd", "onPagefini: "+webView.getUrl()+"             "+link.toString()+"         url     "+url);

//            Toast.makeText(MainActivity.this,"Click yesahd  ",Toast.LENGTH_SHORT).show();
            if(dbhelperBook.checkAlreadyExist(url) ){
                bookmark.setImageResource(R.drawable.ic_book_fill);
            }
            else{
                bookmark.setImageResource(R.drawable.ic_bookmarki);
            }
            saveData();
            super.onPageFinished(view, url);
            pb.setVisibility(View.GONE);
            link.setText(url);
            pb.setProgress(100);
            cross.setVisibility(View.INVISIBLE);
            //jab page full loaded to refresh wala gol progress gone
            refresh.setRefreshing(false);
        }
//        urlFinished=url;}
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.i("bfffcd", "onpagestart: "+webView.getUrl()+"             "+link.toString()+"   url"+url+"            favicon   "+favicon);
            super.onPageStarted(view, url, favicon);
            pb.setVisibility(View.VISIBLE);
            pb.setProgress(0);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            Log.i("bstsssssst", "onReceivedError: ");
        }

        @Nullable
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            Log.i("bstssss", "shouldInterceptRequest: ");
            return super.shouldInterceptRequest(view, request);
        }
    }
    private class WebChromeClientDemo extends WebChromeClient{
        public void onProgressChanged(WebView view, int progress) {
            pb.setProgress(progress);
        }
    }
    public  void search(EditText link){
        Log.i("bfffcd", "search: "+webView.getUrl()+"             "+link.toString());

        String url=link.getText().toString();
        if(url.length() != 0){
            performSearch(link);
            if(!(url.startsWith("http")||url.startsWith("https"))){
                url="http://www.google.com/search?q= "+url;
            }
            webView.loadUrl(url);}
    }

    private void performSearch(EditText link) {
        Log.i("bfffcd", "Perform Search "+webView.getUrl()+"             "+link.toString());

        link.clearFocus();
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(link.getWindowToken(), 0);
    }


    public void onForwardPressed(){
        Log.i("bfffcd", "onForward: "+webView.getUrl()+"             "+link.toString());

        if(webView.canGoForward()){
            webView.goForward();
        }
        else{

            Toast.makeText(this,"can't go further",Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onBackPressed() {
        Log.i("bfffcd", "onBackpress: "+webView.getUrl()+"             "+link.toString());

        if (webView.canGoBack()) {
            String historyUrl="";
            WebBackForwardList mWebBackForwardList = webView.copyBackForwardList();
            if (mWebBackForwardList.getCurrentIndex() > 0)
                historyUrl = mWebBackForwardList.getItemAtIndex(mWebBackForwardList.getCurrentIndex()-1).getUrl();
//            Toast.makeText(MainActivity.this,"Click yesahd  "+historyUrl,Toast.LENGTH_SHORT).show();
            Log.i("mxk ",historyUrl+"    " +link.getText().toString());
            if(webView.getUrl().contains("/amp/s")){
                webView.loadUrl(historyUrl);
            }
            else{
                webView.goBack();
            }
        } else {
            super.onBackPressed();
        }
    }
    public void searchie(){
        String url = link.getText().toString();
        if (url.length() != 0) {
            performSearch(link);
            if(!(url.startsWith("http")||url.startsWith("https"))){
                url="http://www.google.com/search?q= "+url;
            }
            webView.loadUrl(url);
        }
    }
    public void booked(){
        Websites web = new Websites(webView.getUrl().toString(), webView.getTitle().toString());
//                Toast.makeText(MainActivity.this,"Click yesahd  "+web.get_url().toString(),Toast.LENGTH_SHORT).show();
        boolean bo = dbhelperBook.addURL(web);
//                Toast.makeText(MainActivity.this,"Page added"+dbhelperBook.databaseToString()+bo,Toast.LENGTH_SHORT).show();
        if (bo) {

            bookmark.setImageResource(R.drawable.ic_book_fill);


//                    content.launch(book);
            Toast.makeText(MainActivity.this, "Page added", Toast.LENGTH_SHORT).show();
        } else {
            bookmark.setImageResource(R.drawable.ic_bookmarki);
            Toast.makeText(MainActivity.this, "Page removed", Toast.LENGTH_SHORT).show();
        }
        //onBookPressed();


    }

    @Override
    public void onClick(View view) {
        Log.i("bfffcd", "onClick: "+webView.getUrl());
        switch (view.getId()){
            case R.id.backButton:

                onBackPressed();
                break;
            case R.id.frontButton:
                onForwardPressed();
                break;
            case R.id.home:
                onhome();
                break;
            case R.id.cross:
                link.getText().clear();
                break;
            case R.id.search:
                searchie();
                break;
            case R.id.bookmark:
                booked();
                break;
//                Intent book=new Intent(MainActivity.this,Bookmarks.class);
//                content.launch(book);
//                break;
        }
    }

    private void onhome() {
        performSearch(link);
        webView.loadUrl("https://www.google.com/");
    }



}