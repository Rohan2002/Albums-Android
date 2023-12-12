package com.example.androidphotos59;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.androidphotos59.model.Album;
import com.example.androidphotos59.model.User;
import com.example.androidphotos59.utils.ToastMessage;

public class SearchActivity extends AppCompatActivity {
    private EditText searchField;
    private Button searchButton;

    private ListView searchOutput;

    private ImageAdapter imageAdapter;

    private User activeUser;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        Intent intent = getIntent();
        this.activeUser = (User) intent.getSerializableExtra("activeUser");

        searchField = findViewById(R.id.searchText);
        searchButton = findViewById(R.id.searchTagsButton);
        searchOutput = findViewById(R.id.searchResultsList);
    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//        Context searchContext = this;
//
//        this.imageAdapter = new ImageAdapter(this, photoArrayList);
//        this.photoListView.setAdapter(imageAdapter);
//        searchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String tag = searchField.getText().toString();
//                if (tag.length() < 1) {
//                    ToastMessage.showToast(searchContext, "Invalid search query");
//                    return;
//                }
//                Album tagSearchAlbum = activeUser.getActiveAlbum().searchByTag(tag);
//                if (tagSearchAlbum == null) {
//                    ToastMessage.showToast(searchContext, "Query format: A=B, A=B OR C=D, A=B AND C=D");
//                    return;
//                }
//            }
//        });
//    }
}
