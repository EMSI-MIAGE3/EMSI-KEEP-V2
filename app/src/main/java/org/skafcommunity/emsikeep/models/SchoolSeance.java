package org.skafcommunity.emsikeep.models;

import java.io.Serializable;

public class SchoolSeance implements Serializable {

    private String id;
    private String schoolSessionID;
    private String professorID;
    private String schoolClassID;
    private String schoolSubject;
    private SchoolSeanceType sessionType;
    private String classroom;
    private WeekDay day;
    private String startingTime;
    private String endingTime;
    private String date;
    private String details;

    public SchoolSeance() {
        super();
    }

    public SchoolSeance(String schoolSessionID, String professorID, String schoolClassID, String schoolSubject, SchoolSeanceType sessionType, String classroom, WeekDay day, String startingTime, String endingTime, String date, String details) {
        this.schoolSessionID = schoolSessionID;
        this.professorID = professorID;
        this.schoolClassID = schoolClassID;
        this.schoolSubject = schoolSubject;
        this.sessionType = sessionType;
        this.classroom = classroom;
        this.day = day;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
        this.date = date;
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchoolSessionID() {
        return schoolSessionID;
    }

    public void setSchoolSessionID(String schoolSessionID) {
        this.schoolSessionID = schoolSessionID;
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

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
