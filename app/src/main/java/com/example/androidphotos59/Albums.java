package com.example.androidphotos59;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class Albums extends AppCompatActivity
{
        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.album_view);

                //Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
                //setSupportActionBar(myToolbar);
        }
}