package com.example.pinstagram.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pinstagram.Feed.Dashboard;
import com.example.pinstagram.R;
import com.example.pinstagram.Register.Register;
import com.example.pinstagram.RetrofitMain.CRMBuilder;
import com.example.pinstagram.RetrofitMain.MainInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Login extends AppCompatActivity {

    private Button newUser,signIn;
    private EditText email, pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        newUser = findViewById(R.id.bt_new_user);
        signIn = findViewById(R.id.bt_sign_in);

        email = findViewById(R.id.et_add_email);
        pwd = findViewById(R.id.et_add_pwd);

        newUser.setOnClickListener(view -> {
            startActivity(new Intent(Login.this, Register.class));
        });

        signIn.setOnClickListener(view -> {
            LoginDto loginDto = new LoginDto();
            loginDto.setUserEmail(email.getText().toString());
            loginDto.setPassword(pwd.getText().toString());
            loginDto.setAppId("2");
            Retrofit retrofit = CRMBuilder.getInstance();
            Call<LoginResDto> loginResDtoCall = retrofit.create(MainInterface.class).login(loginDto);
            loginResDtoCall.enqueue(new Callback<LoginResDto>() {
                @Override
                public void onResponse(Call<LoginResDto> call, Response<LoginResDto> response) {
                    if(response.code()==200||response.code()==201) {
                        SharedPreferences sharedPreferences = getSharedPreferences("com.example.pinstagram", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("userId",loginDto.getUserEmail());
//                    editor.putString("userName","A");
                        editor.putString("userId", loginDto.getUserEmail());
                        editor.apply();
                        if (response.body().getJwt() != null) {
                            editor.putString("jwt", response.body().getJwt());
                            editor.apply();
                            Toast.makeText(getApplicationContext(), "Logged In", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Dashboard.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();

                        }
                    }else
                    {
                        Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<LoginResDto> call, Throwable t) {

                }
            });

        });





    }
}