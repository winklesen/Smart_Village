package com.samuelbernard147.smartvillage.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.samuelbernard147.smartvillage.R;
import com.samuelbernard147.smartvillage.model.Lamp;

import java.util.ArrayList;

public class LampAdapter extends RecyclerView.Adapter<LampAdapter.LampViewHolder> {
    private ArrayList<Lamp> mData = new ArrayList<>();
    private Context context;

    public void setData(ArrayList<Lamp> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    public LampAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public LampAdapter.LampViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_lamp, viewGroup, false);
        return new LampViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull LampAdapter.LampViewHolder lampViewHolder, int i) {
        lampViewHolder.bind(mData.get(i));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class LampViewHolder extends RecyclerView.ViewHolder {
        ImageView imgLamp;
        TextView tvIdLamp, tvPowerLamp, tvUpdateLamp;

        LampViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLamp = itemView.findViewById(R.id.img_lamp);
            tvIdLamp = itemView.findViewById(R.id.tv_id_lamp);
            tvPowerLamp = itemView.findViewById(R.id.tv_power);
            tvUpdateLamp = itemView.findViewById(R.id.tv_last_update_lmp);
        }

        void bind(Lamp lamp) {
            String lampName = String.format(context.getResources().getString(R.string.item_id), lamp.getId());
            String lampPower = String.format(context.getResources().getString(R.string.item_power), lamp.getPower());
            String lampUpdate = String.format(context.getResources().getString(R.string.item_update), lamp.getUpdatedAt().substring(10));

            setImage(lamp.getStatus());
            tvIdLamp.setText(lampName);
            tvPowerLamp.setText(lampPower);
            tvUpdateLamp.setText(lampUpdate);
        }

        void setImage(boolean status) {
            if (status) {
                imgLamp.setImageResource(R.drawable.lightbulb_on_outline);
            } else {
                imgLamp.setImageResource(R.drawable.lightbulb_off_outline);
            }
        }
    }
}
