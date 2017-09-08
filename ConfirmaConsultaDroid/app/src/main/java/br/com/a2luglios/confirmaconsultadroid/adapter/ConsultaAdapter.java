package br.com.a2luglios.confirmaconsultadroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import br.com.a2luglios.confirmaconsultadroid.R;
import br.com.a2luglios.confirmaconsultadroid.firebase.FirebaseRTDBSaved;
import br.com.a2luglios.confirmaconsultadroid.firebase.FirebaseRTDBUpdate;
import br.com.a2luglios.confirmaconsultadroid.firebase.FirebaseUtilDB;
import br.com.a2luglios.confirmaconsultadroid.modelo.Consulta;

/**
 * Created by ettoreluglio on 19/06/17.
 */

public class ConsultaAdapter extends BaseAdapter {

    private Context ctx;
    private List<Consulta> consultas;
    private SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat sdfHora = new SimpleDateFormat("hh:mm");
    private ConsultaAdapter.Update listener;

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
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_consulta_layout, null);

        Date data = new Date();
        data.setTime(consultas.get(i).getDataInicio());

        TextView txtData = (TextView) v.findViewById(R.id.txtData);
        txtData.setText(sdfData.format(data));
        TextView txtHora = (TextView) v.findViewById(R.id.txtHora);
        txtHora.setText(sdfHora.format(data));
        TextView txtMedico = (TextView) v.findViewById(R.id.txtMedico);
        txtMedico.setText(consultas.get(i).getMedico());
        TextView txtClinica = (TextView) v.findViewById(R.id.txtClinica);
        txtClinica.setText(consultas.get(i).getConsultorio());
        ImageView imgConfirmacao = (ImageView) v.findViewById(R.id.imgConfirmacao);

        ImageButton btnDelete = (ImageButton) v.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FirebaseUtilDB().deleteRTDB("/consultas/" + consultas.get(i).getHash(), new FirebaseRTDBSaved() {
                    @Override
                    public void saved() {
                        listener.update();
                    }
                });
            }
        });

        return v;
    }

    public void setListener(ConsultaAdapter.Update listener) {
        this.listener = listener;
    }

    public interface Update {
        public void update();
    }
}


