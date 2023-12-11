package com.example.androidphotos59;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.ListView;

import com.example.androidphotos59.model.User;

public class PhotoActivity extends AppCompatActivity {
    private ListView photoListView;
    private Button addPhotoButton;
    private Button deletePhotoButton;
    private Button addTagButton;
    private Button deleteTagButton;
    private Button slideShowButton;
    private Button backAlbumButton;

    private User activeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_view);

        this.addPhotoButton = findViewById(R.id.addPhotoButton);
        this.deletePhotoButton = findViewById(R.id.backAlbumButton);
        this.addTagButton = findViewById(R.id.addTagButton);
        this.deleteTagButton = findViewById(R.id.deleteTagButton);

//        this.movePhotoButton = findViewById(R.id.movePhotoButton);
        this.slideShowButton = findViewById(R.id.slideShowButton);
        this.backAlbumButton = findViewById(R.id.backAlbumButton);

        Intent intent = getIntent();
        this.activeUser = (User) intent.getSerializableExtra("activeUser");
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.addPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the modal (dialog)
                // openAlbumSaveModal();
            }
        });

        this.deletePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the modal (dialog)
                // openAlbumSaveModal();
            }
        });

//        this.movePhotoButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Open the modal (dialog)
//                // openAlbumSaveModal();
//            }
//        });

        this.slideShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the modal (dialog)
                // openAlbumSaveModal();
            }
        });

        this.backAlbumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the modal (dialog)
                // openAlbumSaveModal();
            }
        });
    }


}