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
import org.skafcommunity.emsikeep.models.SchoolClass;
import org.skafcommunity.emsikeep.models.UserRole;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import androidx.annotation.NonNull;

public class SchoolClassDAO {
    public static void add(final SchoolClass schoolClass) {
        if (schoolClass != null) {
            FireStoreDAO.addDocument("SchoolClasses", schoolClass).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot document = task.getResult();
                    SchoolClass schoolClassX = (SchoolClass) document.toObject(SchoolClass.class);
                    schoolClassX.setId(document.getId());
                    document.getReference().set(schoolClassX);
                }
            });

        }
    }

    public Task<DocumentSnapshot> update(String documentID, SchoolClass schoolClass, final EntityListner<SchoolClass> entityListner) {
        return FireStoreDAO.setDocument("SchoolClasses", documentID, schoolClass).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    entityListner.doStuff((SchoolClass) task.getResult().toObject(SchoolClass.class));
                }
            }
        });
    }

    public static void get(String documentID, final EntityListner<SchoolClass> entityListner) {
        FireStoreDAO.getDocument("SchoolClasses", documentID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    SchoolClass schoolClass = task.getResult().toObject(SchoolClass.class);
                    entityListner.doStuff(schoolClass);
                } else {
                    entityListner.doStuff(null);
                }
            }
        });
    }

    public static void delete(String documentID, EntityListner<Object> entityListner) {
        FireStoreDAO.deleteDocument("SchoolClasses", documentID, entityListner);
    }

    public static void getAll(final EntityListner<List<SchoolClass>> entityListner) {

        Query ref = FireStoreDAO.getCollection("SchoolClasses");

        List<SchoolClass> schoolClasss = new ArrayList<>();
        ref.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                entityListner.doStuff(queryDocumentSnapshots.toObjects(SchoolClass.class));
            }
        });
    }
}
