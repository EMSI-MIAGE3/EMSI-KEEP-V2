package org.skafcommunity.emsikeep.models;

public class Professor extends User {
    public Professor() {
        super();
        this.setRole(UserRole.professor);
    }

    public Professor(String email, UserRole role) {
        super(email, role);
    }

    public Professor(User u){
        super(u);
    }

    public Professor(String uid, String lastName, String fistName, String email, String phone) {
        super(uid, lastName, fistName, email, phone, UserRole.professor);
    }

    public Professor(String lastName, String fistName, String email, String phone) {
        super(lastName, fistName, email, phone, UserRole.professor);
    }

    @Override
    public String toString() {
        return "Pr. " + this.getFirstName() + " " + this.getLastName();
    }
}
