package com.example.assignment2mcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FoodDetails extends AppCompatActivity {
    private StorageReference mStorageRef;
    private FirebaseAnalytics mFirebaseAnalytics;
    static Date date;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        trackScreen("Details","FoodDetails");
        SimpleDateFormat formatter= new SimpleDateFormat("h:mm a");
        date = new Date(System.currentTimeMillis());
        TextView name =findViewById(R.id.tv);
        TextView dec = findViewById(R.id.textView4);
        ImageView image = findViewById(R.id.imagev);
        String foodName = "";
        String describe = "";
        Bundle extras = getIntent().getExtras();
        if(extras != null ){
            if(extras.getString("foodName").equals("pizza")) {
                foodName = extras.getString("foodName");
                describe = "Pizza with vegetables, 4$";
                mStorageRef = FirebaseStorage.getInstance().getReference().child("food images/pizza.jpg");
                getImage(mStorageRef,"pizza","jpg");
            }
            if(extras.getString("foodName").equals("Grilled chicken")) {
                foodName = extras.getString("foodName");
                describe = "Chicken on the grill, 6$";
                mStorageRef = FirebaseStorage.getInstance().getReference().child("food images/Grilled chicken.jpg");
                getImage(mStorageRef,"Grilled chicken","jpg");
            }
            if(extras.getString("foodName").equals("Shrimp")) {
                foodName = extras.getString("foodName");
                describe = "grilled shrimp, 7$";
                mStorageRef = FirebaseStorage.getInstance().getReference().child("food images/Shrimp.jpg");
                getImage(mStorageRef,"Shrimp","jpg");
            }
            if(extras.getString("foodName").equals("Shawarma")) {
                foodName = extras.getString("foodName");
                describe = "Fresh veal shawarma, 4$";
                mStorageRef = FirebaseStorage.getInstance().getReference().child("food images/Shawarma.jpg");
                getImage(mStorageRef,"Shawarma","jpg");
            }
            if(extras.getString("foodName").equals("Macaroni")) {
                foodName = extras.getString("foodName");
                describe = "Macaroni Bechamel, 5$";
                mStorageRef = FirebaseStorage.getInstance().getReference().child("food images/Macaroni.jpg");
                getImage(mStorageRef,"Macaroni","jpg");
            }

        }
        name.setText(foodName);
        dec.setText(describe);
    }

    public void getImage(StorageReference s, String prefix, String suffix){
        try {
            final File locationFile = File.createTempFile(prefix,suffix);
            s.getFile(locationFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            //   Toast.makeText(MainActivity.this,"success",Toast.LENGTH_SHORT).show();
                            Bitmap bitmap = BitmapFactory.decodeFile(locationFile.getAbsolutePath());
                            ((ImageView)findViewById(R.id.imagev)).setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //Toast.makeText(MainActivity.this,"failure",Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveTime(Date date, String screenName){
        SimpleDateFormat formatter= new SimpleDateFormat("h:mm a");
        Date date1 = new Date(System.currentTimeMillis());
        int x = (int) ((int)date1.getTime()-date.getTime());
        date = new Date(System.currentTimeMillis());
        String date2 =(x / 3600000) + " hour/s " + (x % 3600000) / 60000 + " minutes "+x / 1000 % 60+" sec";
        Map<String, Object> time = new HashMap<>();
        time.put("Screen Name", screenName);
        time.put("Time", date2);
        db.collection("time")
                .add(time)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " );
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document");
                    }
                });
        Toast.makeText(this,"saved",Toast.LENGTH_SHORT).show();
    }
    public void back(View v){
        saveTime(date,"Food Details");
        Intent intent = new Intent(FoodDetails.this, MainActivity.class);
        startActivity(intent);
    }

    void trackScreen(String screenName ,String screenClass){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName);
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, screenClass);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
    }
}