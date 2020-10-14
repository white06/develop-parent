package com.tdu.develop.user.pojo;

import java.util.List;


public class AuthDatas {


    public List<ExtAuthDatas> children;
    private String Id;
    private String Link;

    public List<ExtAuthDatas> getChildren() {
        return children;
    }

    public void setChildren(List<ExtAuthDatas> children) {
        this.children = children;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getPageName() {
        return PageName;
    }

    public void setPageName(String pageName) {
        PageName = pageName;
    }

    public int getNum() {
        return Num;
    }

    public void setNum(int num) {
        Num = num;
    }

    public int getParametersType() {
        return ParametersType;
    }

    public void setParametersType(int parametersType) {
        ParametersType = parametersType;
    }

    private String PageName;
    private int Num;
    private int ParametersType;


    public AuthDatas(String id, String link, String pagename, int num, int ParametersType) {
        this.Id = id;
        this.Link = link;
        this.PageName = pagename;
        this.Num = num;
        this.ParametersType = ParametersType;

    }

    public AuthDatas(String id, String link, String pagename, int num, int ParametersType, List<ExtAuthDatas> children) {
        this.Id = id;
        this.Link = link;
        this.PageName = pagename;
        this.Num = num;
        this.ParametersType = ParametersType;
        this.children = children;
    }

}
