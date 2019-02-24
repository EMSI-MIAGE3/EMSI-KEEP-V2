package org.skafcommunity.emsikeep.staticDATA;

import com.google.firebase.auth.FirebaseUser;

import org.skafcommunity.emsikeep.models.User;


public class Session {
    private static final Session ourInstance = new Session();
    private User currentUser;
    private FirebaseUser firebaseUser;

    public static Session getInstance() {
        return ourInstance;
    }

    private Session() {
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    public void setFirebaseUser(FirebaseUser firebaseUser) {
        this.firebaseUser = firebaseUser;
    }
}
