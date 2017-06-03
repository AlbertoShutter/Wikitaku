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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by alber on 23/05/2017.
 */

public class AnimeEmisionAdapter extends RecyclerView.Adapter<AnimeEmisionAdapter.EmisionAnimeViewHolder> {

    private ArrayList<Anime> items = new ArrayList<Anime>();
    Context context;

    public static class EmisionAnimeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imagen;
        public TextView nombre;
        public TextView fecha;
        public TextView type;

        ArrayList<Anime> anime = new ArrayList<Anime>();
        Context context;

        public EmisionAnimeViewHolder(View v, Context context, ArrayList<Anime> anime) {
            super(v);
            this.anime = anime;
            this.context = context;
            v.setOnClickListener(this);
            imagen = (ImageView) v.findViewById(R.id.portadabuscar);
            nombre = (TextView) v.findViewById(R.id.textName);
            fecha = (TextView) v.findViewById(R.id.textFecha);
            type = (TextView) v.findViewById(R.id.textTipo);
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
            this.context.startActivity(intent);
        }
    }

    public AnimeEmisionAdapter(ArrayList<Anime> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public EmisionAnimeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.container_airing, viewGroup, false);
        return new EmisionAnimeViewHolder(v, context, items);
    }

    @Override
    public void onBindViewHolder(EmisionAnimeViewHolder holder, int position) {
        SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MMM-yyyy");
        Anime anime = items.get(position);
        Picasso.with(context)
                .load(anime.getImagen())
                .into(holder.imagen);
        holder.nombre.setText(items.get(position).getNombre());
        try {
            Date comienzo = parseador.parse(items.get(position).getFechaComienzo());
            holder.fecha.setText(formateador.format(comienzo));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.type.setText(items.get(position).getTipo());
    }
}
