package br.com.a2luglios.confirmaconsultadroid.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View agenda = inflater.inflate(R.layout.fragment_notificacoes, container, false);

        listNotificacoes = (ListView) agenda.findViewById(R.id.listNotificacoes);

        carregaTudao();

        listNotificacoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        listNotificacoes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                return false;
            }
        });


        return agenda;
    }

    public void carregaTudao() {
        new FirebaseUtilDB().readRTDBMensagens("mensagens", new FirebaseUtilDB.FirebaseRTDBUpdateLista<Mensagem>() {
            @Override
            public void updateConsultas(final List<Mensagem> lista) {
                FragmentNotificacoes.this.lista = lista;

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
        });
    }
}
