package com.example.numad22sp_nandishmurugeshi;

public class RViewCard implements ItemClickListener{

    private String itemName;
    private String itemUrl;
    private boolean checked;


    public RViewCard(String itemName, String itemUrl, boolean checked) {
        this.itemName = itemName;
        this.itemUrl = itemUrl;
        this.checked = checked;
    }

    @Override
    public void itemClick(int position) {
        checked = !checked;
    }

    @Override
    public void itemCheckClick(int position) {
        checked = !checked;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
