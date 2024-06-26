# TP1

## Question 2

Créer une base de données dans la console MONGOSH de MongoDb Compass

``` mongosh
use gestion_notes
```

## Question 3

Définir les requêtes permettant de construire les documents montrés par l'annexe en page 2 (données
utilisées dans QD-TP2) et les exécuter dans la console MONGOSH de MongoDb Compass. Cliquer sur " … /
Reload Data" en haut à gauche de l’ihm de MongoDb Compass pour rafraîchir les données et visualiser les
documents créés dans la console MONGOSH.

``` mongosh
db.matieres.insertOne({_id:"Mathématiques", idsEtudiantsInscrits : ["cartieraxel ", "barthezenzo", " manoukianlea"]})
db.matieres.insertOne({_id : "Programmation Java",idsEtudiantsInscrits : ["cartieraxel ", "barthezenzo", "manoukianlea","petruzzitony", "dutroncmaxime", "montalbanmaeva"]})
db.matieres.insertOne({_id : "Conception Objet",idsEtudiantsInscrits: ["cartieraxel", "barthezenzo", "manoukianlea","petruzzitony", "dutroncmaxime", "montalbanmaeva"]})
db.matieres.insertOne({_id : "Anglais", idsEtudiantsInscrits : ["petruzzitony", "dutroncmaxime", "montalbanmaeva"]})

db.enseignants.insertOne({_id : "dupondpaul", motDePasse : "123", nom : "DUPOND", prenom : "Paul", idsMmatieresEnseignees : ["Mathématiques"]})
db.enseignants.insertOne({_id : "machinremy", motDePasse : "456", nom : "MACHIN", prenom : "Rémy", idsMmatieresEnseignees : ["Programmation Java", "Conception Objet"]})
db.enseignants.insertOne({_id : "johnsonboris", motDePasse : "789", nom : "JOHNSON", prenom : "Boris", idsMmatieresEnseignees : ["Anglais"]})

db.etudiants.insertMany([
    {_id : "cartieraxel", motDePasse : "1", nom : "CARTIER", prenom : "Axel"},
    {_id : "barthezenzo", motDePasse : "2", nom : "BARTHEZ", prenom : "Enzo"},
    {_id : "manoukianlea", motDePasse : "3", nom : "MANOUKIAN", prenom : "Léa"},
    {_id : "petruzzitony", motDePasse : "4", nom : "PETRUZZI", prenom : "Tony"},
    {_id : "dutroncmaxime", motDePasse : "5", nom : "DUTRONC", prenom : "Maxime"},
    {_id : "montalbanmaeva", motDePasse : "6", nom : "MONTALBAN", prenom : "Maéva"}
])

```

## Question 4

Définir les requêtes suivantes et les exécuter dans la console MONGOSH :

- Requête qui recherche tous les enseignants.
- Requête qui recherche tous les étudiants.
- Requête qui recherche toutes les matières.
- Requête qui recherche les matières enseignées par un enseignant, par exemple par "MACHIN Rémy".
- Recherche qui recherche les étudiants inscrits dans une matière, par exemple en Programmation Java.

``` mongosh
db.enseignants.find()
db.etudiants.find()
db.matieres.find()
db.enseignants.find({nom : "MACHIN", prenom : "Rémy"}, {"idsMmatieresEnseignees": true})
db.matieres.find({_id : "Programmation Java"}, {"idsEtudiantsInscrits": true})
```

## Question 5

Manipulation des notes d'un étudiant : pour stocker les notes d'un étudiant, on utilise un tableau de la
forme : [{"matiere":<id_matiere>, "valeur":<valeur>}, ...] associé à la clé "notes" dans le document
représentant l'étudiant.
Saisir les requêtes suivantes pour l'étudiant "CARTIER Axel", en partant de la situation où l'étudiant n'a
aucune note enregistrée et les exécuter dans la console MONGOSH :

- Requête qui enregistre la note 15 en "Mathématiques".
- Requête qui enregistre en plus la note 12 en "Programmation Java".
- Requête qui remplace la note en "Mathématiques" par 17.
- Requête qui supprime la note en "Mathématiques".

``` mongosh
db.etudiants.updateOne({nom:"CARTIER", prenom:"Axel"}, {$set:{notes:[{"matiere":"Mathématiques", "valeur":15}]}})
db.etudiants.updateOne({nom:"CARTIER", prenom:"Axel"}, {$addToSet:{notes:{"matiere":"Programmation Java", "valeur":12}}})
db.etudiants.updateOne({nom:"CARTIER", prenom:"Axel", "notes.matiere":"Mathématiques"}, {$set:{"notes.$.valeur":17}})
db.etudiants.updateOne({nom:"CARTIER", prenom:"Axel"}, {$pull:{"notes":{"matiere": "Mathématiques"}}})
```
