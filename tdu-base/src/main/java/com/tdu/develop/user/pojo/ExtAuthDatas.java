package com.tdu.develop.user.pojo;

public class ExtAuthDatas
{
   
    private String Id ;
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
	private String Link ;
	private String PageName ;
	private int Num ;
	private int ParametersType;
}