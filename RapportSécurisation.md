# TP9 - Rapport de sécurité

Reprise du TP6 consistant à interroger une API de météo à base d'une entrée utilisateur pour préciser la ville. Le résultat de l'API est stocké en base de données et il y a un système de cache pour ne pas conserver des données trop vielles.





Ce qui etait déja fait : 
- 



Faciles : 

Principe 0-6 / FUNDAMENTALS-6 : Encapsulate ????
Principe 0-8 / FUNDAMENTALS-8 : Secure third-party code ????
Principe 1-2 / DOS-2 : Release resources in all cases


## ATTAQUE 1 - Guideline 3-1 / INJECT-1: Generate valid formatting

### Descriptif de vulnérabilité 
Le programme prend en entrée un paramètre entrée par l'utilisateur : le nom de la ville. Aucun contrôle sur le format de l'entrée n'est fait.

### Code original avec la vulnérabilité
En donnant un paramètre qui n'est pas une ville (à base de chiffres par exemple), la réponse est une erreur SQL. Il faudrait vérifier les problèmes avant pour éviter d'éventuelles injections.

Le code responsable est : 
```Java
public class Main {  
    public static void main(String[] args) {  
        String url = "jdbc:sqlite:src/Database/weather.db";
        CityWeather weather = null;  
        
        if (args.length != 1) {
            System.err.println(...);
            throw new IllegalArgumentException();
        }
  
        try {
            // Appel en base de donnée et traitement
        }
        // Gestion des exceptions
    }
}
```

*<u>Note</u> : La valeur null est gérée.*

### Version corrigée
On a ajouté une regex pour vérifier le format du nom de ville : on empêche la présence de chiffres et de caractères spéciaux, excepté le "-". Une exception est lancée si le nom entré ne correspond pas au format attendu.

```diff
public class Main {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:src/Database/weather.db";
        CityWeather weather = null;
        
+        Pattern p;
+        p = Pattern.compile("[^a-zA-Z!-]"); // Regex searching for digit or special character except "-"

        try {
+            if (args.length != 1 || p.matcher(args[0]).find()) {
+                throw new IllegalArgumentException();
+            }
            // Appel en base de donnée et traitement
        }
        // Gestion des exceptions
    }
}
```




## ATTAQUE 2 - Principe 2-1 / CONFIDENTIAL-1 : Purge sensitive information from exceptions
### Descriptif de vulnérabilité : 
Lors d'une mauvaise ville entrée en paramètre, une exception est affichée à l'utilisateur. Ca peut donner des informations à un attaquant.


### Code original avec la vulnérabilité : 

![[Pasted image 20211129184513.png]]

Les exceptions ont un contenu assez vague mais qui donne déja des informations.


### Version corrigée avec corrections mises en avant
On pourrait, en production, afficher seulement un message générique "Erreur". Le mieux serait de mettre une variable permettant d'adapter la précision des message à l'environnement.


## ATTAQUE 3 - Principe 3-2 / INJECT-2 : Avoid dynamic SQL
### Descriptif de vulnérabilité


### Code original avec la vulnérabilité


### Version corrigée avec corrections mises en avant


## ATTAQUE 4 - Principe 3-9 / INJECT-9 : Prevent injection of exceptional floating point values ???
### Descriptif de vulnérabilité


### Code original avec la vulnérabilité


### Version corrigée avec corrections mises en avant


## ATTAQUE 5
### Descriptif de vulnérabilité


### Code original avec la vulnérabilité


### Version corrigée avec corrections mises en avant


## ATTAQUE 6
### Descriptif de vulnérabilité


### Code original avec la vulnérabilité


### Version corrigée avec corrections mises en avant


