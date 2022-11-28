package com.example.simplesaletransection;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RepairMenuAdapter extends BaseAdapter<RepairMenuAdapter.RepairViewHolder, UserRow> {

    private List<UserRow> userList;
    private Context context;
    String data[],data1[],data2[];
    int images[];

    public RepairMenuAdapter(Context ct,String t[],String t1[],String t2[],int img[]){
        context=ct;
        data=t;
        data1=t1;
        data2=t2;
        images=img;
    }


    private OnItemClickListener listener;

    public RepairMenuAdapter(OnItemClickListener listener) {

        this.listener = listener;
    }


    @Override
    public RepairViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_user_row2, parent, false);
        return new RepairViewHolder(inflate);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RepairViewHolder holder, @SuppressLint("RecyclerView") int position) {
        super.onBindViewHolder(holder, position);
        UserRow item = getData().get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onUserItemClicked(getData().get(position));
            }
        });

        if (holder.tvText != null && !TextUtils.isEmpty(item.getTitle())) {
            holder.tvText.setText(item.getTitle());
            holder.textView.setText(item.getTittle1());
            holder.textView4.setText(item.getSubTitle());
            Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(),item.getImageid());
            holder.imageView.setImageBitmap(bitmap);
        }
    }

    public interface OnItemClickListener {
        void onUserItemClicked(UserRow userRow);
    }

    class RepairViewHolder extends RecyclerView.ViewHolder {

        TextView tvText;
        TextView textView;
        TextView textView4;
        ImageView imageView;

        RepairViewHolder(View itemView) {
            super(itemView);
            tvText = (TextView) itemView.findViewById(R.id.tvText);
            textView = (TextView) itemView.findViewById(R.id.tvText1);
            textView4 = (TextView) itemView.findViewById(R.id.textView4);
            imageView = (ImageView) itemView.findViewById(R.id.delete);
        }
    }
}
