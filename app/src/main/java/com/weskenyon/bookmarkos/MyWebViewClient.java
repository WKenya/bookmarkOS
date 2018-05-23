package com.weskenyon.bookmarkos;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static android.content.ContentValues.TAG;
import static android.support.v4.content.ContextCompat.startActivity;

public class MyWebViewClient extends WebViewClient
{
    private String _intentURL;

    public MyWebViewClient( String intentURL )
    {
        _intentURL = intentURL;
    }

    @Override
    public boolean shouldOverrideUrlLoading( WebView view, String url )
    {
        String givenURI = Uri.parse( url ).getHost( );
        if ( Constants.BookmarkOSURL.contains( givenURI ) )
        {
            // This is my web site, so do not override; let my WebView load the page
            return false;
        }

        // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
        Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( url ) );
        startActivity( view.getContext( ), intent, null );
        return true;
    }

    @Override
    public final void onPageFinished( WebView view, String url )
    {
        super.onPageFinished( view, url );
        if ( !_intentURL.equals( "" ) )
        {
            view.evaluateJavascript( "(function() {" +
                    "newBookmark();" +
                    "$(\"#web_page_url\")[0].value = \"" + _intentURL + "\";" +
                    "})()", new ValueCallback< String >( )
            {
                @Override
                public void onReceiveValue( String s )
                {
                }
            } );
        }
        Log.d( TAG, "The webView with the following url " + url + " has finished loading" );
    }

    @Override
    public void onPageStarted( WebView view, String url, Bitmap favicon )
    {
        super.onPageStarted( view, url, favicon );
        Log.d( TAG, "The webView with the following url " + url + " has started loading" );
    }
}
