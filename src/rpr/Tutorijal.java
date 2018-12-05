package rpr;

import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Tutorijal {

    public static ArrayList<Grad> ucitajGradove() {
        Scanner ulaz;
        ArrayList<Grad> gradovi=new ArrayList<>();
        try {
            ulaz = new Scanner(new FileReader("mjerenja.txt")).useDelimiter("[\\r,]");
        } catch (FileNotFoundException e) {
            System.out.println("Datoteka mjerenja.txt ne postoji ili se ne moze otvoriti.");
            System.out.println("Greska: " + e);
            return gradovi;
        }

        try {
            while (ulaz.hasNext()) {
                Grad grad = new Grad();
                double [] niz=new double[1000];
                String naziv = ulaz.next();
                int i=0;
                while (ulaz.hasNextDouble()) {
                    niz[i] = Double.parseDouble(ulaz.next());
                    i++;
                    if(i==1000) break;
                }
                String naziv2=naziv;
                grad.setNaziv(naziv);
                grad.setTemperature(niz);
                gradovi.add(grad);
            }

        } catch (Exception e) {
            System.out.println("Problem pri citanju podataka.");
            System.out.println("Greska: " + e);
        } finally {
            ulaz.close();
        }
        return gradovi;
    }

    public static UN ucitajXml(ArrayList<Grad> gradovi){
        UN unitedNations= null;

        try{
            XMLDecoder ulaz= new XMLDecoder(new FileInputStream("drzave.xml"));
            unitedNations=(UN) ulaz.readObject();
            ulaz.close();
        }catch(Exception e){
            System.out.println("Greska: "+e);
        }

        for(Drzava d: unitedNations.getDrzave()){
            for(int i=0; i<gradovi.size();i++){
                if(d.getGlavniGrad().getNaziv().equals(gradovi.get(i).getNaziv())){
                    d.getGlavniGrad().setTemperature(gradovi.get(i).getTemperature());
                }
            }
        }

        return unitedNations;
    }

    public static void main(String[] args) {
        ArrayList<Grad> gradovi=new ArrayList<>(ucitajGradove());
        for(Grad g: gradovi){
            System.out.print(g.getNaziv()+" ");
            for(double x: g.getTemperature()){
                System.out.print(x+" ");
            }
        }


        for(Grad g: gradovi){
            System.out.print(g.getNaziv());
            //System.out.println();
        }
    }
}
