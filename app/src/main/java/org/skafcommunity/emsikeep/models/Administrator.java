package org.skafcommunity.emsikeep.models;

public class Administrator extends User {

    public Administrator() {
        this.setRole(UserRole.administrator);
    }

    public Administrator(String email) {
        super(email, UserRole.administrator);
    }

    public Administrator(String uid, String lastName, String fistName, String email, String phone) {
        super(uid, lastName, fistName, email, phone, UserRole.administrator);
    }

    public Administrator(String lastName, String fistName, String email, String phone) {
        super(lastName, fistName, email, phone, UserRole.administrator);
    }

    @Override
    public String toString() {
        return "Mr. " + this.getFirstName() + " " + this.getLastName();
    }
}
