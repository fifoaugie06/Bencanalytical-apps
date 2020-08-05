package com.example.bencanalytical.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bencanalytical.R;
import com.example.bencanalytical.model.responsePengaduanData;

import java.util.List;

import static com.example.bencanalytical.services.ApiClient.BASE_URL;

public class PengaduanAdapter extends RecyclerView.Adapter<PengaduanAdapter.ViewHolder> {
    private List<responsePengaduanData> responsePengaduanData;
    private View view;
    private ImageView imgBencana;

    public PengaduanAdapter(List<responsePengaduanData> responsePengaduanData) {
        this.responsePengaduanData = responsePengaduanData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_pengaduan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvNama.setText(responsePengaduanData.get(position).getNamaBencana());
        holder.tvTanggal.setText(responsePengaduanData.get(position).getUpdatedAt());
        holder.tvDeskripsiLocation.setText(responsePengaduanData.get(position).getDeskripsiLokasi());

        String status = responsePengaduanData.get(position).getStatusBencana();
        if (status.equals("Pending")){
            holder.tvStatus.setText(status);
            holder.tvStatus.setTextColor(0xFFf39c12);
        }else if (status.equals("Terkonfirmasi") || status.equals("Selesai")){
            holder.tvStatus.setText(status);
            holder.tvStatus.setTextColor(0xFF2ecc71);
        }else{
            holder.tvStatus.setText(status);
            holder.tvStatus.setTextColor(0xFFe74c3c);
        }

        Glide.with(view.getContext())
                .load(BASE_URL + "img/bencana/" + responsePengaduanData.get(position).getGambar())
                .into(imgBencana);
    }

    @Override
    public int getItemCount() {
        return responsePengaduanData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvTanggal, tvDeskripsiLocation, tvStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.tvNama);
            tvTanggal = itemView.findViewById(R.id.tvTanggal);
            tvDeskripsiLocation = itemView.findViewById(R.id.tvDeskripsiLocation);
            imgBencana = itemView.findViewById(R.id.imgBencana);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}
