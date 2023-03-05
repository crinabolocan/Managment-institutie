package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Functionar<E> {

    String nume;

    ArrayList<Cerere> cereri_finalizate = new ArrayList<>();
    public Functionar(String nume) {
        this.nume = nume;
    }

    public void rezolvareCerere(Birou<E> birou, ArrayList<Utilizator> utilizatori) {
        Utilizator save_u = null;
        String data_cerere_rezolv = "";
        for (Utilizator u : utilizatori) {
            if (u.getNume().equals((birou.coada.peek().continut.split(" ")[2] + " " + birou.coada.peek().continut.split(" ")[3]).split(",")[0])) {
                data_cerere_rezolv = birou.coada.peek().data + " " + birou.coada.peek().ora;
                u.retragereCerere(birou.coada.peek().data + " " + birou.coada.peek().ora);
                u.cereri_finalizate.add(birou.coada.peek());
                save_u = u;
            }
        }
        cereri_finalizate.add(birou.coada.remove());

        try{
            FileWriter fw = new FileWriter("./src/main/resources/output/functionar_" + nume + ".txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            if (save_u != null) {
                bw.write(data_cerere_rezolv + " - " + save_u.getNume() + "\n");
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
