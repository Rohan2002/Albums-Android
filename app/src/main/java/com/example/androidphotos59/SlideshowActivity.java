package com.example.androidphotos59;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SyncStatusObserver;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        Context currActivityContext = this;

        if (activeUser.getActiveAlbum().getNumOfPhotosInAlbum() > 0) {
            chosenPhoto = activeUser.getActiveAlbum().getPhotosInAlbum().get(0);
            Uri u = Uri.parse(chosenPhoto.getFile()); // possible parse error handle.
            currActivityContext.getContentResolver().takePersistableUriPermission(u, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            currActivityContext.getContentResolver().takePersistableUriPermission(u, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            slideShowPhotoView.setImageURI(u);
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
                if (activeUser.getActiveAlbum().getNextPhoto(chosenPhoto) != null) {
                    chosenPhoto = activeUser.getActiveAlbum().getNextPhoto(chosenPhoto);
                    Uri u = Uri.parse(chosenPhoto.getFile()); // possible parse error handle.
                    currActivityContext.getContentResolver().takePersistableUriPermission(u, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    currActivityContext.getContentResolver().takePersistableUriPermission(u, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    slideShowPhotoView.setImageURI(u);
                } else {
                    ToastMessage.showToast(currActivityContext, "No Further Photos!");
                }
            }
        });

        this.previousPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activeUser.getActiveAlbum().getPreviousPhoto(chosenPhoto) != null) {
                    chosenPhoto = activeUser.getActiveAlbum().getPreviousPhoto(chosenPhoto);
                    Uri u = Uri.parse(chosenPhoto.getFile()); // possible parse error handle.
                    currActivityContext.getContentResolver().takePersistableUriPermission(u, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    currActivityContext.getContentResolver().takePersistableUriPermission(u, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    slideShowPhotoView.setImageURI(u);
                } else {
                    ToastMessage.showToast(currActivityContext, "No Further Photos!");
                }
            }
        });

        this.movePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movePhotoAlbumModal(currActivityContext, chosenPhoto);
            }
        });
    }

    private void movePhotoAlbumModal(Context c, Photo photo) {
        // Inflate the custom layout for the dialog
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_layout, null);

        // Find the EditText in the custom layout
        final EditText editTextDialogInput = view.findViewById(R.id.editTextDialogInput);

        // Create the AlertDialog.Builder with the custom layout
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(view)
                .setTitle("Move photo to another album")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String albumName = editTextDialogInput.getText().toString();
                        boolean moveAlbumStatus = activeUser.movePhoto(photo, new Album(albumName));
                        if (moveAlbumStatus) {
                            ToastMessage.showToast(c, "Moved album successfully!");
                            System.out.println(activeUser.getActiveAlbum().getPhotosInAlbum());
                        } else {
                            ToastMessage.showToast(c, "Failed to move album!");
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        // Create and show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
