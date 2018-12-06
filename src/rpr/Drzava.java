package rpr;

import java.io.Serializable;

public class Drzava implements Serializable {

    private String naziv;
    private int brojStanovnika;
    private double povrsina;
    private String jedinicaZaPovrsinu;
    private Grad glavniGrad;

    public Drzava() {

    }

    public Drzava(String naziv, int brojStanovnika, double povrsina, String jedinicaZaPovrsinu, Grad glavniGrad) {
        setNaziv(naziv);
        setBrojStanovnika(brojStanovnika);
        setPovrsina(povrsina);
        setJedinicaZaPovrsinu(jedinicaZaPovrsinu);
        setGlavniGrad(glavniGrad);
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getBrojStanovnika() {
        return brojStanovnika;
    }

    public void setBrojStanovnika(int brojStanovnika) {
        this.brojStanovnika = brojStanovnika;
    }

    public double getPovrsina() {
        return povrsina;
    }

    public void setPovrsina(double povrsina) {
        this.povrsina = povrsina;
    }

    public String getJedinicaZaPovrsinu() {
        return jedinicaZaPovrsinu;
    }

    public void setJedinicaZaPovrsinu(String jedinicaPovrsine) {
        this.jedinicaZaPovrsinu = jedinicaPovrsine;
    }

    public Grad getGlavniGrad() {
        return glavniGrad;
    }

    public void setGlavniGrad(Grad glavniGrad) {
        this.glavniGrad = glavniGrad;
    }
}
