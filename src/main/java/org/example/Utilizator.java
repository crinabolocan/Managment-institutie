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

abstract public class Utilizator {
    String nume;

    ArrayList<Cerere> cereri_finalizate = new ArrayList<>();
    public Utilizator() {
    }

    public Utilizator(String nume) {
        this.nume = nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }
    public String getNume() {
        return nume;
    }
    String scriereCerere(String[] args, String tipaux, Cerere.tipCerere tip, Utilizator utilizator) throws Exceptie {
        if (utilizator instanceof Persoana) {
            if (tip == Cerere.tipCerere.inlocuirebuletin) {
                return "- Subsemnatul " + utilizator.getNume() + ", va rog sa-mi aprobati urmatoarea solicitare:" + tipaux;
            } else if (tip == Cerere.tipCerere.inlocuirecarnetdesofer) {
                return "- Subsemnatul " + utilizator.getNume() + ", va rog sa-mi aprobati urmatoarea solicitare:" + tipaux;
            }
            else {
                throw new Exceptie(args, "Utilizatorul de tip persoana nu poate inainta o cerere de tip" + tipaux);
            }
        } else if (utilizator instanceof Angajat) {
            if (tip == Cerere.tipCerere.inlocuirebuletin) {
                return "- Subsemnatul " + utilizator.getNume() + ", angajat la compania " + ((Angajat) utilizator).companie + ", va rog sa-mi aprobati urmatoarea solicitare:" + tipaux;
            } else if (tip == Cerere.tipCerere.inlocuirecarnetdesofer) {
                return "- Subsemnatul " + utilizator.getNume() + ", angajat la compania " + ((Angajat) utilizator).companie + ", va rog sa-mi aprobati urmatoarea solicitare:" + tipaux;
            } else if (tip == Cerere.tipCerere.inregistrarevenitsalarial) {
                return "- Subsemnatul " + utilizator.getNume() + ", angajat la compania " + ((Angajat) utilizator).companie + ", va rog sa-mi aprobati urmatoarea solicitare:" + tipaux;
            }
            else {
                throw new Exceptie(args, "Utilizatorul de tip angajat nu poate inainta o cerere de tip" + tipaux);
            }
        } else if (utilizator instanceof Pensionar) {
            if (tip == Cerere.tipCerere.inlocuirebuletin) {
                return "- Subsemnatul " + utilizator.getNume() + ", va rog sa-mi aprobati urmatoarea solicitare:" + tipaux;
            } else if (tip == Cerere.tipCerere.inlocuirecarnetdesofer) {
                return "- Subsemnatul " + utilizator.getNume() + ", va rog sa-mi aprobati urmatoarea solicitare:" + tipaux;
            } else if (tip == Cerere.tipCerere.inregistrarecupoanedepensie) {
                return "- Subsemnatul " + utilizator.getNume() + ", va rog sa-mi aprobati urmatoarea solicitare:" + tipaux;
            }
            else {
                throw new Exceptie(args, "Utilizatorul de tip pensionar nu poate inainta o cerere de tip" + tipaux);
            }
        } else if (utilizator instanceof Elev) {
            if (tip == Cerere.tipCerere.inlocuirebuletin) {
                return "- Subsemnatul " + utilizator.getNume() + ", elev la scoala " + ((Elev) utilizator).scoala + ", va rog sa-mi aprobati urmatoarea solicitare:" + tipaux;
            } else if (tip == Cerere.tipCerere.inlocuirecarnetdeelev) {
                return "- Subsemnatul " + utilizator.getNume() + ", elev la scoala " + ((Elev) utilizator).scoala + ", va rog sa-mi aprobati urmatoarea solicitare:" + tipaux;
            }
            else {
                throw new Exceptie(args, "Utilizatorul de tip elev nu poate inainta o cerere de tip" + tipaux);
            }
        }
        else if (utilizator instanceof EntitateJuridica)
        {
            if (tip == Cerere.tipCerere.creareactconstitutiv) {
                return "- Subsemnatul " + ((EntitateJuridica) utilizator).reprezentant + ", reprezentant legal al companiei " + utilizator.getNume() + ", va rog sa-mi aprobati urmatoarea solicitare:" + tipaux;
            }
            else if (tip == Cerere.tipCerere.reinnoireautorizatie)
            {
                return "- Subsemnatul " + ((EntitateJuridica) utilizator).reprezentant + ", reprezentant legal al companiei " + utilizator.getNume() + ", va rog sa-mi aprobati urmatoarea solicitare:" + tipaux;
            }
            else {
                throw new Exceptie(args, "Utilizatorul de tip entitate juridica nu poate inainta o cerere de tip" + tipaux);
            }
        }
        return "Nu exista cerere pentru acest tip de utilizator";
    }
    Queue<Cerere> cereri;
    public int crearecerere(String tipaux, Cerere.tipCerere tip, int prioritate, String data, String ora, Utilizator utilizator, String[] args, Birou birou) {
        Cerere cerere = null;
        String continut = "";
        try {
            continut = scriereCerere(args,tipaux,tip,utilizator);
        } catch (Exceptie e) {
            return -1;
        }
        cerere = new Cerere(continut, data, ora, prioritate);
        if(cereri == null)
        {
            Queue<Cerere> aux = new LinkedList<>();
            aux.add(cerere);
            cereri = aux;
            System.out.println("Cererea dumneavoastra a fost inregistrata cu succes!");
            birou.coada.add(cerere);
        }else {
            cereri.add(cerere);
            System.out.println("Cererea dumneavoastra a fost inregistrata cu succes!");
            birou.coada.add(cerere);
        }
        return 1;
    }


    public void retragereCerere(String data) {
        for (int i = 0;i < cereri.size(); i++) {
            if ((cereri.peek().data+ " " +cereri.peek().ora).equals(data)) {
                System.out.println("REMOVED");
                cereri.remove();
            }
            if (cereri.size() == 0) {
                break;
            }
            cereri.add(cereri.remove());
        }
    }

    public void afiseazaCereriFinalizate(String[] args, Utilizator u)
    {
        try{
            String numeout = args[0];
            FileWriter fw = new FileWriter("./src/main/resources/output/" + numeout, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(nume + " - cereri in finalizate:" + "\n");
            for(Cerere cerere : cereri_finalizate)
            {
                bw.write(cerere.data + " "+ cerere.ora+" ");
                bw.write(cerere.continut+"\n");
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void output(String[] args, Utilizator u)//afiseaza cereri in asteptare
    {
        try{
            String numeout = args[0];
            cereri = ordonarecereri(cereri);
            FileWriter fw = new FileWriter("./src/main/resources/output/" + numeout, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(nume + " - cereri in asteptare:" + "\n");
            for(Cerere cerere : cereri)
            {
                bw.write(cerere.data + " "+ cerere.ora+" ");
                bw.write(cerere.continut+"\n");
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void afiseazaentitate(String[] args) {

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
                if (data1.compareTo(data2) < 0) {
                    min = cereri.peek();
                    minIndex = i;
                }
                else if(data1.compareTo(data2) == 0)
                {
                    if(cereri.peek().prioritate > min.prioritate)
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

class Exceptie extends Exception
{
    public Exceptie(String[] args, String mesaj)
    {
        try{
            String numeout = args[0];
            FileWriter fw = new FileWriter("./src/main/resources/output/" + numeout,true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(mesaj+"\n");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}






