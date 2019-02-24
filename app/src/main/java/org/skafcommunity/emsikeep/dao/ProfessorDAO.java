package org.skafcommunity.emsikeep.dao;


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
import org.skafcommunity.emsikeep.models.Professor;
import org.skafcommunity.emsikeep.models.UserRole;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import androidx.annotation.NonNull;

public class ProfessorDAO {
    public static void add(final Professor user, String password){
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
                            Professor userX = (Professor) document.toObject(Professor.class);
                            userX.setId(document.getId());
                            document.getReference().set(userX);
                        }
                    });
                }
            });
        }
    }

    public Task<DocumentSnapshot> update(String documentID, Professor user, final EntityListner<Professor> entityListner){
        return FireStoreDAO.setDocument("Users", documentID, user).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    entityListner.doStuff((Professor) task.getResult().toObject(Professor.class));
                }
            }
        });
    }

    public static void get(String documentID, final EntityListner<Professor> entityListner){
        FireStoreDAO.getDocument("Users", documentID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    Professor user = task.getResult().toObject(Professor.class);
                    entityListner.doStuff(user);
                }
                else {
                    entityListner.doStuff(null);
                }
            }
        });
    }

    public static void delete(String documentID, EntityListner<Object> entityListner){
        FireStoreDAO.deleteDocument("Users", documentID, entityListner);
    }

    public static void getAll(final EntityListner<List<Professor>> entityListner){

        Query ref = FireStoreDAO.getCollection("Users").whereEqualTo("role", UserRole.professor.getValue());

        List<Professor> users = new ArrayList<>();
        ref.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                entityListner.doStuff(queryDocumentSnapshots.toObjects(Professor.class));
            }
        });
    }
}
