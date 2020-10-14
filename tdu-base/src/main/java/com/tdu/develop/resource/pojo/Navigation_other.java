package com.tdu.develop.resource.pojo;

public class Navigation_other {
    private String id;
    private String name;
    private String columnLink;
    private String columnPicture;
    private int columnLevel;//导航栏的等级，1表示父级，2表示子级
    private String pId;
    private String columnUpper;
    private int userrole;//用户的可视权限，0表示所有用户都可视，1表示教师及管理员可视，2表示学生可视
    private String columnPid;

    public int getUserrole() {
        return userrole;
    }

    public void setUserrole(int userrole) {
        this.userrole = userrole;
    }

    public int getColumnLevel() {
        return columnLevel;
    }

    public void setColumnLevel(int columnLevel) {
        this.columnLevel = columnLevel;
    }

    public String getColumnUpper() {
        return columnUpper;
    }

    public void setColumnUpper(String columnUpper) {
        this.columnUpper = columnUpper;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColumnLink() {
        return columnLink;
    }

    public void setColumnLink(String columnLink) {
        this.columnLink = columnLink;
    }

    public String getColumnPicture() {
        return columnPicture;
    }

    public void setColumnPicture(String columnPicture) {
        this.columnPicture = columnPicture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColumnPid() {
        return columnPid;
    }

    public void setColumnPid(String columnPid) {
        this.columnPid = columnPid;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }
}
