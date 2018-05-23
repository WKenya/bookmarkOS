package com.weskenyon.bookmarkos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity
{
    private WebView myWebView;
    private MyWebViewClient myWebViewClient;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        Toolbar toolbar = ( Toolbar ) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        myWebView = ( WebView ) findViewById( R.id.webview );
        WebSettings webSettings = myWebView.getSettings( );
        webSettings.setJavaScriptEnabled( true );

        String intentUrl = handleIntent( );

        myWebViewClient = new MyWebViewClient( intentUrl );
        myWebView.setWebViewClient( myWebViewClient );
        myWebView.loadUrl( Constants.BookmarkOSURL );
    }

    @Override
    public boolean onKeyDown( int keyCode, KeyEvent event )
    {
        // Check if the key event was the Back button and if there's history
        if ( ( keyCode == KeyEvent.KEYCODE_BACK ) && myWebView.canGoBack( ) )
        {
            myWebView.goBack( );
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown( keyCode, event );
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater( ).inflate( R.menu.menu_main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item )
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId( );

        //noinspection SimplifiableIfStatement
        if ( id == R.id.action_settings )
        {
            return true;
        }

        return super.onOptionsItemSelected( item );
    }

    public String handleIntent()
    {
        Intent intent = getIntent( );
        String action = intent.getAction( );
        String type = intent.getType( );

        if ( Intent.ACTION_SEND.equals( action ) && type != null )
        {
            if ( type.equals( Constants.TextPlainMimeType ) )
            {
                return getUrl( intent );
            }
        }
        return "";
    }

    private String getUrl( Intent intent )
    {
        String sharedText = intent.getStringExtra( Intent.EXTRA_TEXT );
        if ( sharedText != null && URLUtil.isValidUrl( sharedText ) )
        {
            return sharedText;
        }
        return "";
    }
}
