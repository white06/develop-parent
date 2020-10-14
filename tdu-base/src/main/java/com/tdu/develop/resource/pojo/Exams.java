package com.tdu.develop.resource.pojo;


public class Exams {


    private String id;
    private String name;
    private String examPager;
    private String examType;
    private String subjectkey;
    private String startTime;
    private String endTime;
    private String invigilateKey;
    private String checkKey;
    private String classKey;
    private int Anony;

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public String getSubjectkey() {
        return subjectkey;
    }

    public void setSubjectkey(String subjectkey) {
        this.subjectkey = subjectkey;
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

    public String getExamPager() {
        return examPager;
    }

    public void setExamPager(String examPager) {
        this.examPager = examPager;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getInvigilateKey() {
        return invigilateKey;
    }

    public void setInvigilateKey(String invigilateKey) {
        this.invigilateKey = invigilateKey;
    }

    public String getCheckKey() {
        return checkKey;
    }

    public void setCheckKey(String checkKey) {
        this.checkKey = checkKey;
    }

    public String getClassKey() {
        return classKey;
    }

    public void setClassKey(String classKey) {
        this.classKey = classKey;
    }

    public int getAnony() {
        return Anony;
    }

    public void setAnony(int anony) {
        Anony = anony;
    }
}
