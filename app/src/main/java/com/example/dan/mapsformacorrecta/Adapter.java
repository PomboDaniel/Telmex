package com.example.dan.mapsformacorrecta;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    private ArrayList<Regiones> lista;
    private Context context;


    public Adapter(ArrayList<Regiones> lista, Context context){

        this.lista = lista;
        this.context = context;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView titulo;
        private LinearLayout ly;

        public ViewHolder(View itemView){
            super(itemView);

            titulo = itemView.findViewById(R.id.txtTitulo);
            ly = itemView.findViewById(R.id.linearlayut);
        }
    }


    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);

        return new ViewHolder(root);
    }


    @Override
    public void onBindViewHolder(Adapter.ViewHolder holder, final int position) {

        holder.titulo.setText(lista.get(position).getTitulo());

        holder.ly.setOnClickListener(new View.OnClickListener() {           //Ponemos a la escucha el layout que tiene el cardview
            @Override
            public void onClick(View view) {

                if(position == 0){          //Los cardView empiezan en la posicion 0 automaticamente.

                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("position_card", position);
                    context.startActivity(intent);
                }

                //Toast.makeText(context, "Click en: " + position, Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return lista.size();
    }

}
