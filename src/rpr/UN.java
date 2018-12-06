package rpr;

import java.io.Serializable;
import java.util.ArrayList;

public class UN implements Serializable {

    private ArrayList<Drzava> drzave=new ArrayList<>();

    public UN() {
    }

    public UN(ArrayList<Drzava> drzave) {
        setDrzave(drzave);
    }

    public UN(UN unitedNations) {
        this.setDrzave(unitedNations.getDrzave());
    }

    public ArrayList<Drzava> getDrzave() {
        return drzave;
    }

    public void setDrzave(ArrayList<Drzava> drzave) {
        this.drzave = drzave;
    }
}
