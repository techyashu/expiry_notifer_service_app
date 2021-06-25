package com.example.expirynotifier;

public class model {

    String Item,date;

    model()
    {

    }

    public model(String item, String date) {
        Item = item;
        this.date = date;
    }

    public void setItem(String item) {
        Item = item;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getItem() {
        return Item;
    }

    public String getDate() {
        return date;
    }

}



