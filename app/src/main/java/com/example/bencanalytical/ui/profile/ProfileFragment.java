package com.example.bencanalytical.ui.profile;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.bencanalytical.LoginActivity;
import com.example.bencanalytical.R;
import com.example.bencanalytical.model.responseGeneral;
import com.example.bencanalytical.model.responseProfile;
import com.example.bencanalytical.services.ApiClient;
import com.example.bencanalytical.services.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bencanalytical.LoginActivity.IDUSER;
import static com.example.bencanalytical.LoginActivity.ISLOGIN;
import static com.example.bencanalytical.LoginActivity.SHARED_PREFS;
import static com.example.bencanalytical.LoginActivity.USERNAME;

public class ProfileFragment extends Fragment {
    private String username;
    private View view;
    private ProgressDialog progress;
    private ApiService apiService;
    private TextView tvUsername, tvFullname, tvPhone, tvEmail, tvAddress;
    private Button btnEditProfile, btnLogout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        SharedPreferences preferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        username = preferences.getString(USERNAME, "");

        tvUsername = view.findViewById(R.id.tvUsername);
        tvFullname = view.findViewById(R.id.tvFullname);
        tvPhone = view.findViewById(R.id.tvPhone);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvAddress = view.findViewById(R.id.tvAlamat);
        btnEditProfile = view.findViewById(R.id.btnEdtProfile);
        btnLogout = view.findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(view -> {
            preferences.edit().remove(IDUSER).apply();
            preferences.edit().remove(USERNAME).apply();
            preferences.edit().remove(ISLOGIN).apply();

            startActivity(new Intent(view.getContext(), LoginActivity.class));
            getActivity().finish();
        });

        loadData();

        return view;
    }

    private void loadData() {
        progress = new ProgressDialog(view.getContext());
        progress.setCancelable(false);
        progress.setMessage("Loading ...");
        progress.show();

        apiService = ApiClient.getClient().create(ApiService.class);
        Call<responseProfile> responseProfileCall = apiService.getUser(username);
        responseProfileCall.enqueue(new Callback<responseProfile>() {
            @Override
            public void onResponse(Call<responseProfile> call, Response<responseProfile> response) {
                if (response.isSuccessful()) {
                    tvUsername.setText(username);
                    tvFullname.setText(response.body().getData().getFullname());
                    tvPhone.setText(response.body().getData().getNoTelp());
                    tvEmail.setText(response.body().getData().getEmail());
                    tvAddress.setText(response.body().getData().getAlamat());
                }
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<responseProfile> call, Throwable t) {
                progress.dismiss();
            }
        });

        btnEditProfile.setOnClickListener(view -> {
            Button btnUpdate;
            EditText edtUsername, edtFullname, edtPhone, edtEmail, edtAddress;
            Dialog dialog;
            dialog = new Dialog(view.getContext());
            dialog.setContentView(R.layout.dialog_update_profile);

            edtUsername = dialog.findViewById(R.id.edt_username);
            edtFullname = dialog.findViewById(R.id.edt_fullname);
            edtPhone = dialog.findViewById(R.id.edt_phone);
            edtEmail = dialog.findViewById(R.id.edt_email);
            edtAddress = dialog.findViewById(R.id.edt_alamat);
            btnUpdate = dialog.findViewById(R.id.btnUpdate);

            edtUsername.setText(tvUsername.getText());
            edtFullname.setText(tvFullname.getText());
            edtPhone.setText(tvPhone.getText());
            edtEmail.setText(tvEmail.getText());
            edtAddress.setText(tvAddress.getText());

            dialog.show();

            // set size dialog
            Window window = dialog.getWindow();
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            btnUpdate.setOnClickListener(view1 -> {
                if (edtFullname.getText().length() != 0 && edtEmail.getText().length() != 0) {
                    updateUser(edtUsername.getText().toString(), edtFullname.getText().toString(), edtPhone.getText().toString(),
                            edtEmail.getText().toString(), edtAddress.getText().toString());
                    dialog.dismiss();
                } else {
                    Toast.makeText(view.getContext(), "Pastikan Semua Terisi", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void updateUser(String username, String fullname, String phone, String email, String address) {
        progress = new ProgressDialog(view.getContext());
        progress.setCancelable(false);
        progress.setMessage("Loading ...");
        progress.show();

        apiService = ApiClient.getClient().create(ApiService.class);
        Call<responseGeneral> responseGeneralCall = apiService.updateUser(username, fullname, phone, email, address);
        responseGeneralCall.enqueue(new Callback<responseGeneral>() {
            @Override
            public void onResponse(Call<responseGeneral> call, Response<responseGeneral> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(view.getContext(), "Berhasil di update", Toast.LENGTH_SHORT).show();

                    tvFullname.setText(fullname);
                    tvPhone.setText(phone);
                    tvEmail.setText(email);
                    tvAddress.setText(address);
                }
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<responseGeneral> call, Throwable t) {
                Toast.makeText(view.getContext(), "Jaringan Error", Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        });
    }
}