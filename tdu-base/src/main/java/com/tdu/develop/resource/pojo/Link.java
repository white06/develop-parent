package com.tdu.develop.resource.pojo;


import java.util.Date;

public class Link {

	private String id;
	private String longUrl;
	private String shortUrl;


	//有效时间
	private Date time;

	//分享时间
	private Date sharetime;
	//分享次数
	private int sharecount;
	//分享用户id
	private String userId;
	//文件名称
	private String name;

	public String getName() {


		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSharecount() {
		return sharecount;
	}

	public void setSharecount(int sharecount) {
		this.sharecount = sharecount;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	public Date getSharetime() {
		return sharetime;
	}

	public void setSharetime(Date sharetime) {
		this.sharetime = sharetime;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	private String yanzheng;

	private String jiami;

	public String getJiami() {
		return jiami;
	}

	public void setJiami(String jiami) {
		this.jiami = jiami;
	}

	public String getYanzheng() {
		return yanzheng;
	}

	public void setYanzheng(String yanzheng) {
		this.yanzheng = yanzheng;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLongUrl() {
		return longUrl;
	}

	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

}
