package org.skafcommunity.emsikeep.models;

import java.io.Serializable;

public enum UserRole implements Serializable {
    professor("professor"),
    student("student"),
    administrator("administrator"),
    superadministrator("super-administrator");

    String value;

    UserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "value='" + value + '\'' +
                '}';
    }
}
