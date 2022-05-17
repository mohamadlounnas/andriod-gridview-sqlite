package com.example.products;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class AddProductActivity extends AppCompatActivity {

    Button addProductBtn;
    Button cancelAddProductBtn;
    Button selectPhotoBtn;
    EditText lable;
    EditText description;
    String photo = "";
    ImageView imageView;
    private DBHandler dbHandler;
    public void takepicture(View view){

        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 1888); // 1888 request code
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == 1888 && resultCode == Activity.RESULT_OK){
            Bitmap photoBitmap = (Bitmap) data.getExtras().get("data");
            imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageBitmap(photoBitmap);
            this.photo = Product.BitmapToString(photoBitmap);
            Log.v("@CCCCC",this.photo);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        lable = findViewById(R.id.label);
        description = findViewById(R.id.description);

        imageView = (ImageView) findViewById(R.id.imageView);
        cancelAddProductBtn = findViewById(R.id.cancelAddProductBtn);
        selectPhotoBtn = findViewById(R.id.selectPhotoBtn);
        selectPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                takepicture(v);

                   // Intent intent = new Intent();
                   // intent.setType("image/*");
                    //intent.setAction(Intent.ACTION_GET_CONTENT);
                 //ActivityResultLauncher startForResultFromGallery = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    //@Override
                    //public void onActivityResult(ActivityResult result) {
                    //    if (result.getResultCode() == Activity.RESULT_OK){
                    //        try {
                    //            if (result.getData() != null){
                    //                Uri selectedImageUri = result.getData().getData();
                    //                Bitmap bitmap = BitmapFactory.decodeStream(getBaseContext().getContentResolver().openInputStream(selectedImageUri));
                    //                photo = Product.BitmapToString(bitmap);
                    //                // set bitmap to image view here........
                    //                //binding.menuFragmentCircularProfileImageview.setImageBitmap(bitmap)
                    //            }
                    //        }catch (Exception exception){
                    //            Log.d("TAG",""+exception.getLocalizedMessage());
                    //        }
                    //    }
                    //}
                //});
            }
        });


        addProductBtn = findViewById(R.id.addProductBtn);
        dbHandler = new DBHandler(AddProductActivity.this,null );
        cancelAddProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random generator = new Random();
                String id = String.valueOf (generator.nextInt(96) + 32);
                String labletxt = lable.getText().toString();
                String descriptiontxt = description.getText().toString();
                Log.v("@photo", photo);
                dbHandler.addProduit(new Product(
                        id,
                        labletxt,
                        descriptiontxt,
                        photo
                ));
                onBackPressed();
            }
        });


    }
}