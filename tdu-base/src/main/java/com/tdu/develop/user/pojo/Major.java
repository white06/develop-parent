package com.tdu.develop.user.pojo;

public class Major {
    public String Id;
    public String MajorName;
    public int MajorOrder;
    public String College_Id;
     
     /*新增专业下的所有科目集合
     List<Subjects> subList;*/


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getMajorName() {
        return MajorName;
    }

    public void setMajorName(String majorName) {
        MajorName = majorName;
    }

    public int getMajorOrder() {
        return MajorOrder;
    }

    public void setMajorOrder(int majorOrder) {
        MajorOrder = majorOrder;
    }

    public String getCollege_Id() {
        return College_Id;
    }

    public void setCollege_Id(String college_Id) {
        College_Id = college_Id;
    }
}
