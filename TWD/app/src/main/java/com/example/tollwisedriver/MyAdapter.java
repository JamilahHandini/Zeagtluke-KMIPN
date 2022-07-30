package com.example.tollwisedriver;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    android.content.Context context;
    ArrayList<Pelanggaran> list;

    public MyAdapter(android.content.Context context, ArrayList<Pelanggaran> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.recyclerview_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Pelanggaran pelanggaran = list.get(position);

        holder.pelanggaran.setText(pelanggaran.getPelanggaran());
        holder.tanggal.setText(pelanggaran.getTanggal());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{
        TextView pelanggaran, tanggal;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pelanggaran = itemView.findViewById(R.id.pelanggaran);
            tanggal = itemView.findViewById(R.id.tanggal);
        }
    }
}
