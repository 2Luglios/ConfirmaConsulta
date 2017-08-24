package br.com.a2luglios.confirmaconsultadroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.a2luglios.confirmaconsultadroid.R;
import br.com.a2luglios.confirmaconsultadroid.modelo.Usuario;

/**
 * Created by ettoreluglio on 20/06/17.
 */

public class MedicoAdapter extends BaseAdapter {

    private Context ctx;
    private List<Usuario> medicos;

    public MedicoAdapter(Context ctx, List<Usuario> medicos) {
        this.ctx = ctx;
        this.medicos = medicos;
    }

    @Override
    public int getCount() {
        return medicos.size();
    }

    @Override
    public Object getItem(int i) {
        return medicos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return medicos.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_medico_layout, null);

        TextView medico = (TextView) v.findViewById(R.id.medico);
        medico.setText(medicos.get(i).getNome());

        return v;
    }
}
