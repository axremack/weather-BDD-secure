# TP9 - Rapport de sécurité

Reprise du TP6 consistant à interroger une API de météo à base d'une entrée utilisateur pour préciser la ville. Le résultat de l'API est stocké en base de données et il y a un système de cache pour ne pas conserver des données trop vielles.





Ce qui etait déja fait : 
- 



Faciles : 

Principe 0-6 / FUNDAMENTALS-6 : Encapsulate ????
Principe 0-8 / FUNDAMENTALS-8 : Secure third-party code ????
Principe 1-2 / DOS-2 : Release resources in all cases


## ATTAQUE 1 - Guideline 3-1 / INJECT-1: Generate valid formatting
## Guideline 5-1 / INPUT-1: Validate inputs ???

### Descriptif de vulnérabilité 
Le programme prends en entrée un paramètre entrée par l'utilisateur : le nom de la ville

### Code original avec la vulnérabilité 

On donne un paramètre qui n'est pas une ville.

La réponse est une erreur SQL, il faudrait vérifier les problèmes avant pour éviter d'éventuelles injections.

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
            DBManager d = new DBManager(url);  
 			d.deleteOldData(); // Deleting data if too old  
 			boolean found = d.findInDB(args[0]);
			... 
		}
		...
	}
}

```

La valeur null est gérée (if args.length != 1 --> throws exception)





### Version corrigée avec corrections mises en avant 
```diff
public class Main {  
    public static void main(String[] args) {  
        String url = "jdbc:sqlite:src/Database/weather.db";  
		CityWeather weather = null;  
  
		if (args.length != 1) {  
    		System.err.println(...);  
 			throw new IllegalArgumentException();  
		}
		
		Pattern p;
        Matcher m;
+        // Regex searching for digit or special character except "-"
+        p = Pattern.compile("\\d");
+        m = p.matcher("Clermont");
+        if(m.find()) {
+            System.out.println("motif trouvé");
+        }

  
        try {  
            DBManager d = new DBManager(url);  
 			d.deleteOldData(); // Deleting data if too old  
 			boolean found = d.findInDB(args[0]);
			... 
		}
		...
	}
}

```

On va ajouter une regex pour vérifier la forme du nom de ville (éviter les chiffres et les caractères spéciaux sauf le "-").



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


