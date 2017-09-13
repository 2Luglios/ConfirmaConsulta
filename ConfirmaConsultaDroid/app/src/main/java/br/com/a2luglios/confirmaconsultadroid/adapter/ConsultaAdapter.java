package br.com.a2luglios.confirmaconsultadroid.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import br.com.a2luglios.confirmaconsultadroid.R;
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

        Button btnEdit = (Button) v.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle("Edit");
                builder.setMessage("Remarcação e/ou confirmação");
                builder.setNeutralButton("OK", null);
                builder.show();
            }
        });

        Button btnMapa = (Button) v.findViewById(R.id.btnMapa);
        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle("Mapa");
                builder.setMessage("Rua das casas, 100");
                builder.setPositiveButton("WAZE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String uri = "waze://?ll=-23.5837077,-46.5725637&navigate=yes";
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        intent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                        intent.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                        intent.setData(Uri.parse(uri));
                        ctx.startActivity(intent);
                    }
                });
                builder.setNegativeButton("MAPS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String uri = "http://maps.google.com/maps?q=loc:-23.5837077,-46.5725637";
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        intent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                        intent.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                        intent.setData(Uri.parse(uri));
                        ctx.startActivity(intent);
                    }
                });
                builder.setNeutralButton("Cancelar", null);
                builder.show();
            }
        });

        Button btnDelete = (Button) v.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle("Apagar Consulta");
                builder.setMessage("Deseja cancelar esta consulta?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new FirebaseUtilDB().deleteRTDB("/consultas/" + consultas.get(i).getHash(), new FirebaseUtilDB.FirebaseRTDBSaved() {
                            @Override
                            public void saved() {
                                listener.update();
                            }
                        });
                    }
                });
                builder.setNegativeButton("Não", null);
                builder.show();
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
