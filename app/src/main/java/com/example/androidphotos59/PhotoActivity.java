package com.example.androidphotos59;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ListView;

import com.example.androidphotos59.model.Album;
import com.example.androidphotos59.model.Photo;
import com.example.androidphotos59.model.Tag;
import com.example.androidphotos59.model.User;
import com.example.androidphotos59.utils.ToastMessage;

import java.util.ArrayList;

import android.net.Uri;

public class PhotoActivity extends AppCompatActivity {
    private ListView photoListView;
    private Button addPhotoButton;
    private Button slideShowButton;
    private Button backAlbumButton;

    private User activeUser;
    private ArrayList<Photo> photoArrayList;
    public final int REQUEST_IMAGE_OPEN = 1;

    private ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_view);

        this.photoListView = findViewById(R.id.photoListView);
        this.addPhotoButton = findViewById(R.id.addPhoto);

//        this.movePhotoButton = findViewById(R.id.movePhotoButton);
        this.slideShowButton = findViewById(R.id.slideShow);
        this.backAlbumButton = findViewById(R.id.backAlbum);

        Intent intent = getIntent();
        this.activeUser = (User) intent.getSerializableExtra("activeUser");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Context photoActivityContext = this;
        this.photoArrayList = activeUser.getActiveAlbum().getPhotosInAlbum();
        this.imageAdapter = new ImageAdapter(this, photoArrayList);
        this.photoListView.setAdapter(imageAdapter);
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

        this.photoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item from the adapter
                Photo photo = imageAdapter.getItem(position);

                photoSelectModal(photoActivityContext, photo);
            }
        });

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
                Intent intent = new Intent(PhotoActivity.this, AlbumsActivity.class);
                intent.putExtra("activeUser", activeUser);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_OPEN && resultCode == RESULT_OK && data != null) {
            // Handle the selected image URI
            Uri selectedImageUri = data.getData();
            if (selectedImageUri == null) {
                ToastMessage.showToast(this, "Cannot add photo Successfully!");
                return;
            }
//            int takeFlags = data.getFlags()
//                    & (Intent.FLAG_GRANT_READ_URI_PERMISSION
//                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//
//            try {
//                this.getContentResolver().takePersistableUriPermission(selectedImageUri, takeFlags);
//            }
//            catch (SecurityException e){
//                System.out.println("Blackbox security issue");
//                e.printStackTrace();
//                return;
//            }

            Photo newPhoto = new Photo(selectedImageUri.toString());
            if (!this.activeUser.addPhoto(newPhoto)) {
                ToastMessage.showToast(this, "Cannot add photo Successfully!");
                return;
            }
            updatePhotoList();//            this.imageAdapter.notifyDataSetChanged();
            ToastMessage.showToast(this, "Added Photo Successfully!");
        }
    }

    private void updatePhotoList() {
        activeUser.getUserFromDisk();
        this.photoArrayList.clear();
        this.photoArrayList.addAll(this.activeUser.getActiveAlbum().getPhotosInAlbum());
        this.imageAdapter.notifyDataSetChanged();
    }

    private boolean checkValidTag(String s) {
        if (s.length() < 3) {
            return false;
        }
        String[] spl = s.split("=");
        if (spl.length != 2) {
            return false;
        }
        return true;
    }

    private void photoSelectModal(Context c, Photo photo) {
        // Inflate the custom layout for the dialog
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.photo_focus, null);

        // Find the EditText in the custom layout
        final EditText editTagInput = view.findViewById(R.id.editTagName);
        final Button addTagButton = view.findViewById(R.id.addTag);
        final Button deleteTagButton = view.findViewById(R.id.deleteTag);
        final Button deletePhotoButton = view.findViewById(R.id.deletePhoto);

        // Create the AlertDialog.Builder with the custom layout
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(view)
                .setTitle("Photo functions")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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

        addTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = editTagInput.getText().toString();
                if (!checkValidTag(tag)) {
                    ToastMessage.showToast(c, "Tag syntax invalid. Cannot add tag.");
                    return;
                }
                Tag validTag = new Tag(tag);
                if (!photo.addTag(validTag)) {
                    ToastMessage.showToast(c, "Cannot add tag!");
                    return;
                }

                activeUser.updatePhoto(photo);
                updatePhotoList();

                dialog.dismiss();
            }
        });

        deleteTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = editTagInput.getText().toString();
                if (!checkValidTag(tag)) {
                    ToastMessage.showToast(c, "Tag syntax invalid. Cannot remove tag.");
                    return;
                }
                Tag validTag = new Tag(tag);
                if (!photo.removeTag(validTag)) {
                    ToastMessage.showToast(c, "Cannot remove tag!");
                    return;
                }

                activeUser.updatePhoto(photo);
                updatePhotoList();

                dialog.dismiss();
            }
        });
        deletePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!activeUser.deletePhoto(photo)) {
                    ToastMessage.showToast(c, "Cannot delete photo Successfully!");
                    return;
                }
                updatePhotoList();
                ToastMessage.showToast(c, "Deleted Photo Successfully!");
                dialog.dismiss();
            }
        });
    }


}