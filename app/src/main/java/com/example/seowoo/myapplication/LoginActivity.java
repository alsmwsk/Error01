package com.example.seowoo.myapplication;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;

public class LoginActivity extends AppCompatActivity {

    ImageView imageView;
    TextView registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerButton = (TextView)findViewById(R.id.registerButton);
        imageView = (ImageView)findViewById(R.id.imageTitle);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registintent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registintent);
            }
        });


        //이미지표시

//        Glide.with(getApplicationContext()).load(R.drawable.mypicture).centerCrop().override(125,125).into(imageView);
//        Glide.with(getApplicationContext()).load(R.drawable.hoseo).centerCrop().override(125,125).into(imageView);
        Glide.with(getApplicationContext()).load("http://blogfiles5.naver.net/data19/2007/4/9/227/result_2007_3_1_13_51_57_890_2-qlcrhktodaud.jpg").override(125,125).centerCrop().into(imageView);

    }

//    public void class LoadImage(final String firstimage){
//        imageView = (ImageView)findViewById(R.id.imageTitle);
//        //이미지 표시
//        Glide.with(this).load("drawable/hoseo.jpg").override(125,125)
//        .into(imageView);
//
//
//    }
}
