package com.example.androidphotos59.utils;

import android.widget.Toast;
import android.content.Context;

/**
 * This will trigger a error message in the application if there is to be one.
 * 
 * @author Rohan Deshpande
 * @version 1.0
 */
public class ToastMessage {
    /**
     * 
     * @param e a value from the enum ErrorCode.
     * @param header title of the error message (short).
     * @param content content of the error message (detailed)
     */
    public static void showToast(Context c, String content) {
        Toast.makeText(c, content, Toast.LENGTH_LONG).show();
    }

}
