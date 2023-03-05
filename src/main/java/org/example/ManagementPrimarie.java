package org.example;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ManagementPrimarie {
    public static void main(String[] args) {
        ArrayList<Utilizator> utilizator = new ArrayList<>();
        String[] utilizator_cereri = new String[100];
        Birou<Angajat> birouangajati = new Birou<>();
        Birou<Persoana> biroupersoane = new Birou<>();
        Birou<Pensionar> biroupensionari = new Birou<>();
        Birou<Elev> birouelevi = new Birou<>();
        Birou<EntitateJuridica> birouentitati = new Birou<>();
        ArrayList<Functionar> functionari = new ArrayList<>();
        try {
            File file = new File("./src/main/resources/input/" + args[0]);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(";");
                if (parts[0].equals("adauga_utilizator")) {
                    if (parts[1].equals(" angajat")) {
                        utilizator.add(new Angajat(parts[2].substring(1), parts[3].substring(1)));
                        //birouangajati.adauga(new Angajat(parts[2].substring(1), parts[3].substring(1)), String utilizator_cereri);
                    } else if (parts[1].equals(" elev")) {
                        utilizator.add(new Elev(parts[2].substring(1), parts[3].substring(1)));
                        //birouelevi.adauga((Elev) utilizator.get(utilizator.size() - 1));
                    } else if (parts[1].equals(" entitate juridica")) {
                        utilizator.add(new EntitateJuridica(parts[2].substring(1), parts[3].substring(1)));
                        //birouentitati.adauga((EntitateJuridica) utilizator.get(utilizator.size() - 1));
                    } else if (parts[1].equals(" pensionar")) {
                        utilizator.add(new Pensionar(parts[2].substring(1)));
                        //biroupensionari.adauga((Pensionar) utilizator.get(utilizator.size() - 1));
                    } else if (parts[1].equals(" persoana")) {
                        utilizator.add(new Persoana(parts[2].substring(1)));
                        //biroupersoane.adauga((Persoana) utilizator.get(utilizator.size() - 1));
                    }
                } else if (parts[0].equals("cerere_noua")) {
                    Utilizator utilizatorCurent = null;
                    for (int i = 0; i < utilizator.size(); i++) {
                        if (utilizator.get(i).getNume().equals(parts[1].substring(1))) {
                            utilizatorCurent = utilizator.get(i);
                            for (int l = 0; l < utilizator_cereri.length; l++) {
                                if (utilizator_cereri[i] == null) {
                                    utilizator_cereri[i] = parts[2].substring(1);
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    String data = parts[3].split(" ")[1];
                    String ora = parts[3].split(" ")[2];
                    String[] enum0 = parts[2].split(" ");
                    String enum3 = enum0[0];
                    String aux = enum0[0];
                    for (int i = 1; i < enum0.length; i++) {
                        enum3 = enum3 + enum0[i];
                        aux = aux + " " + enum0[i];
                    }
                    Cerere.tipCerere tip = Cerere.tipCerere.valueOf(enum3);
                    int prioritate = Integer.parseInt(parts[4].split(" ")[1]);
                    assert utilizatorCurent != null;
                    int rc = 0;
                    if(utilizatorCurent instanceof Angajat)
                        rc = utilizatorCurent.crearecerere(aux, tip, prioritate, data, ora, utilizatorCurent, args, birouangajati);
                    else if(utilizatorCurent instanceof Elev)
                        rc = utilizatorCurent.crearecerere(aux, tip, prioritate, data, ora, utilizatorCurent, args, birouelevi);
                    else if(utilizatorCurent instanceof EntitateJuridica)
                        rc = utilizatorCurent.crearecerere(aux, tip, prioritate, data, ora, utilizatorCurent, args, birouentitati);
                    else if(utilizatorCurent instanceof Pensionar) {
                        rc = utilizatorCurent.crearecerere(aux, tip, prioritate, data, ora, utilizatorCurent, args, biroupensionari);
                    }
                    else if(utilizatorCurent instanceof Persoana) {
                        rc = utilizatorCurent.crearecerere(aux, tip, prioritate, data, ora, utilizatorCurent, args, biroupersoane);
                    }

                    if (rc == -1)
                        continue;
                }
                else if(parts[0].equals("retrage_cerere")) {
                    Utilizator u_aux = null;
                    for (int i = 0; i < utilizator.size(); i++)
                    {
                        if(utilizator.get(i).getNume().equals(parts[1].substring(1)))
                        {
                            u_aux = utilizator.get(i);
                            utilizator.get(i).retragereCerere(parts[2].substring(1));
                            break;
                        }
                    }
                    if (u_aux instanceof Angajat) {
                        birouangajati.retragereCerere(parts[1].substring(1), parts[2].substring(1));
                    }
                    else if (u_aux instanceof Elev) {
                        birouelevi.retragereCerere(parts[1].substring(1), parts[2].substring(1));
                    }
                    else if (u_aux instanceof EntitateJuridica) {
                        birouentitati.retragereCerere(parts[1].substring(1), parts[2].substring(1));
                    }
                    else if (u_aux instanceof Pensionar) {
                        biroupensionari.retragereCerere(parts[1].substring(1), parts[2].substring(1));
                    }
                    else if (u_aux instanceof Persoana) {
                        biroupersoane.retragereCerere(parts[1].substring(1), parts[2].substring(1));
                    }
                }
                else if(parts[0].equals("rezolva_cerere"))
                {
                    if(parts[1].equals(" angajat"))
                    {
                        for (Functionar f: functionari) {
                            if (f.nume.equals(parts[2].substring(1))) {
                                f.rezolvareCerere(birouangajati, utilizator);
                                break;
                            }
                        }
                    }
                    else if(parts[1].equals(" elev"))
                    {
                        for (Functionar f: functionari) {
                            if (f.nume.equals(parts[2].substring(1))) {
                                f.rezolvareCerere(birouelevi, utilizator);
                                break;
                            }
                        }
                    }
                    else if(parts[1].equals(" entitate juridica"))
                    {
                        for (Functionar f: functionari) {
                            if (f.nume.equals(parts[2].substring(1))) {
                                f.rezolvareCerere(birouentitati, utilizator);
                                break;
                            }
                        }
                    }
                    else if(parts[1].equals(" pensionar"))
                    {
                        for (Functionar f: functionari) {
                            if (f.nume.equals(parts[2].substring(1))) {
                                f.rezolvareCerere(biroupensionari, utilizator);
                                break;
                            }
                        }
                    }
                    else if(parts[1].equals(" persoana"))
                    {
                        for (Functionar f: functionari) {
                            if (f.nume.equals(parts[2].substring(1))) {
                                f.rezolvareCerere(biroupersoane, utilizator);
                                break;
                            }
                        }
                    }
                }
                else if (parts[0].equals("afiseaza_cereri_in_asteptare")) {
                    for (int i = 0; i < utilizator.size(); i++) {
                        if (utilizator.get(i).getNume().equals(parts[1].substring(1))) {
                            utilizator.get(i).output(args, utilizator.get(i));
                        }
                    }

                }
                else if(parts[0].equals("afiseaza_cereri"))
                {
                    System.out.println("Cereri:");
                    if(parts[1].equals(" angajat"))
                    {
                        birouangajati.afiseazabirou();
                        birouangajati.output(args, parts[1].substring(1));
                    }
                    else if(parts[1].equals(" elev"))
                    {
                        birouelevi.afiseazabirou();
                        birouelevi.output(args, parts[1].substring(1));
                    }
                    else if(parts[1].equals(" entitate juridica"))
                    {
                        birouentitati.afiseazabirou();
                        birouentitati.output(args, parts[1].substring(1));
                    }
                    else if(parts[1].equals(" pensionar"))
                    {
                        biroupensionari.afiseazabirou();
                        biroupensionari.output(args, parts[1].substring(1));
                    }
                    else if(parts[1].equals(" persoana"))
                    {
                        System.out.println("PERSOANA");
                        biroupersoane.afiseazabirou();
                        biroupersoane.output(args, parts[1].substring(1));
                    }
                }
                else if(parts[0].equals("adauga_functionar")){
                    if(parts[1].equals(" angajat"))
                    {
                        Functionar<Angajat> angajatFunctionar = new Functionar<>(parts[2].substring(1));
                        //System.out.println("Functionarul " + angajatFunctionar.nume + " a fost adaugat cu succes!");
                        functionari.add(angajatFunctionar);
                        birouangajati.functionari.add(angajatFunctionar);
                    }
                    else if(parts[1].equals(" elev"))
                    {
                        Functionar<Elev> elevFunctionar = new Functionar<Elev>(parts[2].substring(1));
                        functionari.add(elevFunctionar);
                        birouelevi.functionari.add(elevFunctionar);
                    }
                    else if(parts[1].equals(" entitate juridica"))
                    {
                        Functionar<EntitateJuridica> entitateJuridicaFunctionar = new Functionar<EntitateJuridica>(parts[2].substring(1));
                        functionari.add(entitateJuridicaFunctionar);
                        birouentitati.functionari.add(entitateJuridicaFunctionar);
                    }
                    else if(parts[1].equals(" pensionar"))
                    {
                        Functionar<Pensionar> pensionarFunctionar = new Functionar<Pensionar>(parts[2].substring(1));
                        functionari.add(pensionarFunctionar);
                        biroupensionari.functionari.add(pensionarFunctionar);
                    }
                    else if(parts[1].equals(" persoana"))
                    {
                        Functionar<Persoana> persoanaFunctionar = new Functionar<Persoana>(parts[2].substring(1));
                        functionari.add(persoanaFunctionar);
                        biroupersoane.functionari.add(persoanaFunctionar);
                    }
                }
                else if(parts[0].equals("afiseaza_cereri_finalizate")) {
                    for (Utilizator u : utilizator) {
                        if (u.getNume().equals(parts[1].substring(1))) {
                            u.afiseazaCereriFinalizate(args, u);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
