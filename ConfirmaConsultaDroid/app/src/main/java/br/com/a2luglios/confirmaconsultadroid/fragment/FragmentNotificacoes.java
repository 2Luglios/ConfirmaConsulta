package br.com.a2luglios.confirmaconsultadroid.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

import br.com.a2luglios.confirmaconsultadroid.R;
import br.com.a2luglios.confirmaconsultadroid.firebase.FirebaseUtilStorage;
import br.com.a2luglios.confirmaconsultadroid.firebase.FirebaseUtilDB;
import br.com.a2luglios.confirmaconsultadroid.modelo.Mensagem;

/**
 * Created by ettoreluglio on 14/08/17.
 */

public class FragmentNotificacoes extends Fragment {

    private ListView listNotificacoes;
    private List<Mensagem> lista;

    private ProgressBar progressLoagind;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        lista = new ArrayList<>();

        View agenda = inflater.inflate(R.layout.fragment_notificacoes, container, false);
        progressLoagind = (ProgressBar) agenda.findViewById(R.id.progressLoading);

        listNotificacoes = (ListView) agenda.findViewById(R.id.listNotificacoes);

        new FirebaseUtilDB().readRTDBMensagens("/topics/propaganda",
                new FirebaseUtilDB.FirebaseRTDBUpdateLista<Mensagem>() {
            @Override
            public void updateConsultas(final List<Mensagem> novaLista) {
                lista.addAll(novaLista);
                new FirebaseUtilDB().readRTDBMensagens(FirebaseInstanceId.getInstance().getToken(),
                        new FirebaseUtilDB.FirebaseRTDBUpdateLista<Mensagem>() {
                            @Override
                            public void updateConsultas(List<Mensagem> novaLista) {
                                lista.addAll(novaLista);
                                carregaTudao();
                                progressLoagind.setIndeterminate(false);
                            }
                        });
            }
        });

        return agenda;
    }

    public void carregaTudao() {
        listNotificacoes.setAdapter(new ArrayAdapter<Mensagem>(getContext(),
                android.R.layout.simple_list_item_1, lista) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if ( lista.get(position).getTipo().equals("propaganda") ) {
                    final View v2 = LayoutInflater.from(getContext()).inflate(R.layout.item_propaganda_layout, null);
                    new FirebaseUtilStorage().getImage(lista.get(position).getImagem(), new FirebaseUtilStorage.FirebaseStorageImage() {
                        @Override
                        public void updateImage(Bitmap bitmap) {
                            ImageView image = (ImageView) v2.findViewById(R.id.imgPropaganda);
                            image.setImageBitmap(bitmap);
                        }
                    });
                    TextView text = (TextView) v2.findViewById(R.id.txtPropaganda);
                    text.setText(lista.get(position).getMensagem());
                    return v2;
                } else {
                    final View v1 = LayoutInflater.from(getContext()).inflate(R.layout.item_mensagem_layout, null);
                    new FirebaseUtilStorage().getImage(lista.get(position).getImagem(), new FirebaseUtilStorage.FirebaseStorageImage() {
                        @Override
                        public void updateImage(Bitmap bitmap) {
                            ImageView image = (ImageView) v1.findViewById(R.id.imgMensagem);
                            image.setImageBitmap(bitmap);
                        }
                    });
                    TextView text = (TextView) v1.findViewById(R.id.txtMensagem);
                    text.setText(lista.get(position).getMensagem());
                    return v1;
                }
            }
        });
    }
}
