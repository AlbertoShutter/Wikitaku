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

public class SearchMangaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<Manga> data = new ArrayList<Manga>();

    public static class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textFishName;
        TextView textSize;
        TextView textSize2;
        TextView textType;
        TextView textPrice;
        ImageView imgPortada;

        Context context;
        ArrayList<Manga> manga = new ArrayList<Manga>();

        // create constructor to get widget reference
        public MyHolder(View v, Context context, ArrayList<Manga> manga) {
            super(v);
            this.manga = manga;
            this.context = context;
            v.setOnClickListener(this);
            textFishName= (TextView) itemView.findViewById(R.id.textFishName);
            textSize = (TextView) itemView.findViewById(R.id.textSize);
            textSize2 = (TextView) itemView.findViewById(R.id.textSize2);
            textType = (TextView) itemView.findViewById(R.id.textType);
            textPrice = (TextView) itemView.findViewById(R.id.textPrice);
            imgPortada = (ImageView) itemView.findViewById(R.id.portadabuscar);
        }

        // Click event for all items
        @Override
        public void onClick(View v) {
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

    // create constructor to initialize context and data sent from MainActivity
    public SearchMangaAdapter(Context context, ArrayList<Manga> data){
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
                .inflate(R.layout.card_search_manga, parent, false);
        return new MyHolder(v, context, data);
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in RecyclerView to bind data and assign values from list
        MyHolder myHolder= (MyHolder) holder;
        Manga current=data.get(position);
        Picasso.with(context)
                .load(current.getImagen())
                .into(myHolder.imgPortada);
        myHolder.textFishName.setText(current.getNombre());
        myHolder.textSize.setText("Capitulos: " + current.getCapitulos());
        myHolder.textSize2.setText("Volumenes: " + current.getVolumenes());
        myHolder.textType.setText("Tipo: " + current.getTipo());
        myHolder.textPrice.setText("Nota " + current.getNota());

    }
}
