package com.example.seowoo.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.io.File;

public class LoginActivity extends AppCompatActivity {

    private AlertDialog dialog;
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

        final EditText idText = (EditText)findViewById(R.id.idText);
        final EditText passwordText = (EditText)findViewById(R.id.passwordText);
        final Button loginButton = (Button)findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userID = idText.getText().toString();
                String userPassword = passwordText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //해당 결과를 받아올수 있게 JSONObject생성
                            JSONObject jsonResponse = new JSONObject(response);
                            // boolean 타입의 success 변수에 success라는 이름으로 값이 오는지 확인
                            boolean success = jsonResponse.getBoolean("success");
                            if (success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                dialog = builder.setMessage("로그인에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                            @Override
                                            public void onDismiss(DialogInterface dialogInterface) {
                                                //해석이 잘 안된다.
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                intent.putExtra("userID",userID);
                                                LoginActivity.this.startActivity(intent);
                                            }
                                        })
                                        .create();
                                dialog.show();


                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                dialog = builder.setMessage("아이디와 비밀번호를 확인해 주세요.")
                                        .setNegativeButton("확인", null)
                                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                            @Override
                                            public void onDismiss(DialogInterface dialogInterface) {

                                            }
                                        })
                                        .create();
                                dialog.show();
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
                // queue.add(loginRequest)의 결과로 나온 값이 JSONobject를 통해서 다뤄진다.

            }
        });


        //이미지표시

//        Glide.with(getApplicationContext()).load(R.drawable.mypicture).centerCrop().override(125,125).into(imageView);
//        Glide.with(getApplicationContext()).load(R.drawable.hoseo).centerCrop().override(125,125).into(imageView);
        Glide.with(getApplicationContext()).load("http://cafefiles.naver.net/20140407_160/babssap_1396846300096LX1pP_JPEG/%C8%A3%BC%AD%B4%EB_%C7%D7%B0%F8%BC%AD%BA%F1%BD%BA%C7%D0%B0%FA_%BC%F6%BD%C3%C1%D8%BA%F1%A4%B1%A4%A4%A4%B7.jpg").override(125,125).centerCrop().into(imageView);

    }

    //다이얼로그가 켜져 있을 때에는 함부로 종료시키지 않도록 한다.
    @Override
    protected void onStop(){
        super.onStop();
        if (dialog != null)
        {
            dialog.dismiss();
            dialog = null;
        }
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
