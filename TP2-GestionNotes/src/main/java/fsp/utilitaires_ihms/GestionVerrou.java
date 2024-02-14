package fsp.utilitaires_ihms;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author lbath
 */
public class GestionVerrou {
    /** constante contenant le nom du fichier utilisé pour le verrouillage 
     * de l'accès à l'application. */
    private static String NOM_VERROU = "verrou";
    
    /** Pose un verrou en créant un fichier nommé NOM_VERROU.*/
    public static void verrouiller() throws IOException {
        new FileWriter(new File(NOM_VERROU)).close();
    }

    /* Renvoie true si le verrou est posé, false sinon. */
    public static boolean verrouFerme() throws IOException {
        return (new File(NOM_VERROU).isFile());
    }

    /** Lève le verrou en supprimant le fichier NOM_VERROU. */
    public static void deVerrouiller() throws IOException {
        new File(NOM_VERROU).delete();
    }
}
