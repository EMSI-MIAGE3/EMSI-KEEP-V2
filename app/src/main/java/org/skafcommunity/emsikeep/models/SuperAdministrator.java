package org.skafcommunity.emsikeep.models;


//Singleton-Class
public class SuperAdministrator extends Administrator {

    public SuperAdministrator() {
        super();
        this.setRole(UserRole.superadministrator);
    }

    public SuperAdministrator(String email) {
        super(email);
        this.setRole(UserRole.superadministrator);
    }

    public SuperAdministrator(String uid, String lastName, String fistName, String email, String phone) {
        super(uid, lastName, fistName, email, phone);
        this.setRole(UserRole.superadministrator);
    }

    public SuperAdministrator(String lastName, String fistName, String email, String phone) {
        super(lastName, fistName, email, phone);
        this.setRole(UserRole.superadministrator);
    }
}
