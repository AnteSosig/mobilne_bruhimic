package com.example.kolokvijumvezba;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ZadatakAdapter extends RecyclerView.Adapter<ZadatakAdapter.VH> {
    private final List<Zadatak> items = new ArrayList<>();

    public void submit(List<Zadatak> data) {
        items.clear();
        items.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_zadatak, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Zadatak z = items.get(position);
        holder.naziv.setText(z.getNaziv());
        holder.vreme.setText(z.getVreme());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        final TextView naziv;
        final TextView vreme;

        VH(@NonNull View itemView) {
            super(itemView);
            naziv = itemView.findViewById(R.id.item_naziv);
            vreme = itemView.findViewById(R.id.item_vreme);
        }
    }
}

