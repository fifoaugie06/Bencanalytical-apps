package com.example.bencanalytical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import static com.example.bencanalytical.services.ApiClient.BASE_URL;

public class DetailBencanaActivity extends AppCompatActivity {
    private ImageView imgBencana;
    private TextView tvNamaBencana, tvTanggal, tvPublikasi, tvLokasi, tvKejadian;
    private String koordinat, namabencana;

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

        getSupportActionBar().setTitle("Detail Bencana");

        if (getIntent().getExtras() != null){
            Bundle bundle = getIntent().getExtras();

            tvNamaBencana.setText(bundle.getString("NAMABENCANA"));
            tvTanggal.setText(bundle.getString("TANGGAL"));
            tvPublikasi.setText(bundle.getString("PUBLIKASI"));
            tvLokasi.setText(bundle.getString("LOKASI"));
            tvKejadian.setText(bundle.getString("KEJADIAN"));

            namabencana = bundle.getString("NAMABENCANA");
            koordinat = bundle.getString("KOORDINAT");

            Glide.with(this)
                    .load(BASE_URL + "img/bencana/" + bundle.getString("GAMBAR"))
                    .into(imgBencana);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mybutton) {
            String[] splitKoordinat = koordinat.split(",");

            Intent intent = new Intent(DetailBencanaActivity.this, MapsActivity.class);
            intent.putExtra("NAMABENCANA", namabencana);
            intent.putExtra("LATITUDE", splitKoordinat[0]);
            intent.putExtra("LONGITUDE", splitKoordinat[1]);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}