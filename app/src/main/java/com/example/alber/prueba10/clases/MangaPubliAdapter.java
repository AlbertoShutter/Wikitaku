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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by alber on 27/05/2017.
 */

public class MangaPubliAdapter extends RecyclerView.Adapter<MangaPubliAdapter.PubliMangaViewHolder> {

    private ArrayList<Manga> items = new ArrayList<Manga>();
    Context context;

    public static class PubliMangaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imagen;
        public TextView nombre;
        public TextView fecha;
        public TextView type;
        //public TextView nota;

        ArrayList<Manga> manga = new ArrayList<Manga>();
        Context context;

        public PubliMangaViewHolder(View v, Context context, ArrayList<Manga> manga) {
            super(v);
            this.manga = manga;
            this.context = context;
            v.setOnClickListener(this);
            imagen = (ImageView) v.findViewById(R.id.portadabuscar);
            nombre = (TextView) v.findViewById(R.id.textName);
            fecha = (TextView) v.findViewById(R.id.textFecha);
            type = (TextView) v.findViewById(R.id.textTipo);
            //chapters = (TextView) v.findViewById(R.id.episodios);
            //nota = (TextView) v.findViewById(R.id.nota);
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
            this.context.startActivity(intent);
        }
    }

    public MangaPubliAdapter(ArrayList<Manga> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public PubliMangaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.container_airing, viewGroup, false);
        return new PubliMangaViewHolder(v, context, items);
    }

    @Override
    public void onBindViewHolder(PubliMangaViewHolder holder, int position) {
        Manga manga = items.get(position);
        SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MMM-yyyy");
        Picasso.with(context)
                .load(manga.getImagen())
                .into(holder.imagen);
        //viewHolder.imagen.setImageResource(items.get(i).getImagen());
        //viewHolder.nota.setText(""+items.get(i).getNota());
        holder.nombre.setText(items.get(position).getNombre());
        try {
            Date comienzo = parseador.parse(items.get(position).getFechaComienzo());
            holder.fecha.setText("Fecha comienzo: " + formateador.format(comienzo));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //holder.fecha.setText("Fecha comienzo: " + items.get(position).getFechaComienzo());
        holder.type.setText(items.get(position).getTipo());
    }
}
