package com.devdeloop.noticenows.Data;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.devdeloop.noticenows.Activities.PengumumanDetailActivity;
import com.devdeloop.noticenows.Model.Pengumuman;
import com.devdeloop.noticenows.R;

import java.util.List;

public class PengumumanRecViewAdapter extends RecyclerView.Adapter<PengumumanRecViewAdapter.ViewHolder> {

    private Context context;
    private List<Pengumuman> pengmumanList;

    public PengumumanRecViewAdapter(Context context, List<Pengumuman> pengumumen) {
        this.context = context;
        pengmumanList = pengumumen;
    }

    @Override
    public PengumumanRecViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pengumuman_row, parent, false);

        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(PengumumanRecViewAdapter.ViewHolder holder, int position) {
        Pengumuman pengumuman = pengmumanList.get(position);

//        holder.id_artikel.setText(pengumuman.getId_artikel());
        holder.judul.setText(pengumuman.getJudul());
        holder.kategori.setText(pengumuman.getKategori());
        holder.tanggal.setText(pengumuman.getTanggal_dibuat());
    }

    @Override
    public int getItemCount() {
        return pengmumanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView judul;
        TextView kategori;
        TextView tanggal;
//        TextView id_artikel;

        public ViewHolder(View itemView, final Context ctx) {
            super(itemView);
            context = ctx;

            judul = (TextView) itemView.findViewById(R.id.pengumumanJudul);
            kategori = (TextView) itemView.findViewById(R.id.pengumumanKategori);
            tanggal = (TextView) itemView.findViewById(R.id.pengumumanDate);
//            id_artikel = (TextView) itemView.findViewById(R.id.id_artikel);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Pengumuman pengumuman = pengmumanList.get(getAdapterPosition());

                    Intent intent = new Intent(context, PengumumanDetailActivity.class);

                    intent.putExtra("id_artikel", pengumuman.getId_artikel().toString());
                    ctx.startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View view) {

        }
    }
}
