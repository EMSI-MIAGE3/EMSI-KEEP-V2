package org.skafcommunity.emsikeep.dao;


import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.skafcommunity.emsikeep.access.firebase.firestore.FireStoreDAO;
import org.skafcommunity.emsikeep.dao.filtersListners.EntityListner;
import org.skafcommunity.emsikeep.models.Administrator;
import org.skafcommunity.emsikeep.models.Administrator;
import org.skafcommunity.emsikeep.models.UserRole;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;

import androidx.annotation.NonNull;

public class AdministratorDAO {
    public static void add(final Administrator user, String password){
        if(user != null){
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.getEmail(), password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull final Task<AuthResult> task) {
                    final String uID = task.getResult().getUser().getUid();
                    user.setUid(uID);
                    FireStoreDAO.addDocument("Users", user).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot document = task.getResult();
                            Administrator userX = (Administrator) document.toObject(Administrator.class);
                            userX.setId(document.getId());
                            document.getReference().set(userX);
                        }
                    });
                }
            });
        }
    }

    public Task<DocumentSnapshot> update(String documentID, Administrator user, final EntityListner<Administrator> entityListner){
        return FireStoreDAO.setDocument("Users", documentID, user).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    entityListner.doStuff((Administrator) task.getResult().toObject(Administrator.class));
                }
            }
        });
    }

    public static void get(String documentID, final EntityListner<Administrator> entityListner){
        FireStoreDAO.getDocument("Users", documentID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                Administrator user = documentSnapshot.toObject(Administrator.class);
                entityListner.doStuff(user);
            }
        });
    }

    public static void delete(String documentID, EntityListner<Object> entityListner){
        FireStoreDAO.deleteDocument("Users", documentID, entityListner);
    }

    public static void getAll(final EntityListner<List<Administrator>> entityListner){

        Query ref = FireStoreDAO.getCollection("Users").whereEqualTo("role", UserRole.administrator.getValue());

        List<Administrator> users = new ArrayList<>();
        ref.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                entityListner.doStuff(queryDocumentSnapshots.toObjects(Administrator.class));
                Log.w("FireStoreDAO", "Admin GET ALL");
            }
        });
    }
}
