package rpr;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Tutorijal {

    public static void main(String[] args) {

    }

    public static ArrayList<Grad> ucitajGradove() {
        Scanner ulaz;
        ArrayList<Grad> gradovi=new ArrayList<>();
        try {
            ulaz = new Scanner(new FileReader("mjerenja.txt")).useDelimiter("[,]");
        } catch (FileNotFoundException e) {
            System.out.println("Datoteka mjerenja.txt ne postoji ili se ne moze otvoriti.");
            System.out.println("Greska: " + e);
            return gradovi;
        }

        try {
            int vel = 0;
            while (ulaz.hasNext()) {
                Grad grad = new Grad();
                double [] niz=new double[1000];
                String naziv = ulaz.nextLine();
                int i=0;
                while (ulaz.hasNextDouble()) {
                    niz[i] = ulaz.nextDouble();
                    i++;
                }
                grad.setNaziv(naziv);
                grad.setTemperature(niz);
                gradovi.add(grad);
                vel++;
                if(vel==1000) break;
            }

        } catch (Exception e) {
            System.out.println("Problem pri citanju podataka.");
            System.out.println("Greska: " + e);
        } finally {
            ulaz.close();
        }
        return gradovi;
    }
}
