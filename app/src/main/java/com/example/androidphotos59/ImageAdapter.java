package com.example.androidphotos59;

// ImageAdapter.java
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.androidphotos59.model.Photo;
import java.util.List;

public class ImageAdapter extends ArrayAdapter<Photo> {

    private Context context;
    private List<Photo> Photos;

    public ImageAdapter(@NonNull Context context, @NonNull List<Photo> Photos) {
        super(context, 0, Photos);
        this.context = context;
        this.Photos = Photos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView textView = convertView.findViewById(R.id.textView);

        Photo currentItem = getItem(position);

        if (currentItem != null) {
            Uri u = Uri.parse(currentItem.getFile()); // possible parse error handle.
            context.getContentResolver().takePersistableUriPermission(u, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.getContentResolver().takePersistableUriPermission(u, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            imageView.setImageURI(u);
            textView.setText(currentItem.setTagsToString());
        }

        return convertView;
    }
}
