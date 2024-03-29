package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class Quiz2 extends AppCompatActivity {
    FirebaseFirestore fStore;
    TextView question;
    FirebaseStorage storage= FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://quizapp-registration.appspot.com").child("q2.jpg");
    ImageView picture;
    RadioGroup rg;
    RadioButton rb,rb1,rb2;
    Button bNext;
    int score;
    String RepCorrect="À droite";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz2);
        fStore= FirebaseFirestore.getInstance();
        question= findViewById(R.id.question2);
        rb1= findViewById(R.id.rb1);
        rb2= findViewById(R.id.rb2);
        DocumentReference documentReference = fStore.collection("questions").document("Q2");
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                question.setText(value.getString("question2"));
                rb1.setText(value.getString("rb1"));
                rb2.setText(value.getString("rb2"));
            }
        });
        picture = (ImageView) findViewById(R.id.picture2);
        final File file;
        try {
            file = File.createTempFile("picture2","JPG");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        storageRef.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                picture.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Quiz2.this, "Image failed to load from Storage", Toast.LENGTH_SHORT).show();
            }
        });

        rg=(RadioGroup) findViewById(R.id.rg);
        bNext=(Button) findViewById(R.id.bNext);
        Intent intent=getIntent();
        score=intent.getIntExtra("activity_score",0) ;
        //Toast.makeText(getApplicationContext(),activity_score+"",Toast.LENGTH_SHORT).show();
        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb=(RadioButton) findViewById(rg.getCheckedRadioButtonId());
                if(rg.getCheckedRadioButtonId()==-1){
                    Toast.makeText(getApplicationContext(),"Merci de choisir une réponse S.V.P !",Toast.LENGTH_SHORT).show();
                }
                else {
                    //Toast.makeText(getApplicationContext(),rb.getText().toString(),Toast.LENGTH_SHORT).show();
                    if(rb.getText().toString().equals(RepCorrect)){
                        score+=1;
                        //Toast.makeText(getApplicationContext(),activity_score+"",Toast.LENGTH_SHORT).show();
                    }
                    Intent intent=new Intent(Quiz2.this,Quiz3.class);
                    intent.putExtra("activity_score",score);
                    startActivity(intent);
                    //overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                    overridePendingTransition(R.anim.exit,R.anim.entry);
                    finish();
                }

            }
        });

    }
}

