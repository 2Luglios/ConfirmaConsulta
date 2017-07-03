package br.com.a2luglios.confirmaconsultadroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.a2luglios.confirmaconsultadroid.R;
import br.com.a2luglios.confirmaconsultadroid.modelo.Remedio;

/**
 * Created by ettoreluglio on 20/06/17.
 */

public class RemedioAdapter extends BaseAdapter {

    private Context ctx;
    private List<Remedio> remedios;

    public RemedioAdapter(Context ctx, List<Remedio> remedios) {
        this.ctx = ctx;
        this.remedios = remedios;
    }

    @Override
    public int getCount() {
        return remedios.size();
    }

    @Override
    public Object getItem(int i) {
        return remedios.get(i);
    }

    @Override
    public long getItemId(int i) {
        return remedios.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_remedio_layout, null);

        TextView remedio = (TextView) v.findViewById(R.id.remedio);
        remedio.setText(remedios.get(i).getNome());

        return v;
    }
}
