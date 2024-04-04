package org.fsp.utilitaires_ihms;


import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;

/**
 * - URI, USERNAME, PASSWORD : constantes d'accès à la bd.
 * - ConnexionBD() : Construit une instance et tente de créer une session de connexion avec la bd qu'elle mémorise en cas de succès dans l'attribut session. Renvoie l'exception levée par les opérations Neo4j Java Driver d'ouvertue de session en cas d'échec.
 * - session : Session de connexion avec la bd si la connexion réussit.
 * - instance : Unique instance de ConexionBD.
 * - getInstance() : Appelle ConnexionBD() pour construire une instance si non déjà créée. Lève ConnexionBDException si la connexion échoue. Méthode thread safe (synchronized).
 */
public class ConnexionBD {
    private String URI = "bolt://localhost:7687";

    private String USERNAME = "neo4j";

    private String PASSWORD = "rootroot";

    private static Session session;

    private ConnexionBD instance;

    private ConnexionBD() {
        Driver driver = null;
        try {
            driver = GraphDatabase.driver(URI, AuthTokens.basic(USERNAME, PASSWORD));
            session = driver.session();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Session getSession() {
        if (session == null) {
            new ConnexionBD();
        }
        return session;
    }
}
