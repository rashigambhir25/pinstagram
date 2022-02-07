package com.example.pinstagram.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pinstagram.Feed.Dashboard;
import com.example.pinstagram.R;
import com.example.pinstagram.Register.Register;
import com.example.pinstagram.RetrofitMain.CRMBuilder;
import com.example.pinstagram.RetrofitMain.MainInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

import kotlin.jvm.internal.Intrinsics;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import kotlin.Metadata;

import android.view.View;

@Metadata(
        mv = {1, 1, 18},
        bv = {1, 0, 3},
        k = 1,
        d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000¨\u0006\t"},
        d2 = {"Lcom/example/pinstagram/Login/Login;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "TAG", "", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"}
)

public class Login extends AppCompatActivity {

    boolean isAllFieldsChecked = false;
    private final String TAG = "LoginActivity";
    private final String Token_Name = "";
    private HashMap _$_findViewCache;
    public String st = "";

    private Button newUser,signIn;
    private EditText email, pwd;
    LoginDto loginDto = new LoginDto();

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
            loginDto.setUserEmail(email.getText().toString());
            loginDto.setPassword(pwd.getText().toString());
            loginDto.setAppId("2");
            Retrofit retrofit = CRMBuilder.getInstance();

            FirebaseInstanceId var10000 = FirebaseInstanceId.getInstance();
            Intrinsics.checkExpressionValueIsNotNull(var10000, "FirebaseInstanceId.getInstance()");
            var10000.getInstanceId().addOnSuccessListener((OnSuccessListener)(new OnSuccessListener() {
                // $FF: synthetic method
                // $FF: bridge method
                public void onSuccess(Object var1) {
                    this.onSuccess((InstanceIdResult)var1);
                }

                public final void onSuccess(InstanceIdResult it) {
//                    TextView var10000 = findViewById(R.id.textfield);
                    Intrinsics.checkExpressionValueIsNotNull(it, "it");
//                    var10000.append((CharSequence)it.getToken());
                    st = it.getToken();
                    loginDto.setDeviceId(st);
                    //ReturnTopic(st);
                    Log.d("tokenid", String.valueOf(it.getToken()));
                }
            }));
            FirebaseMessaging.getInstance().subscribeToTopic("testnotificationjava123").addOnCompleteListener((OnCompleteListener)(new OnCompleteListener() {
                public final void onComplete(@NonNull Task task) {
                    Intrinsics.checkParameterIsNotNull(task, "task");
                    if (task.isSuccessful()) {
                        Log.d(Login.this.TAG, "Global topic subscription successful");
                    } else {
                        String var10000 = Login.this.TAG;
                        StringBuilder var10001 = (new StringBuilder()).append("Global topic subscription failed. Error: ");
                        Exception var10002 = task.getException();
                        Log.e(var10000, var10001.append(var10002 != null ? var10002.getLocalizedMessage() : null).toString());
                    }

                }
            }));
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

    public View _$_findCachedViewById(int var1) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }

        View var2 = (View)this._$_findViewCache.get(var1);
        if (var2 == null) {
            var2 = this.findViewById(var1);
            this._$_findViewCache.put(var1, var2);
        }

        return var2;
    }

    public void _$_clearFindViewByIdCache() {
        if (this._$_findViewCache != null) {
            this._$_findViewCache.clear();
        }

    }



}