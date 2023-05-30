package com.example.notlar;

public class MyData {
    private String txtNotes, txtTitle,txtUid;

    public MyData(String notes,String title) {
        this.txtNotes = notes;
        this.txtTitle = title;
    }
    public MyData(String notes,String title,String txtUid) {
        this.txtNotes = notes;
        this.txtTitle = title;
        this.txtUid = txtUid;
    }

    public String getTxtNotes() {
        return txtNotes;
    }

    public void setTxtNotes(String txtNotes) {
        this.txtNotes = txtNotes;
    }

    public String getTxtTitle() {
        return txtTitle;
    }

    public void setTxtTitle(String txtTitle) {
        this.txtTitle = txtTitle;
    }

    public String getTxtUid() {
        return txtUid;
    }

    public void setTxtUid(String txtUid) {
        this.txtUid = txtUid;
    }
}





