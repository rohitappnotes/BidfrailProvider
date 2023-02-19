package com.bidfrail.provider.dialogsingleselect;

public class SingleSelect {

    private String id;
    private String name;
    private String image;
    private boolean isSelected;

    public SingleSelect() {
    }

    public SingleSelect(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public SingleSelect(String id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public SingleSelect(String id, String name, String image, boolean isSelected) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.isSelected = isSelected;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
