package org.skafcommunity.emsikeep.access.firebase.firestore;


import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.skafcommunity.emsikeep.dao.filtersListners.EntityListner;

import androidx.annotation.NonNull;

public class FireStoreDAO {

    public static CollectionReference getCollection(String collectionName) {
        return FirebaseFirestore.getInstance().collection(collectionName);
    }

    public static DocumentReference getDocument(String collectionName, String documentID) {
        return FirebaseFirestore.getInstance().collection(collectionName).document(documentID);
    }

    public static DocumentReference setDocument(String collectionName, String documentID, Object data) {
        DocumentReference document = FirebaseFirestore.getInstance().collection(collectionName).document(documentID);
        document.set(data);
        return document;
    }

    public static DocumentReference addDocument(String collectionName, Object data) {
        DocumentReference document = FirebaseFirestore.getInstance().collection(collectionName).document();
        document.set(data);
        return document;
    }

    public static void deleteDocument(String collectionName, String documentID, final EntityListner<Object> entityListner) {
        Log.d("DELETE_ENTITY", collectionName + "/" + documentID);
        DocumentReference document = FirebaseFirestore.getInstance().collection(collectionName).document(documentID);
        document.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("DELETE_ENTITY", "DocumentSnapshot successfully deleted!");
                entityListner.doStuff(new Boolean(true));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("DELETE_ENTITY", "Error deleting document", e);
                entityListner.doStuff(new Boolean(false));
            }
        });
    }


}
