package com.samuelbernard147.smartvillage.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samuelbernard147.smartvillage.R;
import com.samuelbernard147.smartvillage.model.Humidity;

import java.util.ArrayList;

public class AgricultureAdapter extends RecyclerView.Adapter<AgricultureAdapter.AgriViewHolder> {
    private ArrayList<Humidity> mData = new ArrayList<>();
    private Context context;

    public void setData(ArrayList<Humidity> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    public AgricultureAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public AgricultureAdapter.AgriViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_agri, viewGroup, false);
        return new AgriViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull AgricultureAdapter.AgriViewHolder AgriViewHolder, int i) {
        AgriViewHolder.bind(mData.get(i));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class AgriViewHolder extends RecyclerView.ViewHolder {
        TextView tvTimeAgri, tvHumidityAgri;

        AgriViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTimeAgri = itemView.findViewById(R.id.tv_time_agri);
            tvHumidityAgri = itemView.findViewById(R.id.tv_humidity_agri);
        }

        void bind(Humidity humidity) {
            String ph = String.format(context.getResources().getString(R.string.item_humidity), humidity.getHumidity());
            tvTimeAgri.setText(humidity.getTime().substring(10));
            tvHumidityAgri.setText(ph);
        }
    }
}
