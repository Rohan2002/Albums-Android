package com.example.androidphotos59;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import android.widget.Button;
import android.content.DialogInterface;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.content.Intent;

import com.example.androidphotos59.model.Album;
import com.example.androidphotos59.model.User;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CreateAlbumActivity extends AppCompatActivity {
    private Context createAlbumContext;
    private Button saveAlbumButton;
    private User activeUser;

    private ArrayList<Album> albumArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_view);

        this.saveAlbumButton = findViewById(R.id.savedAlbumButton);

        Intent intent = getIntent();
        this.activeUser = (User) intent.getSerializableExtra("activeUser");

        this.albumArrayList = this.activeUser.getAlbumsList() == null ? new ArrayList<Album>() : this.activeUser.getAlbumsList();

        this.createAlbumContext = this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.saveAlbumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the modal (dialog)
                // openAlbumSaveModal();
            }
        });
    }

//    private void openAlbumSaveModal() {
//        // Inflate the custom layout for the dialog
//        LayoutInflater inflater = LayoutInflater.from(this);
//        View view = inflater.inflate(R.layout.dialog_layout, null);
//
//        // Find the EditText in the custom layout
//        final EditText editTextDialogInput = view.findViewById(R.id.editTextDialogInput);
//
//        // Create the AlertDialog.Builder with the custom layout
//        AlertDialog.Builder builder = new AlertDialog.Builder(createAlbumContext);
//
//        ArrayList<Album> currActiveAlbum = this.albumArrayList;
//        builder.setView(view)
//                .setTitle("Save album")
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String albumName = editTextDialogInput.getText().toString();
//
//                        // save album functionality.
//                        // TODO: Update photos in activeAlbum
//                        activeUser.updateUserAlbum(new Album())
//                        Album activeAlbum = activeUser.getActiveAlbum();
//                        activeAlbum.setAlbumName(albumName);
//
//
//                        User.saveUserToDisk(createAlbumContext, user);
//                        dialog.dismiss();
//                    }
//                })
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//
//        // Create and show the AlertDialog
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }
}