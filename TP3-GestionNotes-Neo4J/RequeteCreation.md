# NoSQL TP3

## Exercice 2

Saisir dans la zone de commande Cypher les requêtes permettant de construire le graphe décrivant
les données suivantes et faire afficher le graphe obtenu.

- Contraintes (à spécifier en Cypher) : existence et unicité des intitulés des matières et unicité des identifiants des utilisateurs.
- Matières : Mathématiques, Programmation Java, Conception Objet, Anglais.
- Enseignants :

| Nom | Prénom | Identifiant | Mot de passe | Matières enseignées |
|-----|--------|-------------|--------------|---------------------|
| DUPOND | Paul | dupondpaul | 123 | Mathématiques |
| MACHIN | Rémy | machinremy | 456 | Programmation Java, Conception Objet |
| JOHNSON | Boris | johnsonboris | 789 | Anglais |

- Etudiants (la propriété note ne doit pas être saisie tant que sa valeur n'est pas connue) :

| Nom | Prénom | Identifiant | Mot de passe | Matières suivies |
|-----|--------|-------------|--------------|------------------|
| CARTIER | Axel | cartieraxel | 1 | Mathématiques, Programmation Java, Conception Objet |
| BARTHEZ | Enzo | barthezenzo | 2 | Mathématiques, Programmation Java, Conception Objet |
| MANOUKIAN | Léa | manoukianlea | 3 | Mathématiques, Programmation Java, Conception Objet |
| PETRUZZI | Tony | petruzzitony | 4 | Programmation Java, Conception Objet, Anglais |
| DUTRONC | Maxime | dutroncmaxime | 5 | Programmation Java, Conception Objet, Anglais |
| MONTALBAN | Maéva | montalbanmaeva | 6 | Programmation Java, Conception Objet, Anglais |

```cypher
// Créer les contraintes
CREATE CONSTRAINT FOR (m:Matiere) REQUIRE m.intitule IS UNIQUE;
CREATE CONSTRAINT FOR (u:Utilisateur) REQUIRE u.identifiant IS UNIQUE;

// Créer les matières
CREATE (:Matiere {intitule: 'Mathématiques'});
CREATE (:Matiere {intitule: 'Programmation Java'});
CREATE (:Matiere {intitule: 'Conception Objet'});
CREATE (:Matiere {intitule: 'Anglais'});

//Créer les enseignants
CREATE (:Enseignant {identifiant: 'dupondpaul', matieres: ['Mathématiques']});
CREATE (:Enseignant {identifiant: 'machinremy', matieres: ['Programmation Java', 'Conception Objet']});
CREATE (:Enseignant {identifiant: 'johnsonboris', matieres: ['Anglais']});

// Créer les étudiants
CREATE (:Etudiant {identifiant: 'cartieraxel', matieres: ['Mathématiques', 'Programmation Java', 'Conception Objet']});
CREATE (:Etudiant {identifiant: 'barthezenzo', matieres: ['Mathématiques', 'Programmation Java', 'Conception Objet']});
CREATE (:Etudiant {identifiant: 'manoukianlea', matieres: ['Mathématiques', 'Programmation Java', 'Conception Objet']});
CREATE (:Etudiant {identifiant: 'petruzzitony', matieres: ['Programmation Java', 'Conception Objet', 'Anglais']});
CREATE (:Etudiant {identifiant: 'dutroncmaxime', matieres: ['Programmation Java', 'Conception Objet', 'Anglais']});
CREATE (:Etudiant {identifiant: 'montalbanmaeva', matieres: ['Programmation Java', 'Conception Objet', 'Anglais']});

//Création des relations
//Etudiant-Note->Matière
// Créer les relations entre les étudiants et les matières
MATCH (e:Etudiant {identifiant: 'cartieraxel'}), (m:Matiere {intitule: 'Mathématiques'})
CREATE (e)-[:NOTE {note: 15}]->(m);

MATCH (e:Etudiant {identifiant: 'cartieraxel'}), (m:Matiere {intitule: 'Programmation Java'})
CREATE (e)-[:NOTE {note: 14}]->(m);

MATCH (e:Etudiant {identifiant: 'cartieraxel'}), (m:Matiere {intitule: 'Conception Objet'})
CREATE (e)-[:NOTE {note: 16}]->(m);

// Répétez ces commandes pour chaque étudiant et chaque matière qu'il suit, en changeant l'identifiant de l'étudiant, l'intitulé de la matière et la note.
```

