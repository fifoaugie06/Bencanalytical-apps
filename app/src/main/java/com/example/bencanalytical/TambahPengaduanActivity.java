package com.example.bencanalytical;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bencanalytical.model.responseGeneral;
import com.example.bencanalytical.services.ApiClient;
import com.example.bencanalytical.services.ApiService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bencanalytical.LoginActivity.IDUSER;
import static com.example.bencanalytical.LoginActivity.SHARED_PREFS;

public class TambahPengaduanActivity extends AppCompatActivity {
    private ImageButton btnCurrentLocation;
    private EditText edtCurrentLocation, edtNamaBencana, edtLokasi, edtKejadian;
    private ImageView icCamera, imgPreviewBencana;
    private String mediaPath;
    private Button tambahBencana;
    private ProgressDialog dialog;
    private ApiService apiService;
    private String iduser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pengaduan);

        btnCurrentLocation = findViewById(R.id.btnCurrentLocation);
        edtCurrentLocation = findViewById(R.id.edtKoordinat);
        icCamera = findViewById(R.id.ic_Camera);
        imgPreviewBencana = findViewById(R.id.imgBencana);
        tambahBencana = findViewById(R.id.tambahBencana);
        edtNamaBencana = findViewById(R.id.edtNamaBencana);
        edtLokasi = findViewById(R.id.edtDeskripsi);
        edtKejadian = findViewById(R.id.edtKejadian);

        SharedPreferences preferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        iduser = preferences.getString(IDUSER, "");

        getSupportActionBar().setTitle("Tambahkan Pengaduan");

        btnCurrentLocation.setOnClickListener(view -> {
            String[] mapsPermissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            if (EasyPermissions.hasPermissions(this, mapsPermissions)) {
                getLocation();
            } else {
                EasyPermissions.requestPermissions(this, "Access for maps",
                        101, mapsPermissions);
            }
        });

        icCamera.setOnClickListener(view -> {
            String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

            if (EasyPermissions.hasPermissions(this, galleryPermissions)) {
                Intent galeryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galeryIntent, 0);
            } else {
                EasyPermissions.requestPermissions(this, "Access for storage",
                        101, galleryPermissions);
            }
        });

        tambahBencana.setOnClickListener(view -> {
            if (mediaPath != null && edtNamaBencana.getText().length() != 0 && edtCurrentLocation.getText().length() != 0
                    && edtLokasi.getText().length() != 0 && edtKejadian.getText().length() != 0) {
                tambahkanData(edtNamaBencana.getText().toString(), edtCurrentLocation.getText().toString(),
                        edtLokasi.getText().toString(), edtKejadian.getText().toString());
            } else {
                Toast.makeText(TambahPengaduanActivity.this, "Isi Semua Data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getLocation() {
        FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mFusedLocation.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {

                edtCurrentLocation.setText(location.getLatitude() + ", " + location.getLongitude());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {

                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);

                imgPreviewBencana.setImageBitmap(BitmapFactory.decodeFile(mediaPath));

                cursor.close();
            } else {
                Toast.makeText(this, "You haven't picked Image/Video", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

    private void tambahkanData(String bencana, String Koordinat, String Lokasi, String Kejadian) {
//        dialog = new ProgressDialog(this);
//        dialog.setCancelable(false);
//        dialog.setMessage("Loading ...");
//        dialog.show();

        File file = new File(mediaPath);

        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("gambar", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        RequestBody idUser = RequestBody.create(MediaType.parse("text/plain"), iduser);
        RequestBody namaBencana = RequestBody.create(MediaType.parse("text/plain"), bencana);
        RequestBody koordinat = RequestBody.create(MediaType.parse("text/plain"), Koordinat);
        RequestBody lokasi = RequestBody.create(MediaType.parse("text/plain"), Lokasi);
        RequestBody kejadian = RequestBody.create(MediaType.parse("text/plain"), Kejadian);

        Log.i("QWE", lokasi.toString());

//        apiService = ApiClient.getClient().create(ApiService.class);
//        apiService.addPengaduan(fileToUpload, filename, idUser, namaBencana, koordinat, lokasi, kejadian).enqueue(new Callback<responseGeneral>() {
//            @Override
//            public void onResponse(Call<responseGeneral> call, Response<responseGeneral> response) {
//                if (response.isSuccessful()) {
//                    Toast.makeText(TambahPengaduanActivity.this, "Pengaduan Baru menunggu konfirmasi", Toast.LENGTH_SHORT).show();
//
//                    Intent intent = new Intent(TambahPengaduanActivity.this, DashboardActivity.class);
//                    intent.putExtra("IDUSER", IDUSER);
//                    startActivity(intent);
//                    finish();
//                } else if (response.code() == 400) {
//                    Toast.makeText(TambahPengaduanActivity.this, "Gambar melebihi 2Mb", Toast.LENGTH_SHORT).show();
//                }
//
//
//                dialog.dismiss();
//            }
//
//            @Override
//            public void onFailure(Call<responseGeneral> call, Throwable t) {
//                Toast.makeText(TambahPengaduanActivity.this, "Jaringan Error", Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//            }
//        });
    }
}