package com.example.bencanalytical.ui.cuaca;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bencanalytical.R;
import com.example.bencanalytical.model.weather.Main;
import com.example.bencanalytical.model.weather.Weather;
import com.example.bencanalytical.model.weather.resultWeather;
import com.example.bencanalytical.services.ApiClient;
import com.example.bencanalytical.services.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bencanalytical.services.ApiClient.BASE_URL;
import static com.example.bencanalytical.services.ApiClient.BASE_URL_WEATHER_KEY;

public class CuacaFragment extends Fragment {
    private View view;
    private TextView tvKota, tvWeather, tvSuhu, tvAngin, tvFeel, tvLembab, tvTekanan;
    private ApiService apiService;
    private ImageView imgWeather;
    private List<Weather> weathers;
    private ProgressDialog progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cuaca, container, false);

        tvKota = view.findViewById(R.id.tvKota);
        tvWeather = view.findViewById(R.id.tvHariIni);
        tvSuhu = view.findViewById(R.id.tvSuhu);
        tvAngin = view.findViewById(R.id.tvAngin);
        tvFeel = view.findViewById(R.id.tvFeel);
        tvLembab = view.findViewById(R.id.tvKelembaban);
        tvTekanan = view.findViewById(R.id.tvTekanan);
        imgWeather = view.findViewById(R.id.imgAvatar);

        progress = new ProgressDialog(view.getContext());
        progress.setCancelable(false);
        progress.setMessage("Loading ...");
        progress.show();

        apiService = ApiClient.getClientWeather().create(ApiService.class);
        apiService.getWeather(BASE_URL_WEATHER_KEY).enqueue(new Callback<resultWeather>() {
            @Override
            public void onResponse(Call<resultWeather> call, Response<resultWeather> response) {
                if (response.isSuccessful()) {

                    tvKota.setText(response.body().getName());

                    Glide.with(view.getContext())
                            .load("http://openweathermap.org/img/wn/" + response.body().getWeather().get(0).getIcon() + "@2x.png")
                            .into(imgWeather);

                    tvWeather.setText("Hari ini  " + '\u25CF' + "  " +
                            response.body().getWeather().get(0).getDescription().substring(0, 1).toUpperCase() +
                            response.body().getWeather().get(0).getDescription().substring(1).toLowerCase());

                    tvSuhu.setText(response.body().getMain().getTempMax() + "\u00B0" + "/"
                            + response.body().getMain().getTempMin() + "\u00B0");

                    tvAngin.setText(response.body().getWind().getSpeed() + "m/sec");

                    tvFeel.setText(response.body().getMain().getFeelsLike() + "\u00B0");

                    tvLembab.setText(response.body().getMain().getHumidity() + "Hum");

                    tvTekanan.setText(response.body().getMain().getPressure() + "hPa");
                }

                progress.dismiss();
            }

            @Override
            public void onFailure(Call<resultWeather> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(view.getContext(), "Server Error", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}