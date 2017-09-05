package br.com.a2luglios.confirmaconsultadroid.firebase;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by ettoreluglio on 24/08/17.
 */

public interface FirebaseLoginInterface {

    public void onSuccess(FirebaseUser user);
    public void onError(String txt);

}
