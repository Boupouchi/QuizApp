package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Score extends AppCompatActivity {
    Button bLogout, bTry;
    ProgressBar progressBar;
    TextView tvScore;
    int score;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        tvScore =(TextView) findViewById(R.id.tvScore);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        bLogout=(Button) findViewById(R.id.bLogout);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        bTry=(Button) findViewById(R.id.bTry);
        Intent intent=getIntent();
        score=intent.getIntExtra("activity_score",0) ;
        progressBar.setProgress(100*score/5);
        tvScore.setText(100*score/5+" %");
        //Toast.makeText(getApplicationContext(),activity_score+"",Toast.LENGTH_SHORT).show();
        if (user == null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
        bTry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Score.this,Quiz1.class));
            }
        });

    }
}

