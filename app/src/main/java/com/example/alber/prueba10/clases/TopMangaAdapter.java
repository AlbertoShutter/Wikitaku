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
import com.example.alber.prueba10.activity.MangaItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by alber on 27/05/2017.
 */

public class TopMangaAdapter extends RecyclerView.Adapter<TopMangaAdapter.MangaViewHolder> {

    private ArrayList<Manga> items = new ArrayList<Manga>();
    Context context;

    public static class MangaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imagen;
        public TextView nombre;
        //public TextView info;
        //public TextView chapters;
        public TextView nota;

        ArrayList<Manga> manga = new ArrayList<Manga>();
        Context context;

        public MangaViewHolder(View v, Context context, ArrayList<Manga> manga) {
            super(v);
            this.manga = manga;
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
            Manga manga = this.manga.get(position);
            Intent intent = new Intent(this.context, MangaItem.class);
            intent.putExtra("nombre", manga.getNombre());
            intent.putExtra("capitulos", manga.getCapitulos());
            intent.putExtra("volumenes", manga.getVolumenes());
            intent.putExtra("tipo", manga.getTipo());
            intent.putExtra("estado", manga.getEstado());
            intent.putExtra("nota", manga.getNota());
            intent.putExtra("imagen", manga.getImagen());
            intent.putExtra("fechacomienzo", manga.getFechaComienzo());
            intent.putExtra("fechafin", manga.getFechaFin());
            intent.putExtra("genero", manga.getGenero());
            intent.putExtra("autor", manga.getAutor());
            intent.putExtra("serializacion", manga.getSerializacion());
            intent.putExtra("sinopsis", manga.getSinopsis());
            intent.putExtra("nombreoriginal", manga.getNombreOriginal());
            intent.putExtra("link", manga.getLink());
            this.context.startActivity(intent);
        }
    }

    public TopMangaAdapter(ArrayList<Manga> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public MangaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.top_manga_card, viewGroup, false);
        return new MangaViewHolder(v, context, items);
    }

    @Override
    public void onBindViewHolder(MangaViewHolder viewHolder, int i) {
        Manga manga = items.get(i);
        Picasso.with(context)
                .load(manga.getImagen())
                .into(viewHolder.imagen);
        //viewHolder.imagen.setImageResource(items.get(i).getImagen());
        viewHolder.nota.setText(""+items.get(i).getNota());
        viewHolder.nombre.setText(items.get(i).getNombre());
        //viewHolder.info.setText(items.get(i).getTipo() + " • " + items.get(i).getEstado());
        //viewHolder.chapters.setText(items.get(i).getEpisodios());
    }
}
