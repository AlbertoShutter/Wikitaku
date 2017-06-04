package com.example.alber.prueba10.clases;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alber.prueba10.R;
import com.example.alber.prueba10.activity.AnimeItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<Anime> data = new ArrayList<Anime>();

    public static class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textFishName;
        TextView textSize;
        TextView textType;
        TextView textPrice;
        ImageView imgPortada;

        Context context;
        ArrayList<Anime> anime = new ArrayList<Anime>();

        // create constructor to get widget reference
        public MyHolder(View v, Context context, ArrayList<Anime> anime) {
            super(v);
            this.anime = anime;
            this.context = context;
            v.setOnClickListener(this);
            textFishName= (TextView) itemView.findViewById(R.id.textFishName);
            textSize = (TextView) itemView.findViewById(R.id.textSize);
            textType = (TextView) itemView.findViewById(R.id.textType);
            textPrice = (TextView) itemView.findViewById(R.id.textPrice);
            imgPortada = (ImageView) itemView.findViewById(R.id.portadabuscar);
        }

        // Click event for all items
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Anime anime = this.anime.get(position);
            Intent intent = new Intent(this.context, AnimeItem.class);
            intent.putExtra("imagen", anime.getImagen());
            intent.putExtra("nombre", anime.getNombre());
            intent.putExtra("nota", anime.getNota());
            intent.putExtra("tipo", anime.getTipo());
            intent.putExtra("estado", anime.getEstado());
            intent.putExtra("episodios", anime.getEpisodios());
            intent.putExtra("fechacomienzo", anime.getFechaComienzo());
            intent.putExtra("nombreoriginal", anime.getNombreOriginal());
            intent.putExtra("fechafin", anime.getFechaFin());
            intent.putExtra("transmitido", anime.getPopularidad());
            intent.putExtra("duracion", anime.getDuracion());
            intent.putExtra("pegi", anime.getPegi());
            intent.putExtra("productores", anime.getProductores());
            intent.putExtra("estudio", anime.getEstudio());
            intent.putExtra("genero", anime.getGenero());
            intent.putExtra("sinopsis", anime.getSinopsis());
            intent.putExtra("enlacetrailer", anime.getEnlaceTrailer());
            intent.putExtra("temporada", anime.getTemporada());
            intent.putExtra("fuente", anime.getFuente());
            intent.putExtra("link", anime.getLink());

            this.context.startActivity(intent);
        }

    }

    // create constructor to initialize context and data sent from MainActivity
    public SearchAdapter(Context context, ArrayList<Anime> data){
        this.context=context;
        this.data=data;
    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }

    // Inflate the layout when ViewHolder created
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_search_anime, parent, false);
        return new MyHolder(v, context, data);
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in RecyclerView to bind data and assign values from list
        MyHolder myHolder= (MyHolder) holder;
        Anime current=data.get(position);
        Picasso.with(context)
                .load(current.getImagen())
                .into(myHolder.imgPortada);
        myHolder.textFishName.setText(current.getNombre());
        myHolder.textSize.setText("Capitulos: " + current.getEpisodios());
        myHolder.textType.setText("Tipo: " + current.getTipo());
        myHolder.textPrice.setText("Nota " + current.getNota());

    }
}
