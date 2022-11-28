package com.example.simplesaletransection;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseAdapter<VH extends RecyclerView.ViewHolder, T extends Object> extends RecyclerView.Adapter<VH> {
    public OnItemChildClickListener<T> onItemChildClickListener;
    public OnItemChildCheckListener<T> onItemChildCheckListener;
    private OnItemClickListener<T> onItemClickListener;
    private List<T> list;

    @Override
    public void onBindViewHolder(VH holder, @SuppressLint("RecyclerView") int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null)
                    onItemClickListener.onClick(v, position, getData().get(position));
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemChildClickListener(OnItemChildClickListener<T> onItemChildClickListener) {
        this.onItemChildClickListener = onItemChildClickListener;
    }

    public void setOnItemChildCheckListener(OnItemChildCheckListener<T> onItemChildCheckListener) {
        this.onItemChildCheckListener = onItemChildCheckListener;
    }

    protected View inflate(ViewGroup parent, int resId) {
        return LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
    }

    public List<T> getData() {
        return list;
    }

    public void setData(List<T> list) {
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener<Ti> {
        void onClick(View view, int position, Ti data);
    }

    public interface OnItemChildClickListener<Ti> {
        void onClick(View view, int position, Ti data);
    }

    public interface OnItemChildCheckListener<Ti> {
        void onChecked(View view, int position, boolean isChecked, Ti data);
    }
}



