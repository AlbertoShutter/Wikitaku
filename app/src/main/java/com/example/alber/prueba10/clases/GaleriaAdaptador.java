package com.example.alber.prueba10.clases;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.alber.prueba10.R;
import com.example.alber.prueba10.activity.DetalleActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alber on 28/05/2017.
 */

public class GaleriaAdaptador extends RecyclerView.Adapter<GaleriaAdaptador.ViewHolder> {

    private Context context;
    private ArrayList<MangaImagen> imagenes = new ArrayList<MangaImagen>();

    public GaleriaAdaptador(Context context, ArrayList<MangaImagen> imagenes) {
        this.context = context;
        this.imagenes = imagenes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);

        return new ViewHolder(itemView, context, imagenes);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Glide.with(context).load(imagenes.get(position).getImageLink()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imagenes.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView movieName;
        public ImageView imageView;

        List<MangaImagen> imagenes = new ArrayList<MangaImagen>();
        Context context;

        public ViewHolder(View itemView, Context context, List<MangaImagen> imagenes) {
            super(itemView);
            this.imagenes = imagenes;
            this.context = context;
            itemView.setOnClickListener(this);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            imageView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            MangaImagen galeria = this.imagenes.get(position);
            Intent intent = new Intent(this.context, DetalleActivity.class);
            intent.putExtra("imagen", galeria.getImageLink());
            this.context.startActivity(intent);
        }
    }
}
