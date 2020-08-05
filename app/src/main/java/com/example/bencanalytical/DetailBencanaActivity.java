package com.example.bencanalytical;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailBencanaActivity extends AppCompatActivity {
    private ImageView imgBencana;
    private TextView tvNamaBencana, tvTanggal, tvPublikasi, tvLokasi, tvKejadian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bencana);

        imgBencana = findViewById(R.id.imgBencana);
        tvNamaBencana = findViewById(R.id.tvNamaBencana);
        tvTanggal = findViewById(R.id.tvTanggalPublikasi);
        tvPublikasi = findViewById(R.id.tvDipublikasikan);
        tvLokasi = findViewById(R.id.tvDeskripsiLokasi);
        tvKejadian = findViewById(R.id.tvDeskripsiKejadian);

        if (getIntent().getExtras() != null){
            Bundle bundle = getIntent().getExtras();

            tvNamaBencana.setText(bundle.getString("NAMABENCANA"));
        }
    }
}