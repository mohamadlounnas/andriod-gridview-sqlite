package com.example.products;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Product {
    public String id;
    public String label;
    public String description;
    public String photo;

    public Product(String id, String label,String description, String photo) {
        this.id = id;
        this.label = label;
        this.description = description;
        this.photo = photo;
    }

    public static String BitmapToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
    return  Base64.encodeToString(byteArray, Base64.DEFAULT);}
    public static Bitmap StringToBitmap(String photo)  {
        Log.i(photo,photo);
        byte[] decodedByteArray = android.util.Base64.decode(photo, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0,
                decodedByteArray.length);
    }
}
