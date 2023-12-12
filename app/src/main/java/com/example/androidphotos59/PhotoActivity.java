package com.example.androidphotos59;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ListView;

import com.example.androidphotos59.model.Album;
import com.example.androidphotos59.model.Photo;
import com.example.androidphotos59.model.User;
import com.example.androidphotos59.utils.ToastMessage;

import java.util.ArrayList;

import android.net.Uri;

public class PhotoActivity extends AppCompatActivity {
    private ListView photoListView;
    private Button addPhotoButton;
    private Button deletePhotoButton;
    private Button addTagButton;
    private Button deleteTagButton;
    private Button slideShowButton;
    private Button backAlbumButton;

    private User activeUser;

    private ArrayList<Photo> photoArrayList;

    private ImageAdapter imageAdapter;
    public final int REQUEST_IMAGE_OPEN = 1;

    private boolean imageSelection = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_view);

        this.photoListView = findViewById(R.id.photoListView);
        this.addPhotoButton = findViewById(R.id.addPhotoButton);
        this.deletePhotoButton = findViewById(R.id.deletePhotoButton);
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

        this.photoArrayList = activeUser.getActiveAlbum().getPhotosInAlbum();
        this.imageAdapter = new ImageAdapter(this, photoArrayList);
        this.photoListView.setAdapter(this.imageAdapter);
        this.addPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("image/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                // Only the system receives the ACTION_OPEN_DOCUMENT, so no need to test.
                startActivityForResult(intent, REQUEST_IMAGE_OPEN);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_OPEN && resultCode == RESULT_OK && data != null) {
            // Handle the selected image URI
            Uri selectedImageUri = data.getData();
            if(selectedImageUri == null){
                ToastMessage.showToast(this, "Cannot add photo Successfully!");
                return;
            }
            Photo newPhoto = new Photo(selectedImageUri.toString());
            if(!this.activeUser.addPhoto(newPhoto)){
                ToastMessage.showToast(this, "Cannot add photo Successfully!");
                return;
            }
            updatePhotoList();//            this.imageAdapter.notifyDataSetChanged();
            ToastMessage.showToast(this, "Added Photo Successfully!");
        }
    }

    private void updatePhotoList(){
        this.photoArrayList.clear();
        this.photoArrayList.addAll(this.activeUser.getActiveAlbum().getPhotosInAlbum());
    }
    private void addPhotoModal(Context c, User u, ArrayList<Album> albumArrayList) {
        // Inflate the custom layout for the dialog
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_layout, null);

        // Find the EditText in the custom layout
        final EditText editTextDialogInput = view.findViewById(R.id.editTextDialogInput);

        // Create the AlertDialog.Builder with the custom layout
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(view)
                .setTitle("Add Photo")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String albumName = editTextDialogInput.getText().toString();

                        boolean addStatus = u.addAlbum(new Album(albumName));
                        if (addStatus) {
                            ToastMessage.showToast(c, "Added album successfully!");
                            albumArrayList.clear();
                            albumArrayList.addAll(u.getAlbumsList());
                        } else {
                            ToastMessage.showToast(c, "Failed to add album successfully!");
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