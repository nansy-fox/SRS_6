package com.example.srs_6;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public abstract class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder>{

        private final LayoutInflater inflater;
        private final List<Weather> weather = null;
        private final Context context;
        public WeatherAdapter(Context context, List<Weather>weather)
        {
                this.inflater= LayoutInflater.from(context);
                this.weather= weather;
                this.context= context;
        }
@NonNull
        @Override
        public WeatherAdapter.ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
                View view = inflater.inflate(R.layout.list, parent, false);
                return new ViewHolder(view);
        }

        @Override
        publicvoidonBindViewHolder(@NonNull WeatherAdapter.ViewHolder holder, int position) {
                Weather w = weather.get(position);
                Picasso.get().load(w.GetUrl()).fit().centerInside().into(holder.flagView);
                holder.nameView.setText(w.GetDate());
                holder.capitalView.setText(w.GetTemp());
        }

        @Override
        public int getItemCount() {
                return weather.size();
        }
        public static class ViewHolder extends RecyclerView.ViewHolder {
                final Image  flagView;
                final Text  nameView, capitalView;
                ViewHolder(View view){
                        super(view);
                        flagView= view.findViewById(R.id.list);
                        nameView= view.findViewById(R.id.temp);
                        capitalView= view.findViewById(R.id.condition);
                }
        }
}


