package com.example.androidphotos59;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.androidphotos59.model.Album;
import com.example.androidphotos59.model.Photo;
import com.example.androidphotos59.model.User;
import com.example.androidphotos59.utils.ToastMessage;

public class SlideshowActivity extends AppCompatActivity {

    private ImageView slideShowPhotoView;

    private Button nextPictureButton;

    private Button previousPictureButton;

    private Button openPhotoButton;

    private Button movePictureButton;

    private User activeUser;

    private Album activeAlbum;

    private ImageAdapter imageAdapter;

    private Photo chosenPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slideshow);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        Intent intent = getIntent();
        this.activeUser = (User) intent.getSerializableExtra("activeUser");

        // Album List View
        this.slideShowPhotoView = (ImageView) findViewById(R.id.slideShowPhotoView);
        this.nextPictureButton = (Button) findViewById(R.id.nextPictureButton);
        this.previousPictureButton = (Button) findViewById(R.id.previousPictureButton);
        this.openPhotoButton = (Button) findViewById(R.id.openPhotoButton);
        this.movePictureButton = (Button) findViewById(R.id.movePictureButton);
//        this.imageAdapter = new ImageAdapter(this, activeAlbum.getPhotosInAlbum());
//        this.slideShowPhotoView.setAdapter(imageAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Context currActivityContext = this;
        activeAlbum = activeUser.getActiveAlbum();

        if (activeAlbum.getNumOfPhotosInAlbum() > 0)
        {
//            chosenPhoto = activeAlbum.getPhotosInAlbum().get(0);
//            Drawable drawable  = Drawable.createFromPath(chosenPhoto.getFile());
//            slideShowPhotoView.setImageDrawable(drawable);
        }

        this.openPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SlideshowActivity.this, PhotoActivity.class);
                intent.putExtra("activeUser", activeUser);
                startActivity(intent);
            }
        });

        this.nextPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activeAlbum.getNextPhoto(chosenPhoto) != null) {
                    Drawable drawable = Drawable.createFromPath(chosenPhoto.getFile());
                    slideShowPhotoView.setImageDrawable(drawable);
                    chosenPhoto = activeAlbum.getNextPhoto(chosenPhoto);
                }
                else
                {
                    ToastMessage.showToast(currActivityContext, "No Further Photos!");
                }
            }
        });

        this.previousPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activeAlbum.getPreviousPhoto(chosenPhoto) != null) {
                    Drawable drawable = Drawable.createFromPath(chosenPhoto.getFile());
                    slideShowPhotoView.setImageDrawable(drawable);
                    chosenPhoto = activeAlbum.getNextPhoto(chosenPhoto);
                }
                else
                {
                    ToastMessage.showToast(currActivityContext, "No Further Photos!");
                }
            }
        });

        this.movePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
