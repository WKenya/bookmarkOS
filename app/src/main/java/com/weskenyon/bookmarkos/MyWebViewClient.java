package com.weskenyon.bookmarkos;

import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static android.support.v4.content.ContextCompat.startActivity;

class MyWebViewClient extends WebViewClient
{
    @Override
    public boolean shouldOverrideUrlLoading( WebView view, String url )
    {
        if ( Uri.parse(url).getHost().contains(Constants.BookmarkOSURL)) {
            // This is my web site, so do not override; let my WebView load the page
            return false;
        }
        // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(view.getContext(), intent, null);
        return true;
    }
}
