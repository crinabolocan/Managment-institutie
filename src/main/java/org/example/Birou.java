package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

public class Birou<E> {
    Queue<Cerere> coada = new LinkedList<>();

    ArrayList<Functionar<E>> functionari = new ArrayList<>();
    public void afiseazabirou()
    {
        System.out.println("Biroul are urmatoarele cereri:");
        for(int i = 0; i < coada.size(); i++)
        {
            System.out.println(coada.peek().data+" "+coada.peek().ora+" "+coada.peek().continut);
            coada.add(coada.remove());
            if(coada.size() == 0)
            {
                break;
            }
        }
    }

    public void retragereCerere(String nume, String data) {
        for (Cerere cer: coada) {
            System.out.println("NUME " + nume + "------" + (cer.continut.split(" ")[2] + cer.continut.split(" ")[3]).split(",")[0]);
            System.out.println("DATA " + data + "------" + cer.data + " " + cer.ora);
            if ((cer.continut.split(" ")[2] + " " + cer.continut.split(" ")[3]).split(",")[0].equals(nume) && (cer.data + " " + cer.ora).equals(data)) {
                coada.remove(cer);
                break;
            }
        }
    }
    public void output(String[] args, String tip)//afiseaza cereri in asteptare
    {
        try{
            String numeout = args[0];
            coada = ordonarecereri(coada);
            FileWriter fw = new FileWriter("./src/main/resources/output/" + numeout, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(tip + " - cereri in birou:" + "\n");
            for(int i = 0; i < coada.size(); i++)
            {
                bw.write(coada.peek().prioritate +" - "+ coada.peek().data + " "+ coada.peek().ora+" ");
                bw.write(coada.peek().continut+"\n");
                coada.add(coada.remove());
                if(coada.size() == 0)
                {
                    break;
                }
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    Queue<Cerere> ordonarecereri(Queue<Cerere> cereri) throws ParseException {
        Queue<Cerere> aux = new LinkedList<>();
        Queue<Cerere> aux2 = new LinkedList<>();
        int n = cereri.size();
        for (int j = 0; j < n;j++) {
            Cerere min = new Cerere(" ", "01-Dec-9998", "23:00:00", 0);
            int minIndex = -1;
            for (int i = 0; i < n - j; i++) {
                Date data1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(cereri.peek().data + " " +cereri.peek().ora);
                Date data2 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(min.data + " " + min.ora);
                if (cereri.peek().prioritate > min.prioritate) {
                    min = cereri.peek();
                    minIndex = i;
                }
                else if(cereri.peek().prioritate == min.prioritate)
                {
                    if(data1.compareTo(data2) < 0)
                    {
                        min = cereri.peek();
                        minIndex = i;
                    }
                }
                aux2.add(cereri.peek());
                cereri.add(cereri.remove());
            }
            cereri = new LinkedList<>();
            for (int k = 0; k < n - j; k++) {
                if (k != minIndex) {
                    cereri.add(aux2.remove());
                } else {
                    aux.add(aux2.remove());
                }
            }
            aux2 = new LinkedList<>();
        }
        return aux;
    }
}
