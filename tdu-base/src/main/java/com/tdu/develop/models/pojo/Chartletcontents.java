package com.tdu.develop.models.pojo;


/**
 * 贴图内容表  前台页面
 *
 * @author TDU
 */
public class Chartletcontents {


    private String Id;
    private String Nmae;
    private String Type;
    private String Introduce;
    private String CustomName;
    private String CustomStyle;
    private String Chartlet_Id;
    private String ImageContentIcons;
    private int Order;

    private String userKey;
    private String PhotoName;
    private String Url;

    private int CheckDel;
    private String DelTime;


    public int getCheckDel() {
        return CheckDel;
    }

    public void setCheckDel(int checkDel) {
        CheckDel = checkDel;
    }

    public String getDelTime() {
        return DelTime;
    }

    public void setDelTime(String delTime) {
        DelTime = delTime;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getPhotoName() {
        return PhotoName;
    }

    public void setPhotoName(String photoName) {
        PhotoName = photoName;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getNmae() {
        return Nmae;
    }

    public void setNmae(String nmae) {
        Nmae = nmae;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getIntroduce() {
        return Introduce;
    }

    public void setIntroduce(String introduce) {
        Introduce = introduce;
    }

    public String getCustomName() {
        return CustomName;
    }

    public void setCustomName(String customName) {
        CustomName = customName;
    }

    public String getCustomStyle() {
        return CustomStyle;
    }

    public void setCustomStyle(String customStyle) {
        CustomStyle = customStyle;
    }

    public String getChartlet_Id() {
        return Chartlet_Id;
    }

    public void setChartlet_Id(String chartlet_Id) {
        Chartlet_Id = chartlet_Id;
    }

    public String getImageContentIcons() {
        return ImageContentIcons;
    }

    public void setImageContentIcons(String imageContentIcons) {
        ImageContentIcons = imageContentIcons;
    }

    public int getOrder() {
        return Order;
    }

    public void setOrder(int order) {
        Order = order;
    }

}
