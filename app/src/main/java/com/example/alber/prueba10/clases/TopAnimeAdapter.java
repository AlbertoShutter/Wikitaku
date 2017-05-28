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

/**
 * Creado por albert
 */
public class TopAnimeAdapter extends RecyclerView.Adapter<TopAnimeAdapter.AnimeViewHolder> {

    private ArrayList<Anime> items = new ArrayList<Anime>();
    Context context;

    public static class AnimeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imagen;
        public TextView nombre;
        //public TextView info;
        //public TextView chapters;
        public TextView nota;

        ArrayList<Anime> anime = new ArrayList<Anime>();
        Context context;

        public AnimeViewHolder(View v, Context context, ArrayList<Anime> anime) {
            super(v);
            this.anime = anime;
            this.context = context;
            v.setOnClickListener(this);
            imagen = (ImageView) v.findViewById(R.id.imagen);
            nombre = (TextView) v.findViewById(R.id.nombre);
            //info = (TextView) v.findViewById(R.id.visitas);
            //chapters = (TextView) v.findViewById(R.id.episodios);
            nota = (TextView) v.findViewById(R.id.nota);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Anime anime = this.anime.get(position);
            Intent intent = new Intent(this.context, AnimeItem.class);
            intent.putExtra("imagen", anime.getImagen());
            intent.putExtra("nombre", anime.getNombre());
            intent.putExtra("nota", anime.getNota());
            intent.putExtra("tipo", anime.getTipo());
            intent.putExtra("estado", anime.getEstado());
            intent.putExtra("episodios", anime.getEpisodios());
            this.context.startActivity(intent);
        }
    }

    public TopAnimeAdapter(ArrayList<Anime> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public AnimeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.top_anime_card, viewGroup, false);
        return new AnimeViewHolder(v, context, items);
    }

    @Override
    public void onBindViewHolder(AnimeViewHolder viewHolder, int i) {
        Anime anime = items.get(i);
        Picasso.with(context)
            .load(anime.getImagen())
            .into(viewHolder.imagen);
        //viewHolder.imagen.setImageResource(items.get(i).getImagen());
        viewHolder.nota.setText(""+items.get(i).getNota());
        viewHolder.nombre.setText(items.get(i).getNombre());
        //viewHolder.info.setText(items.get(i).getTipo() + " • " + items.get(i).getEstado());
        //viewHolder.chapters.setText(items.get(i).getEpisodios());
    }
}
