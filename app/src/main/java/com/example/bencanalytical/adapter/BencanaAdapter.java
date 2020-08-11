package com.example.bencanalytical.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bencanalytical.DetailBencanaActivity;
import com.example.bencanalytical.R;
import com.example.bencanalytical.model.responseBencanaData;

import java.util.List;

import static com.example.bencanalytical.services.ApiClient.BASE_URL;

public class BencanaAdapter extends RecyclerView.Adapter<BencanaAdapter.ViewHolder> {
    private List<responseBencanaData> responseBencanaData;
    private View view;
    private ImageView imgBencana;

    public BencanaAdapter(List<responseBencanaData> responseBencanaData) {
        this.responseBencanaData = responseBencanaData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_bencana, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvNama.setText(responseBencanaData.get(position).getNamaBencana());
        holder.tvTanggal.setText(responseBencanaData.get(position).getUpdatedAt());
        holder.tvDeskripsiLocation.setText(responseBencanaData.get(position).getDeskripsiLokasi());

        Glide.with(view.getContext())
                .load(BASE_URL + "img/bencana/" + responseBencanaData.get(position).getGambar())
                .into(imgBencana);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), DetailBencanaActivity.class);
            intent.putExtra("NAMABENCANA", responseBencanaData.get(position).getNamaBencana());
            intent.putExtra("TANGGAL", responseBencanaData.get(position).getUpdatedAt());
            intent.putExtra("PUBLIKASI", responseBencanaData.get(position).getFullname());
            intent.putExtra("LOKASI", responseBencanaData.get(position).getDeskripsiLokasi());
            intent.putExtra("KEJADIAN", responseBencanaData.get(position).getDeskripsiKejadian());
            intent.putExtra("KOORDINAT", responseBencanaData.get(position).getKoordinat());
            intent.putExtra("GAMBAR", responseBencanaData.get(position).getGambar());

            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return responseBencanaData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvTanggal, tvDeskripsiLocation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.tvNama);
            tvTanggal = itemView.findViewById(R.id.tvTanggal);
            tvDeskripsiLocation = itemView.findViewById(R.id.tvDeskripsiLocation);
            imgBencana = itemView.findViewById(R.id.imgBencana);
        }
    }
}
