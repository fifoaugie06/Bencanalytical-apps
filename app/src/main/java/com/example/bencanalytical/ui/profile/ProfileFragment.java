package com.example.bencanalytical.ui.profile;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.bencanalytical.R;
import com.example.bencanalytical.model.responseProfile;
import com.example.bencanalytical.services.ApiClient;
import com.example.bencanalytical.services.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {
    private String USERNAME;
    private View view;
    private ProgressDialog progress;
    private ApiService apiService;
    private TextView tvUsername, tvFullname, tvPhone, tvEmail, tvAddress;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        USERNAME = getActivity().getIntent().getStringExtra("USERNAME");

        tvUsername = view.findViewById(R.id.tvUsername);
        tvFullname = view.findViewById(R.id.tvFullname);
        tvPhone = view.findViewById(R.id.tvPhone);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvAddress = view.findViewById(R.id.tvAlamat);

        loadData();

        return view;
    }

    private void loadData() {
        progress = new ProgressDialog(view.getContext());
        progress.setCancelable(false);
        progress.setMessage("Loading ...");
        progress.show();

        apiService = ApiClient.getClient().create(ApiService.class);
        Call<responseProfile> responseProfileCall = apiService.getUser(USERNAME);
        responseProfileCall.enqueue(new Callback<responseProfile>() {
            @Override
            public void onResponse(Call<responseProfile> call, Response<responseProfile> response) {
                if (response.isSuccessful()){
                    tvUsername.setText(USERNAME);
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
    }
}