package com.tdu.develop.resource.pojo;

public class ZNodes {
	private String icon;
	private String imageicon;
	private String isparent="true";
	private String drop="true";
	private String id;
	private String pId;
	private String name;
	private String open="false";
	private String knowledgecontentId;
	private String columnLink;
	
	public String getColumnLink() {
		return columnLink;
	}
	public void setColumnLink(String columnLink) {
		this.columnLink = columnLink;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getImageicon() {
		return imageicon;
	}
	public void setImageicon(String imageicon) {
		this.imageicon = imageicon;
	}
	public String getDrop() {
		return drop;
	}
	public void setDrop(String drop) {
		this.drop = drop;
	}
	
	public String getIsparent() {
		return isparent;
	}
	public void setIsparent(String isparent) {
		this.isparent = isparent;
	}
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public String getKnowledgecontentId() {
		return knowledgecontentId;
	}
	public void setKnowledgecontentId(String knowledgecontentId) {
		this.knowledgecontentId = knowledgecontentId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "ZNodes [icon=" + icon + ", imageicon=" + imageicon + ", isparent=" + isparent + ", drop=" + drop
				+ ", id=" + id + ", pId=" + pId + ", name=" + name + ", open=" + open + ", knowledgecontentId="
				+ knowledgecontentId + "]";
	}

}
