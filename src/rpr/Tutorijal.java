package rpr;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;


import org.w3c.dom.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class Tutorijal {

    public static ArrayList<Grad> ucitajGradove() {
        Scanner ulaz;
        ArrayList<Grad> gradovi = new ArrayList<>();
        try {
            ulaz = new Scanner(new FileReader("mjerenja.txt")).useDelimiter("[\\r,]");
        }
        catch (FileNotFoundException e) {
            System.out.println("Datoteka mjerenja.txt ne postoji ili se ne moze otvoriti.");
            System.out.println("Greska: " + e);
            return gradovi;
        }

        try {
            while (ulaz.hasNext()) {
                Grad grad = new Grad();
                double[] niz = new double[1000];
                String naziv = ulaz.next();
                int i = 0;
                while (ulaz.hasNextDouble()) {
                    niz[i] = Double.parseDouble(ulaz.next());
                    i++;
                    if (i == 1000) break;
                }
                grad.setBrojTemperatura(i);
                String naziv2 = naziv;
                grad.setNaziv(naziv);
                grad.setTemperature(niz);
                gradovi.add(grad);
            }

        }
        catch (Exception e) {
            System.out.println("Problem pri citanju podataka.");
            System.out.println("Greska: " + e);
        }
        finally {
            ulaz.close();
        }
        return gradovi;
    }

    public static UN ucitajXml2(ArrayList<Grad> gradovi) {
        UN unitedNations = null;

        try {
            XMLDecoder ulaz = new XMLDecoder(new FileInputStream("drzave.xml"));
            unitedNations = (UN) ulaz.readObject();
            ulaz.close();
        }
        catch (Exception e) {
            System.out.println("Greska: " + e);
        }

        for (Drzava d : unitedNations.getDrzave()) {
            for (int i = 0; i < gradovi.size(); i++) {
                if (d.getGlavniGrad().getNaziv().equals(gradovi.get(i).getNaziv())) {
                    d.getGlavniGrad().setTemperature(gradovi.get(i).getTemperature());
                }
            }
        }

        return unitedNations;
    }

    static  UN ucitajXml(ArrayList<Grad> listaGradova) throws Exception {
        listaGradova = ucitajGradove();
        UN UNdrzave = new UN();
        ArrayList<Drzava> drzave = new ArrayList<>();

        try {
            File datoteka = new File("drzave.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(datoteka);
            NodeList nodeList = doc.getElementsByTagName("drzava");



            for(int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == 1) {
                    Element eElement = (Element)node;
                    Drzava drzavaTemp = new Drzava();
                    drzavaTemp.setBrojStanovnika(Integer.parseInt(eElement.getAttribute("stanovnika")));
                    drzavaTemp.setNaziv(eElement.getElementsByTagName("naziv").item(0).getTextContent());
                    drzavaTemp.setJedinicaZaPovrsinu(eElement.getElementsByTagName("povrsina").item(0).getAttributes().getNamedItem("jedinica").getTextContent());
                    drzavaTemp.setPovrsina(Double.parseDouble(eElement.getElementsByTagName("povrsina").item(0).getTextContent()));


                    System.out.println("Broj stanovnika: " + eElement.getAttribute("stanovnika"));
                    System.out.println("Naziv drzave: " + eElement.getElementsByTagName("naziv").item(0).getTextContent());
                    System.out.println("Povrsina: " + eElement.getElementsByTagName("povrsina").item(0).getTextContent());
                    System.out.println("Jedinica: " + eElement.getElementsByTagName("povrsina").item(0).getAttributes().getNamedItem("jedinica").getTextContent());
                    System.out.println("Moneta: " + eElement.getElementsByTagName("moneta").item(0).getAttributes().getNamedItem("naziv").getTextContent());


                    NodeList lista1 = eElement.getElementsByTagName("glavnigrad");
                    Node tempNode = lista1.item(0);
                    if( tempNode.getNodeType() == Node.ELEMENT_NODE ){
                        Element element1 = (Element) tempNode;

                        Grad gradTemp = new Grad();
                        gradTemp.setNaziv(element1.getElementsByTagName("naziv").item(0).getTextContent());
                        gradTemp.setBrojStanovnika(Integer.parseInt(element1.getAttribute("stanovnika")));

                        Iterator it = listaGradova.iterator();
                        while(it.hasNext()){
                            Grad gradT = (Grad) it.next();
                            if(gradT.getNaziv().equals(gradTemp.getNaziv())){
                                gradTemp.setTemperature(gradT.getTemperature());
                                System.out.println("Tempreature: ");
                                double[] nizTemperatura = gradTemp.getTemperature();
                                for(int k = 0; k < nizTemperatura.length; k++){
                                    if(nizTemperatura[k] != 0)
                                        System.out.print(nizTemperatura[k] + ",");
                                }
                            }
                        }
                        drzavaTemp.setGlavniGrad(gradTemp);

                        System.out.println("\nIme glavnog grada: " + element1.getElementsByTagName("naziv").item(0).getTextContent());
                        System.out.println("Broj stanovnika glavnog grada: " + element1.getAttribute("stanovnika") + "\n");

                    }
                    drzave.add(drzavaTemp);
                }
            }
        } catch (Exception var9) {
            System.out.println("Izuzetak bacen!");
        }

        UNdrzave.setDrzave(drzave);
        return UNdrzave;
    }


    public static void zapisiXml(UN unitedNations) {

        try {
            XMLEncoder izlaz = new XMLEncoder(new FileOutputStream("un.xml"));
            izlaz.writeObject(unitedNations);
            izlaz.close();
        }
        catch (Exception e) {
            System.out.println("Greska: " + e);
        }
    }

    public static void main(String[] args) {
        ArrayList<Grad> gradovi = new ArrayList<>(ucitajGradove());
        for (Grad g : gradovi) {
            System.out.print(g.getNaziv() + " ");
            for (double x: g.getTemperature()) {
                System.out.print(x + " ");
            }
        }

        System.out.println();


        UN unitedNations= null;
        try {
            unitedNations = new UN(ucitajXml(gradovi));
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(Drzava d : unitedNations.getDrzave()){
            System.out.println(d.getNaziv()+"\nGlavni grad: "+d.getGlavniGrad().getNaziv()+"\nPovrsina: "+d.getPovrsina()+" "+d.getJedinicaZaPovrsinu());
            System.out.println();
        }

        zapisiXml(unitedNations);
    }
}
