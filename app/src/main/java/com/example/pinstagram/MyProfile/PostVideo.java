package com.example.pinstagram.MyProfile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
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

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PostVideo extends AppCompatActivity {

    Button uploadv;
    Button bolly,ecom,edu,life,sport;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_video);

        // initialise layout
        uploadv = findViewById(R.id.uploadv);
        bolly = findViewById(R.id.bt_bollywood_v);
        ecom= findViewById(R.id.bt_ecommerce_v);
        edu = findViewById(R.id.bt_edu_v);
        life = findViewById(R.id.bt_lifestyle_v);
        sport = findViewById(R.id.bt_sports_v);

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.pinstagram",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        uploadv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code for showing progressDialog while uploading
                progressDialog = new ProgressDialog(PostVideo.this);
                choosevideo();
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

    // choose a video from phone storage
    private void choosevideo() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 5);
    }

    Uri videouri;

    // startActivityForResult is used to receive the result, which is the selected video.
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            videouri = data.getData();
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            uploadvideo();
        }
    }

    private String getfiletype(Uri videouri) {
        ContentResolver r = getContentResolver();
        // get the file type ,in this case its mp4
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(r.getType(videouri));
    }

    private void uploadvideo() {
        if (videouri != null) {
            // save the selected video in Firebase storage
            final StorageReference reference = FirebaseStorage.getInstance().getReference("Files/" + System.currentTimeMillis() + "." + getfiletype(videouri));
            reference.putFile(videouri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful()) ;
                    // get the link of video
                    String downloadUri = uriTask.getResult().toString();
                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Video");
                    HashMap<String, String> map = new HashMap<>();
                    map.put("videolink", downloadUri);
                    reference1.child("" + System.currentTimeMillis()).setValue(map);
                    // Video uploaded successfully
                    // Dismiss dialog
                    progressDialog.dismiss();
                    Toast.makeText(PostVideo.this, "Video Uploaded!!", Toast.LENGTH_SHORT).show();

                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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
                                    Toast.makeText(PostVideo.this,"Posted",Toast.LENGTH_SHORT).show();
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
                public void onFailure(@NonNull Exception e) {
                    // Error, Image not uploaded
                    progressDialog.dismiss();
                    Toast.makeText(PostVideo.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                // Progress Listener for loading
                // percentage on the dialog box
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    // show the progress bar
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                }
            });
        }
    }
}