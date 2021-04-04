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

public class ClothesDetails extends AppCompatActivity {
    private StorageReference mStorageRef;
    private FirebaseAnalytics mFirebaseAnalytics;
    static Date date;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes_details);
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
            if(extras.getString("clothesName").equals("trouser")) {
                foodName = extras.getString("clothesName");
                describe = "Residential men's trousers, 3$";
                mStorageRef = FirebaseStorage.getInstance().getReference().child("clothes images/trouser.jpg");
                getImage(mStorageRef,"trouser","jpg");
            }
            if(extras.getString("clothesName").equals("jacket")) {
                foodName = extras.getString("clothesName");
                describe = "Black leather jacket for men, 6$";
                mStorageRef = FirebaseStorage.getInstance().getReference().child("clothes images/jacket.jpg");
                getImage(mStorageRef,"jacket","jpg");
            }
            if(extras.getString("clothesName").equals("Shirt")) {
                foodName = extras.getString("clothesName");
                describe = "Men's black t-shirt, 2$";
                mStorageRef = FirebaseStorage.getInstance().getReference().child("clothes images/Shirt.jpg");
                getImage(mStorageRef,"Shirt","jpg");
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
        saveTime(date,"Clothes Details");
        Intent intent = new Intent(ClothesDetails.this, MainActivity.class);
        startActivity(intent);
    }

    void trackScreen(String screenName ,String screenClass){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName);
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, screenClass);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
    }
}