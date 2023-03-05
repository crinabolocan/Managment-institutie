package org.example;

public class Cerere {
    String continut;
    String data;
    String ora;
    int prioritate;

    public String getContinut() {
        return continut;
    }

    public void setContinut(String continut) {
        this.continut = continut;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOra() {
        return ora;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    public int getPrioritate() {
        return prioritate;
    }

    public void setPrioritate(int prioritate) {
        this.prioritate = prioritate;
    }

    public enum tipCerere{
        inlocuirebuletin,
        inregistrarevenitsalarial,
        inlocuirecarnetdesofer,
        inlocuirecarnetdeelev,
        creareactconstitutiv,
        reinnoireautorizatie,
        inregistrarecupoanedepensie,
    }

    public Cerere(String continut, String data, String ora, int prioritate) {
        this.continut = continut;
        this.data = data;
        this.ora = ora;
        this.prioritate = prioritate;
    }


}


