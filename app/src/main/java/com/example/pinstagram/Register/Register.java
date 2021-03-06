package com.example.pinstagram.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pinstagram.Login.Login;
import com.example.pinstagram.R;
import com.example.pinstagram.RetrofitMain.CRMBuilder;
import com.example.pinstagram.RetrofitMain.MainBuilder;
import com.example.pinstagram.RetrofitMain.MainInterface;
import com.example.pinstagram.SearchList.AddSearchDto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Register extends AppCompatActivity {
    Button signUp;
    private EditText etname, etmail,etphone ,etaddress, etpassword , etconfirmpw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etname = findViewById(R.id.enter_name);
        etmail = findViewById(R.id.enter_email);
        etphone = findViewById(R.id.enter_phone);
        etpassword = findViewById(R.id.enter_pwd);
        signUp = findViewById(R.id.bt_sign_up);

        signUp.setOnClickListener(view -> {
            RegisterDto registerDto=new RegisterDto();
            registerDto.setName(etname.getText().toString());
            registerDto.setUserEmail(etmail.getText().toString().trim());
            registerDto.setAppId("2");
            registerDto.setPassword(etpassword.getText().toString());
            registerDto.setContact(etphone.getText().toString());
            AddSearchDto addSearchDto=new AddSearchDto();
            addSearchDto.setId(registerDto.getUserEmail());
            addSearchDto.setName(registerDto.getName());;
            addSearchDto.setType(false);
            Retrofit retrofit = CRMBuilder.getInstance();
            Retrofit retrofit1 = MainBuilder.getInstance();
            //addTOSearch
            Call<Void> addSearch=retrofit1.create(MainInterface.class).addToSearch(addSearchDto);
            addSearch.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();

                }
            });


            //register
            Call<StatusDto> statusDtoCall=retrofit.create(MainInterface.class).register(registerDto);
            statusDtoCall.enqueue(new Callback<StatusDto>() {
                @Override
                public void onResponse(Call<StatusDto> call, Response<StatusDto> response) {
                    Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }

                @Override
                public void onFailure(Call<StatusDto> call, Throwable t) {

                }
            });
            startActivity(new Intent(this, Login.class));
        });




    }
}