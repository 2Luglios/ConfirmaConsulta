package br.com.a2luglios.confirmaconsultadroid.firebase;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * Created by ettoreluglio on 20/08/17.
 */

public class FirebaseUtilStorage {

    private final FirebaseStorage storage;
    private final long ONE_MEGABYTE = 1024 * 1024;

    public FirebaseUtilStorage() {
        storage = FirebaseStorage.getInstance("gs://confirmaconsulta-63f26.appspot.com");
    }

    public void setBytes(String raiz, byte[] bytes) {
        StorageReference storageRef = storage.getReference().child(raiz);
        UploadTask uploadTask = storageRef.putBytes(bytes);
//        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Uri downloadUrl = taskSnapshot.getDownloadUrl();
//            }
//        });
    }

    public void getImage(String raiz, final FirebaseStorageImage firebaseStorageImage) {
        StorageReference storageRef = storage.getReference().child(raiz);
        storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap img = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                firebaseStorageImage.updateImage(img);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("FirebaseStorage", "Error reading img " + exception.getMessage());
            }
        });
    }

    public interface FirebaseStorageImage {
        public void updateImage(Bitmap bitmap);
    }

}
