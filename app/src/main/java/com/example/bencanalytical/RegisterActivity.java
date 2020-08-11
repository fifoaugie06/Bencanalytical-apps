package com.example.bencanalytical;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.bencanalytical.model.responseGeneral;
import com.example.bencanalytical.services.ApiClient;
import com.example.bencanalytical.services.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private Button btnIntentLogin;
    private ImageButton btnRegister;
    private EditText edtUsername, edtFullname, edtPassword, edtEmail;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnIntentLogin = findViewById(R.id.btn_Login_Intent);
        edtUsername = findViewById(R.id.edt_username);
        edtFullname = findViewById(R.id.edt_fullname);
        edtPassword = findViewById(R.id.edt_password);
        edtEmail = findViewById(R.id.edt_email);
        btnRegister = findViewById(R.id.btn_Register);

        btnIntentLogin.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

            finish();
        });

        btnRegister.setOnClickListener(view -> {
            if (edtUsername.getText().length() != 0 && edtFullname.getText().length() != 0
                    && edtEmail.getText().length() != 0 && edtPassword.getText().length() != 0) {
                tambahkanUser(edtUsername.getText().toString(), edtFullname.getText().toString(),
                        edtPassword.getText().toString(), edtEmail.getText().toString());
            } else {
                Toast.makeText(RegisterActivity.this, "Pastikan semua diisi", Toast.LENGTH_SHORT).show();
            }
        });

        getSupportActionBar().hide();
    }

    private void tambahkanUser(String username, String fullname, String password, String email) {
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Loading ...");
        dialog.show();

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.addUser(username, fullname, password, email).enqueue(new Callback<responseGeneral>() {
            @Override
            public void onResponse(Call<responseGeneral> call, Response<responseGeneral> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Berhasil mendaftar", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<responseGeneral> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Jaringan Error", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }
}