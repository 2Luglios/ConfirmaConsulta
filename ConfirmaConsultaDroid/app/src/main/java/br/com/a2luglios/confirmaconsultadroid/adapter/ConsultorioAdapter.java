package br.com.a2luglios.confirmaconsultadroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.a2luglios.confirmaconsultadroid.R;
import br.com.a2luglios.confirmaconsultadroid.modelo.Consultorio;

/**
 * Created by ettoreluglio on 20/06/17.
 */

public class ConsultorioAdapter extends BaseAdapter {

    private Context ctx;
    private List<Consultorio> consultorios;

    public ConsultorioAdapter(Context ctx, List<Consultorio> consultorios) {
        this.ctx = ctx;
        this.consultorios = consultorios;
    }

    @Override
    public int getCount() {
        return consultorios.size();
    }

    @Override
    public Object getItem(int i) {
        return consultorios.get(i);
    }

    @Override
    public long getItemId(int i) {
        return consultorios.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_consultorio_layout, null);

        TextView consultorio = (TextView) v.findViewById(R.id.consultorio);
        consultorio.setText(consultorios.get(i).getNome());

        return v;
    }
}
