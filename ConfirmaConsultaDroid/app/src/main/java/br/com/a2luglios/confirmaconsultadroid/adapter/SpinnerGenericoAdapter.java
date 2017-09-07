package br.com.a2luglios.confirmaconsultadroid.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.a2luglios.confirmaconsultadroid.R;
import br.com.a2luglios.confirmaconsultadroid.modelo.Consultorio;
import br.com.a2luglios.confirmaconsultadroid.modelo.Usuario;

/**
 * Created by ettoreluglio on 05/09/17.
 */

public class SpinnerGenericoAdapter implements SpinnerAdapter {

    private Context ctx;
    private List lista;

    public SpinnerGenericoAdapter(Context ctx, List lista) {
        this.ctx = ctx;
        this.lista = lista;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflator = LayoutInflater.from(ctx);
        View v = inflator.inflate(R.layout.item_spinner_layout_colapsed, null);
        TextView texto = (TextView) v.findViewById(R.id.txtTexto);

        if (lista.get(i) instanceof Usuario) {
            Usuario usuario = (Usuario) lista.get(i);
            texto.setText(usuario.getNome());
        } else if (lista.get(i) instanceof  Consultorio) {
            Consultorio consultorio = (Consultorio) lista.get(i);
            texto.setText(consultorio.getNome());
        } else {
            texto.setText(lista.get(i).toString());
        }
        
        return v;
    }
    
    @Override
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflator = LayoutInflater.from(ctx);
        View v = inflator.inflate(R.layout.item_spinner_layout_dropdown, null);
        TextView texto = (TextView) v.findViewById(R.id.txtTexto);
        ImageView imgImagem = (ImageView) v.findViewById(R.id.imgImagem);

        if (lista.get(i) instanceof Usuario) {
            Usuario usuario = (Usuario) lista.get(i);
            texto.setText(usuario.getNome());
        } else if (lista.get(i) instanceof Consultorio) {
            Consultorio consultorio = (Consultorio) lista.get(i);
            texto.setText(consultorio.getNome());
        } else {
            v = inflator.inflate(R.layout.item_spinner_layout_colapsed, null);
            texto = (TextView) v.findViewById(R.id.txtTexto);
            texto.setText(lista.get(i).toString());
        }

        return v;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int i) {
        return lista.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
