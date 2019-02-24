package org.skafcommunity.emsikeep.prez.prezModels;

import org.skafcommunity.emsikeep.models.SchoolClass;

public class FullDisplayNameSchoolClass {
    private String schoolClass;
    private String Group;

    public String getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(String schoolClass) {
        this.schoolClass = schoolClass;
    }

    public String getGroup() {
        return Group;
    }

    public void setGroup(String group) {
        Group = group;
    }


    @Override
    public String toString() {
        return schoolClass + " - " + Group;
    }
}
