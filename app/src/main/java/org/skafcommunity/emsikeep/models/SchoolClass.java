package org.skafcommunity.emsikeep.models;

import java.io.Serializable;

public class SchoolClass implements Serializable {
    private String id;
    private String name;
    private String level;


    public SchoolClass() {
        super();
    }

    public SchoolClass(String name, String level) {
        this.name = name;
        this.level = level;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return  this.name;
    }
}
