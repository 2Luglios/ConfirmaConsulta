package br.com.a2luglios.confirmaconsultadroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import br.com.a2luglios.confirmaconsultadroid.R;
import br.com.a2luglios.confirmaconsultadroid.modelo.Consulta;

/**
 * Created by ettoreluglio on 19/06/17.
 */

public class ConsultaAdapter extends BaseAdapter {

    private Context ctx;
    private List<Consulta> consultas;

    public ConsultaAdapter(Context ctx, List<Consulta> consultas) {
        this.ctx = ctx;
        this.consultas = consultas;
    }

    @Override
    public int getCount() {
        return consultas.size();
    }

    @Override
    public Object getItem(int i) {
        return consultas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return consultas.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_consulta_layout, null);

        TextView medico = (TextView) v.findViewById(R.id.medico);
        medico.setText(consultas.get(i).getMedico());

        return v;
    }
}
