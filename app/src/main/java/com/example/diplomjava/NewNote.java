package com.example.diplomjava;

public class NewNote {

    private String titleNote;
    private String textNote;
    private Integer checkDeadLine;
    private String dateAndTime;

    public NewNote(String titleNote, String textNote, Integer checkDeadLine, String dateAndTime) {
        this.titleNote = titleNote;
        this.textNote = textNote;
        this.checkDeadLine = checkDeadLine;
        this.dateAndTime = dateAndTime;
    }

    public String getTitleNote() {
        return titleNote;
    }

    public void setTitleNote(String titleNote) {
        this.titleNote = titleNote;
    }

    public String getTextNote() {
        return textNote;
    }

    public void setTextNote(String textNote) {
        this.textNote = textNote;
    }

    public Integer getCheckDeadLine() {
        return checkDeadLine;
    }

    public void setCheckDeadLine(Integer checkDeadLine) {
        this.checkDeadLine = checkDeadLine;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    @Override
    public String toString() {
        return "NewNote{" +
                "titleNote='" + titleNote + '\'' +
                ", textNote='" + textNote + '\'' +
                ", checkDeadLine=" + checkDeadLine +
                ", dateAndTime='" + dateAndTime + '\'' +
                '}';
    }
}
