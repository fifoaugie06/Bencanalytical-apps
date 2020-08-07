package com.example.bencanalytical;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.bencanalytical.model.responseAuth;
import com.example.bencanalytical.model.responseAuthData;
import com.example.bencanalytical.services.ApiClient;
import com.example.bencanalytical.services.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText edtUsername, edtPassword;
    private ImageButton btnLogin;
    private Button btnRegisterIntent;
    private ProgressDialog progress;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
        btnRegisterIntent = findViewById(R.id.btn_Register_Intent);

        btnRegisterIntent.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

            finish();
        });

        btnLogin.setOnClickListener(view -> {
            progress = new ProgressDialog(this);
            progress.setCancelable(false);
            progress.setMessage("Loading ...");
            progress.show();

            apiService = ApiClient.getClient().create(ApiService.class);
            Call<responseAuth> resultAuth = apiService.auth(edtUsername.getText().toString(), edtPassword.getText().toString());
            resultAuth.enqueue(new Callback<responseAuth>() {
                @Override
                public void onResponse(Call<responseAuth> call, Response<responseAuth> response) {
                    if (response.isSuccessful()) {
                        int status = response.body().getStatus();
                        String message = response.body().getMessage();

                        if (status == 1) {
                            String idUser = response.body().getData().getId();

                            Toast.makeText(LoginActivity.this, "Selamat datang", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                            intent.putExtra("USERNAME", edtUsername.getText().toString());
                            intent.putExtra("IDUSER", idUser);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                        progress.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<responseAuth> call, Throwable t) {
                    progress.dismiss();
                    Toast.makeText(LoginActivity.this, "Jaringan Error", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}