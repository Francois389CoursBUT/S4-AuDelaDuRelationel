package fsp.donnees;

import java.util.ArrayList;

public class Matiere  {
    
    private String _id;   
    private ArrayList<String> idsEtudiantsInscrits ;

    public String getId() {
        return _id;
    }

    public ArrayList<String> getIdsEtudiantsInscrits() {
        return idsEtudiantsInscrits;
    }
}
