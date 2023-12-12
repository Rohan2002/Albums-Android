package com.example.androidphotos59;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView;

import android.os.Bundle;

import android.content.Intent;

import com.example.androidphotos59.model.Album;
import com.example.androidphotos59.model.User;
import com.example.androidphotos59.utils.ToastMessage;

import java.util.ArrayList;

public class AlbumsActivity extends AppCompatActivity {
    private ListView albumListView;
    private Button addAlbumButton;
    private Button deleteAlbumButton;
    private Button renameAlbumButton;
    private Button openAlbumButton;

    private ArrayList<Album> albumArrayList;

    private User activeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_view);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Album List View
        albumListView = findViewById(R.id.albumListView);

        addAlbumButton = findViewById(R.id.addAlbum);
        deleteAlbumButton = findViewById(R.id.deleteAlbum);
        renameAlbumButton = findViewById(R.id.renameAlbum);

    }

    @Override
    protected void onStart() {
        // Get reference to the Album Button
        super.onStart();

        Context currActivityContext = this;

        this.activeUser = new User(this.getDataDir());
        // user's album stuff
        this.albumArrayList = this.activeUser.getAlbumsList();
        ArrayAdapter<Album> adapter = new ArrayAdapter<>(currActivityContext, android.R.layout.simple_list_item_1, this.albumArrayList);
        this.albumListView.setAdapter(adapter);
        this.albumListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item from the adapter
                Album album = adapter.getItem(position);

                activeUser.setActiveAlbum(album); // new album

                Intent intent = new Intent(AlbumsActivity.this, PhotoActivity.class);
                intent.putExtra("activeUser", activeUser);
                startActivity(intent);
            }
        });
        addAlbumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAlbumModal(currActivityContext);
            }
        });

        deleteAlbumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAlbumModal(currActivityContext);
            }
        });

        renameAlbumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                renameAlbumModal(currActivityContext);
            }
        });

    }

    private void addAlbumModal(Context c) {
        // Inflate the custom layout for the dialog
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_layout, null);

        // Find the EditText in the custom layout
        final EditText editTextDialogInput = view.findViewById(R.id.editTextDialogInput);

        // Create the AlertDialog.Builder with the custom layout
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(view)
                .setTitle("Add album")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String albumName = editTextDialogInput.getText().toString();

                        boolean addStatus = activeUser.addAlbum(new Album(albumName));
                        if (addStatus) {
                            ToastMessage.showToast(c, "Added album successfully!");
                            updateAlbumList();
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

    private void deleteAlbumModal(Context c) {
        // Inflate the custom layout for the dialog
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_layout, null);

        // Find the EditText in the custom layout
        final EditText editTextDialogInput = view.findViewById(R.id.editTextDialogInput);

        // Create the AlertDialog.Builder with the custom layout
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(view)
                .setTitle("Delete album")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String albumName = editTextDialogInput.getText().toString();

                        boolean deleteStatus = activeUser.deleteAlbum(new Album(albumName));
                        if (deleteStatus) {
                            ToastMessage.showToast(c, "Deleted album successfully!");
                            updateAlbumList();
                        } else {
                            ToastMessage.showToast(c, "Failed to delete album successfully!");
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

    private void renameAlbumModal(Context c) {
        // Inflate the custom layout for the dialog
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_layout_two, null);

        // Find the EditText in the custom layout
        final EditText editTextDialogInputOne = view.findViewById(R.id.editTextDialogInputOne);
        final EditText editTextDialogInputTwo = view.findViewById(R.id.editTextDialogInputTwo);


        // Create the AlertDialog.Builder with the custom layout
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(view)
                .setTitle("Rename album")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String oldAlbumName = editTextDialogInputOne.getText().toString();
                        String newAlbumName = editTextDialogInputTwo.getText().toString();

                        boolean updateStatus = activeUser.updateUserAlbum(new Album(oldAlbumName), new Album(newAlbumName));
                        if (updateStatus) {
                            ToastMessage.showToast(c, "Updated album name " + oldAlbumName + "with new album name " + newAlbumName + " successfully!");
                            updateAlbumList();
                        } else {
                            ToastMessage.showToast(c, "Failed to update album name successfully!");
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

    private void updateAlbumList() {
        activeUser.getUserFromDisk();
        this.albumArrayList.clear();
        this.albumArrayList.addAll(activeUser.getAlbumsList());
    }
}