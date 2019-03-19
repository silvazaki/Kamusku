package com.example.kamusku.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kamusku.R;
import com.example.kamusku.data.model.Data;
import com.example.kamusku.util.InterfaceOnItemClick;

import java.util.ArrayList;

public class KamusAdapter extends RecyclerView.Adapter<KamusAdapter.KamusHolder> {
    private ArrayList<Data> mData = new ArrayList<>();
    public InterfaceOnItemClick.KamusItemCallback callback;

    public KamusAdapter() {

    }

    public void setCallback(InterfaceOnItemClick.KamusItemCallback callback) {
        this.callback = callback;
    }

    public Data getItem(int position) {
        return mData.get(position);
    }


    public void addItem(ArrayList<Data> mData) {
        this.mData.clear();
        this.mData.addAll(mData);
        Log.e("hasil", "addItem: "+mData.get(0).getKata());
        notifyDataSetChanged();
    }

    @Override
    public KamusHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kamus_row, parent, false);
        final KamusHolder holder = new KamusHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) {
                    callback.onItemClick(holder.getPosition(), view);
                }
            }
        });
        //ini untuk mengembalikan nilai view (position dari view tersebut)
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull KamusHolder holder, int position) {
        holder.textViewKata.setText(getItem(position).getKata());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class KamusHolder extends RecyclerView.ViewHolder {
        private TextView textViewKata;

        public KamusHolder(View itemView) {
            super(itemView);
            textViewKata = itemView.findViewById(R.id.txt_kata);
        }

    }
}