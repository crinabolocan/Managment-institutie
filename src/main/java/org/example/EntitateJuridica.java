package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class EntitateJuridica extends Utilizator {
    String nume;
    String reprezentant;
    public EntitateJuridica(String nume, String reprezentant) {
        super(nume);
        this.reprezentant = reprezentant;
    }
    public void setReprezentant(String reprezentant) {
        this.reprezentant = reprezentant;
    }
    public void afiseazaentitate(String[] args) {
        try{
            String numeout = args[0];
            FileWriter fw = new FileWriter("./src/main/resources/output/" + numeout, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(reprezentant + " - cereri in asteptare:" + "\n");
            bw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
