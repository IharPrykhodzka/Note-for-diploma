package com.example.diplomjava;

public class DataItems {

    private String title_view;
    private String subTitle_view;
    private String dateTime_view;
    private String checkBoxInInteger;
    private String id;

    public DataItems(String title_view, String subTitle_view, String dateTime_view, String checkBoxInInteger, String id) {
        this.title_view = title_view;
        this.subTitle_view = subTitle_view;
        this.dateTime_view = dateTime_view;
        this.checkBoxInInteger = checkBoxInInteger;
        this.id = id;
    }

    public String getTitle_view() {
        return title_view;
    }

    public void setTitle_view(String title_view) {
        this.title_view = title_view;
    }

    public String getSubTitle_view() {
        return subTitle_view;
    }

    public void setSubTitle_view(String subTitle_view) {
        this.subTitle_view = subTitle_view;
    }

    public String getDateTime_view() {
        return dateTime_view;
    }

    public void setDateTime_view(String dateTime_view) {
        this.dateTime_view = dateTime_view;
    }

    public String getCheckBoxInInteger() {
        return checkBoxInInteger;
    }

    public void setCheckBoxInInteger(String checkBoxInInteger) {
        this.checkBoxInInteger = checkBoxInInteger;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DataItems{" +
                "title_view='" + title_view + '\'' +
                ", subTitle_view='" + subTitle_view + '\'' +
                ", dateTime_view='" + dateTime_view + '\'' +
                ", checkBoxInInteger='" + checkBoxInInteger + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
