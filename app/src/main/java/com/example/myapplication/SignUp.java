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

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUp extends AppCompatActivity {
    Button btnSignUp;
    EditText edUsername, edPassword, edFirstname, edSecondName,edPassword1,edPassword3,edPassword4;
    ConstraintLayout error;
    TextView textView6,errorMessage;
    Button ok;

    int a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        btnSignUp = findViewById(R.id.button2);
        edUsername = findViewById(R.id.username2);
        edPassword = findViewById(R.id.password2);
        edPassword1=findViewById(R.id.password21);
       edPassword4=findViewById(R.id.password3);
        edFirstname = findViewById(R.id.firstname);
        edSecondName = findViewById(R.id.secondname);
        ok=findViewById(R.id.button4);
        errorMessage=findViewById(R.id.textView5);
        error=findViewById(R.id.error);
        textView6=findViewById(R.id.textView6);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edUsername.getText().toString()) || TextUtils.isEmpty(edFirstname.getText().toString()) || TextUtils.isEmpty(edSecondName.getText().toString()) || TextUtils.isEmpty(edPassword.getText().toString())) {
                    String Message = "Введите данные пользователя";
                    btnSignUp.setVisibility(View.INVISIBLE);
                    error.setVisibility(View.VISIBLE);
                    errorMessage.setText(Message);
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            btnSignUp.setVisibility(View.VISIBLE);
                            error.setVisibility(View.INVISIBLE);
                        }
                    });
                }


                else  {
                    RegisterRequest registerRequest = new RegisterRequest();
                    registerRequest.setEmail(edUsername.getText().toString());
                    registerRequest.setFirstName(edFirstname.getText().toString());
                    registerRequest.setLastName(edSecondName.getText().toString());
                    registerRequest.setPassword(edPassword.getText().toString());

                    SignUpUsers(registerRequest);
                }
            }
        });

        textView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this, SIgnIn.class));


            }
        });
    }

    public void SignUpUsers(RegisterRequest registerRequest) {
        Call<RegisterResponse> registerResponseCall = ApiClient.getService().registerUser(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {

                    startActivity(new Intent(SignUp.this, SIgnIn.class));
                    finish();
                } else {
                    String message = "Что-то пошло не так";
                    btnSignUp.setVisibility(View.INVISIBLE);
                    error.setVisibility(View.VISIBLE);
                    errorMessage.setText(message);
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            btnSignUp.setVisibility(View.VISIBLE);
                            error.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }


            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(SignUp.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void init() {
        btnSignUp = findViewById(R.id.button);
        edUsername = findViewById(R.id.username2);
        edPassword = findViewById(R.id.password2);
        edFirstname = findViewById(R.id.firstname);
        edSecondName = findViewById(R.id.secondname);
    }
}



