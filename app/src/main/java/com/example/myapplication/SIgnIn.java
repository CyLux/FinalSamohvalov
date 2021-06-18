package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SIgnIn extends AppCompatActivity {
EditText edEmail,edPassword;
Button btnLogin;
Button ok;
TextView textView,errorMessage;
ConstraintLayout error;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.sign_in);
            edEmail=findViewById(R.id.username);
            edPassword=findViewById(R.id.password);
            btnLogin=findViewById(R.id.button);
            textView=findViewById(R.id.textView);
            error=findViewById(R.id.error);
            ok=findViewById(R.id.button3);
            errorMessage=findViewById(R.id.textView3);
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(edEmail.getText().toString()) || TextUtils.isEmpty(edPassword.getText().toString())) {
                        String Message = "Введите данные пользователя";
                        btnLogin.setVisibility(View.INVISIBLE);
                        error.setVisibility(View.VISIBLE);
                        errorMessage.setText(Message);
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                btnLogin.setVisibility(View.VISIBLE);
                                error.setVisibility(View.INVISIBLE);
                            }
                        });
                    } else {
                        loginUser();
                    }
                }
            });
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(SIgnIn.this,SignUp.class));
                }
            });
        }

                public void loginUser() {
                    LoginRequest loginRequest = new LoginRequest();
                    loginRequest.setEmail(edEmail.getText().toString());
                    loginRequest.setPassword(edPassword.getText().toString());
                    Call<LoginResponse> loginResponseCall = ApiClient.getService().loginUser(loginRequest);
                    loginResponseCall.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if (response.isSuccessful()) {


                                startActivity(new Intent(SIgnIn.this,MainActivity.class));
                                finish();
                            } else {
                                String message = "Данные пользователя не верны";
                                btnLogin.setVisibility(View.INVISIBLE);
                                error.setVisibility(View.VISIBLE);
                                errorMessage.setText(message);
                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        btnLogin.setVisibility(View.VISIBLE);
                                        error.setVisibility(View.INVISIBLE);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            String message = t.getLocalizedMessage();
                            Toast.makeText(SIgnIn.this, message, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }



