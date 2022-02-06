package com.example.pinstagram.MyProfile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pinstagram.R;
import com.example.pinstagram.RetrofitMain.MainBuilder;
import com.example.pinstagram.RetrofitMain.MainInterface;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PostImage extends AppCompatActivity {
    private Button btnSelect, btnUpload,bolly,ecom,edu,life,sport;

    // view for image view
    private ImageView imageView;

    // Uri indicates, where the image will be picked from
    private Uri filePath;

    // request code
    private final int PICK_IMAGE_REQUEST = 22;

    // instance for firebase storage and StorageReference
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_image);

//        ActionBar actionBar;
//        actionBar = getSupportActionBar();
//        ColorDrawable colorDrawable
//                = new ColorDrawable(
//                Color.parseColor("#0F9D58"));
//        actionBar.setBackgroundDrawable(colorDrawable);

        // initialise views
        btnSelect = findViewById(R.id.btnChoose);
        btnUpload = findViewById(R.id.btnUpload);
        imageView = findViewById(R.id.imgView);
        bolly = findViewById(R.id.bt_bollywood);
        ecom= findViewById(R.id.bt_ecommerce);
        edu = findViewById(R.id.bt_edu);
        life = findViewById(R.id.bt_lifestyle);
        sport = findViewById(R.id.bt_sports);

        // get the Firebase  storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.pinstagram",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // on pressing btnSelect SelectImage() is called
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SelectImage();
            }
        });

        // on pressing btnUpload uploadImage() is called
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                uploadImage();
            }
        });
        bolly.setOnClickListener(view -> {
            editor.putString("category","Bollywood");
            editor.apply();
            Toast.makeText(getApplicationContext(), "Bollywood Selected", Toast.LENGTH_SHORT).show();
        });
        ecom.setOnClickListener(view -> {
            editor.putString("category","E-Commerce");
            editor.apply();
            Toast.makeText(getApplicationContext(), "E-Commerce Selected", Toast.LENGTH_SHORT).show();
        });
        edu.setOnClickListener(view -> {
            editor.putString("category","Education");
            editor.apply();
            Toast.makeText(getApplicationContext(), "Education Selected", Toast.LENGTH_SHORT).show();
        });
        life.setOnClickListener(view -> {
            editor.putString("category","LifeStyle");
            editor.apply();
            Toast.makeText(getApplicationContext(), "LifeStyle Selected", Toast.LENGTH_SHORT).show();
        });
        sport.setOnClickListener(view -> {
            editor.putString("category","Sports");
            editor.apply();
            Toast.makeText(getApplicationContext(), "Sports Selected", Toast.LENGTH_SHORT).show();
        });
    }
    // Select Image method
    private void SelectImage()
    {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image from here..."), PICK_IMAGE_REQUEST);
    }

    // Override onActivityResult method
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    // UploadImage method
    private void uploadImage()
    {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                {
                    // Image uploaded successfully
                    // Dismiss dialog
                    progressDialog.dismiss();
                    Toast.makeText(PostImage.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            SharedPreferences sharedPreferences=getSharedPreferences("com.example.pinstagram", Context.MODE_PRIVATE);
                            String userId=sharedPreferences.getString("userId","B");
                            String category = sharedPreferences.getString("category","LifeStyle");
                            Uri downloadUri = uri;
                            PostDto postDto=new PostDto();
                            postDto.setUrl(downloadUri.toString());
                            postDto.setType(true);
                            postDto.setUserId(userId);
                            postDto.setCategory(category);
                            //Time
                            long unixTime = System.currentTimeMillis() / 1000L;

                            Log.d("xcv", downloadUri + "");
                            Retrofit retrofit= MainBuilder.getInstance();
                            Call<Void> res=retrofit.create(MainInterface.class).addPost(postDto);
                            res.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    Toast.makeText(PostImage.this,"Posted",Toast.LENGTH_SHORT).show();
                                }
                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                }
                            });
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e)
                {// Error, Image not uploaded
                    progressDialog.dismiss();
                    Toast.makeText(PostImage.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        // Progress Listener for loading
                        // percentage on the dialog box
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot)
                        { double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int)progress + "%");
                        }
                    });
        }
    }

}