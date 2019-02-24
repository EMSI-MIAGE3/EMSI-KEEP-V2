package org.skafcommunity.emsikeep.models;

import java.io.Serializable;

public class SchoolSession implements Serializable {
    private String id;
    private String professorID;
    private String schoolClassID;
    private String schoolSubject;
    private SchoolSeanceType sessionType;
    private String classroom;
    private WeekDay day;
    private String startingTime;
    private String endingTime;
    private String startingDate;
    private Integer seancesCount;

    public SchoolSession() {
        super();
    }

    public SchoolSession(String professorID, String schoolClassID, String schoolSubject, SchoolSeanceType sessionType, String classroom, WeekDay day, String startingTime, String endingTime, String startingDate, Integer seancesCount) {
        this.professorID = professorID;
        this.schoolClassID = schoolClassID;
        this.schoolSubject = schoolSubject;
        this.sessionType = sessionType;
        this.classroom = classroom;
        this.day = day;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
        this.startingDate = startingDate;
        this.seancesCount = seancesCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfessorID() {
        return professorID;
    }

    public void setProfessorID(String professorID) {
        this.professorID = professorID;
    }

    public String getSchoolClassID() {
        return schoolClassID;
    }

    public void setSchoolClassID(String schoolClassID) {
        this.schoolClassID = schoolClassID;
    }

    public String getSchoolSubject() {
        return schoolSubject;
    }

    public void setSchoolSubject(String schoolSubject) {
        this.schoolSubject = schoolSubject;
    }

    public SchoolSeanceType getSessionType() {
        return sessionType;
    }

    public void setSessionType(SchoolSeanceType sessionType) {
        this.sessionType = sessionType;
    }

    public WeekDay getDay() {
        return day;
    }

    public void setDay(WeekDay day) {
        this.day = day;
    }

    public String getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(String startingTime) {
        this.startingTime = startingTime;
    }

    public String getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(String endingTime) {
        this.endingTime = endingTime;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    public Integer getSeancesCount() {
        return seancesCount;
    }

    public void setSeancesCount(Integer seancesCount) {
        this.seancesCount = seancesCount;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }
}
