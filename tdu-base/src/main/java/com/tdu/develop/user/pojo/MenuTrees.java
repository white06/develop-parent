package com.tdu.develop.user.pojo;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-11-11-13:48
 */
public class MenuTrees {

    private String id;
    private String icon;
    private String treeName;
    private String	state;
    private List<MenuTrees> children;
    private String page;
    private boolean checked;
    private String domId;
    private Object target;
    private boolean _checked;
    public MenuTrees() {
        this.state = "open";
    }
    public boolean is_checked() {
        return _checked;
    }
    public void set_checked(boolean _checked) {
        this._checked = _checked;
    }

    public Object getTarget() {
        return target;
    }
    public void setTarget(Object target) {
        this.target = target;
    }
    public boolean isChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    public String getDomId() {
        return domId;
    }
    public void setDomId(String domId) {
        this.domId = domId;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getTreeName() {
        return treeName;
    }
    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public List<MenuTrees> getChildren() {
        return children;
    }
    public void setChildren(List<MenuTrees> children) {
        this.children = children;
    }
    public String getPage() {
        return page;
    }
    public void setPage(String page) {
        this.page = page;
    }
    @Override
    public String toString() {
        return "MenuTrees [id=" + id + ", icon=" + icon + ", treeName=" + treeName + ", state=" + state + ", children="
                + children + ", page=" + page + ", getId()=" + getId() + ", getIcon()=" + getIcon() + ", getTitle()="
                + getTreeName() + ", getState()=" + getState() + ", getChildren()=" + getChildren() + ", getPage()="
                + getPage() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
                + super.toString() + "]";
    }
}
