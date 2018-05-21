package com.weskenyon.bookmarkos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SharedActivity extends AppCompatActivity {
    public static final String textPlainMimeType = "text/plain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if(Intent.ACTION_SEND.equals(action) && type != null)
        {
            if(type.equals(textPlainMimeType))
            {
                handleSentText(intent);
            }
        }
    }

    private void handleSentText(Intent intent)
    {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if(sharedText != null)
        {
        }
    }
}
