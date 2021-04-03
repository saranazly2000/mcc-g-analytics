package com.example.assignment2mcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class FoodDetails extends AppCompatActivity {
   // private StorageReference firebaseStorage;
   private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
        //firebaseStorage= FirebaseStorage.getInstance().getReference();
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
}