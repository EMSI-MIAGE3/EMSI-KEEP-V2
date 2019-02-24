package org.skafcommunity.emsikeep.models;

import org.skafcommunity.emsikeep.access.firebase.firestore.FireStoreDAO;
import org.skafcommunity.emsikeep.dao.SchoolClassDAO;
import org.skafcommunity.emsikeep.dao.filtersListners.EntityListner;
import org.skafcommunity.emsikeep.prez.prezModels.FullDisplayNameSchoolClass;

public class Student extends User {

    private String schoolClassID;
    private String schoolClassGroup;

    public Student() {
        super();
        this.setRole(UserRole.student);
    }

    public Student(User u) {
        super(u);
    }

    public Student(String uid, String lastName, String fistName, String email, String phone, String schoolClassID) {
        super(uid, lastName, fistName, email, phone, UserRole.student);
        this.setSchoolClassID(schoolClassID);
    }

    public Student(String lastName, String fistName, String email, String phone, String schoolClassID) {
        super(lastName, fistName, email, phone, UserRole.student);
        this.setSchoolClassID(schoolClassID);
    }

    public String getSchoolClassID() {
        return schoolClassID;
    }

    public void setSchoolClassID(String schoolClassID) {
        this.schoolClassID = schoolClassID;
    }

    public String getSchoolClassGroup() {
        return schoolClassGroup;
    }

    public void setSchoolClassGroup(String schoolClassGroup) {
        this.schoolClassGroup = schoolClassGroup;
    }

    public FullDisplayNameSchoolClass getFullDisplayNameSchoolClass() {
        FullDisplayNameSchoolClass fullClassName = new FullDisplayNameSchoolClass();
        fullClassName.setGroup(schoolClassGroup);
        if(schoolClassID != null && "".equals(schoolClassID)) {
            SchoolClassDAO.get(schoolClassID, new EntityListner<SchoolClass>() {
                @Override
                public void doStuff(SchoolClass schoolClass) {
                    fullClassName.setSchoolClass(schoolClass.getName());
                }
            });
            return fullClassName;
        }
        else return null;

    }


}
