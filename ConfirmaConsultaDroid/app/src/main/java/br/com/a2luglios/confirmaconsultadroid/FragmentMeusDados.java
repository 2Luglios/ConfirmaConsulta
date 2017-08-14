package br.com.a2luglios.confirmaconsultadroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ettoreluglio on 14/08/17.
 */

public class FragmentMeusDados extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View meusDados =
                inflater.inflate(R.layout.fragment_meus_dados,
                        container, false);

        return meusDados;
    }
}
