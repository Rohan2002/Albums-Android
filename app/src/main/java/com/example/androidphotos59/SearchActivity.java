package com.example.androidphotos59;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.androidphotos59.model.Album;
import com.example.androidphotos59.model.Photo;
import com.example.androidphotos59.model.Tag;
import com.example.androidphotos59.model.User;
import com.example.androidphotos59.utils.ToastMessage;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private EditText searchField;
    private Button searchButton;

    private Button backToAlbum;

    private ListView searchOutput;

    private ImageAdapter imageAdapter;

    private User activeUser;

    private AutoCompleteTextView firstSearchBar;

    private AutoCompleteTextView secondSearchBar;

    private ArrayList<Album> allAlbums;

    private ArrayList<Tag> allTags;

    private Spinner searchRule;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        Intent intent = getIntent();
        this.activeUser = (User) intent.getSerializableExtra("activeUser");
        this.allAlbums = this.activeUser.getAlbumsList() == null ? new ArrayList<>() : this.activeUser.getAlbumsList();
        this.allTags = allTags();

        firstSearchBar = findViewById(R.id.firstSearchByTagsTextBox);
        secondSearchBar = findViewById(R.id.secondSearchByTagsTextBox);

        searchRule = findViewById(R.id.AndOrListen);

        searchButton = findViewById(R.id.searchTagsButton);
        searchOutput = findViewById(R.id.searchResultsList);

        backToAlbum = findViewById(R.id.backToAlbum);
    }

    private ArrayList<Tag> allTags() {
        ArrayList<Tag> allTags = new ArrayList<Tag>();
        for (Album a : this.allAlbums) {
            for (Photo p : a.getPhotosInAlbum()) {
                for (Tag t : p.getAllTags()) {
                    allTags.add(t);
                }
            }
        }
        return allTags;
    }

    private List<String> fetchTagSuggestion(String searchQuery) {
        // tagType = location or name.
        List<String> suggestions = new ArrayList<>();

        for (Tag item : allTags) {
            if (item.getTagData().toLowerCase().contains(searchQuery.toLowerCase())) {
                suggestions.add(item.getTagData());
            }
        }
        return suggestions;
    }

    @Override
    protected void onStart() {
        super.onStart();
        final String[] selectedRule = {""};
        Context searchContext = this;

        String[] items = {"", "AND", "OR"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(searchContext, android.R.layout.simple_spinner_item, items);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        searchRule.setAdapter(adapter);
        searchRule.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Get the selected item from the spinner
                String selectedItem = (String) parentView.getItemAtPosition(position);
                selectedRule[0] = selectedItem;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle case where nothing is selected
            }
        });
        firstSearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
                // This method is called to notify you that the characters within `start` and `start + before` are about to be replaced with new text with a length of `count`.
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // This method is called to notify you that somewhere within `start` and `start + before`, `count` characters have been replaced with new text.
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // This method is called when the text in the `AutoCompleteTextView` has been changed.
                String searchQuery = editable.toString();
                List<String> suggestions = fetchTagSuggestion(searchQuery);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(searchContext, android.R.layout.simple_dropdown_item_1line, suggestions);
                firstSearchBar.setAdapter(adapter);
            }
        });

        secondSearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
                // This method is called to notify you that the characters within `start` and `start + before` are about to be replaced with new text with a length of `count`.
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // This method is called to notify you that somewhere within `start` and `start + before`, `count` characters have been replaced with new text.
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // This method is called when the text in the `AutoCompleteTextView` has been changed.
                String searchQuery = editable.toString();
                List<String> suggestions = fetchTagSuggestion(searchQuery);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(searchContext, android.R.layout.simple_dropdown_item_1line, suggestions);
                secondSearchBar.setAdapter(adapter);
            }
        });
//
//        this.imageAdapter = new ImageAdapter(this, photoArrayList);
//        this.photoListView.setAdapter(imageAdapter);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstQuery = firstSearchBar.getText().toString();
                String secondQuery = secondSearchBar.getText().toString();
                String rule = selectedRule[0];
                if (rule.equals("") && secondQuery.length() > 0) {
                    ToastMessage.showToast(searchContext, "No rule specified. Cannot use second field. Only use first field");
                }

                ArrayList<Photo> searchedPhotos = new ArrayList<>();
                boolean firstQueryMatch = false;
                boolean secondQueryMatch = false;
                for (Album a : activeUser.getAlbums()) {
                    for (Photo p : a.getPhotosInAlbum()) {
                        for (Tag t : p.getAllTags()) {
                            String value = t.getTagData();
                            if (value.equals(firstQuery)) {
                                firstQueryMatch = true;
                            }
                            if (value.equals(secondQuery)) {
                                secondQueryMatch = true;
                            }
                        }
                        if (rule.equals("AND") && firstQueryMatch && secondQueryMatch) {
                            searchedPhotos.add(p);
                        } else if (rule.equals("OR") && (firstQueryMatch || secondQueryMatch)) {
                            searchedPhotos.add(p);
                        } else if (rule.equals("") && firstQueryMatch) {
                            searchedPhotos.add(p);
                        }
                        firstQueryMatch = false;
                        secondQueryMatch = false;
                    }
                }
                imageAdapter = new ImageAdapter(searchContext, searchedPhotos);
                searchOutput.setAdapter(imageAdapter);
            }
        });

        backToAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, AlbumsActivity.class);
                intent.putExtra("activeUser", activeUser);
                startActivity(intent);
            }
        });
    }
}
