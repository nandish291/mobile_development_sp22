package com.example.numad22sp_nandishmurugeshi;

public class RViewCard{

    private String itemName;
    private String itemUrl;


    public RViewCard(String itemName, String itemUrl) {
        this.itemName = itemName;
        this.itemUrl = itemUrl;
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
}
