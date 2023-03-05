package org.example;

public class Angajat extends Utilizator {
    String companie;
    public Angajat(String nume, String companie) {
        super(nume);
        this.companie = companie;
    }
}
