package org.example;

public class Elev extends Utilizator {
    String nume;
    String scoala;

    public Elev(String nume, String scoala) {
        super(nume);
        this.scoala = scoala;
    }
    public void setScoala(String scoala) {
        scoala = scoala;
    }
}
