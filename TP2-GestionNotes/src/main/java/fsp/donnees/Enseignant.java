package fsp.donnees;

import java.util.ArrayList;

public class Enseignant extends Utilisateur {

    private ArrayList<String> idsMmatieresEnseignees;


    public ArrayList<String> getIdsMatieresEnseignees() {
        return idsMmatieresEnseignees;
    }

    @Override
    public String toString() {
        return super.toString().substring(0, super.toString().length() - 1) +
                ", idsMatieresEnseignees=" + idsMmatieresEnseignees +
                '}';
    }

}
