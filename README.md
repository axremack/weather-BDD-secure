# ♨ TP9 Java - Reprise du TP6 pour sécurisation

Le TP6 consiste en un programme exécutable via la ligne de commande permettant d'afficher la météo actuelle d'une ville donnée en argument. La récupération des informations se fait en base de données si des informations assez récentes sont déja stockées ou via le réseau sinon.

L'idée est d'accroitre la rapidité de la récupération et de l'affichage des données ainsi que d'éviter des appels réseaux constant (permettant une économie de ressource ainsi qu'une utilisation offline). Les données trop anciennes (+ d'un jour) sont supprimées de la base de données automatiquement.

Les informations météorologiques sont fournies par https://openweathermap.org/.

<span style="color:darkred">**Concernant la sécurisation, un rapport détaillé des changements effectués dans le code est disponible dans le fichier *RapportSécurisation.md*.**</span>

## ✨ Installation
Télécharger le projet ou cloner le dépôt en local.
Il suffit ensuite de l'importer dans IntelliJ ou d'ouvrir un terminal pour l'utiliser.

## 🚀 Utilisation
### Lancement du programme
Il faut compiler le projet puis l'éxécuter à l'aide de l'interface graphique d'IntelliJ.
Dans un terminal, à l'intérieur du projet, taper la commande suivante pour afficher les informations de la ville précisée :
```
/usr/lib/jvm/java-11-openjdk-amd64/bin/java -Dfile.encoding=UTF-8 -classpath /mnt/c/Users/axrem/Documents/ZZ3/Java/TP6/out/production/TP6:/mnt/c/Users/axrem/Downloads/sqlite-jdbc-3.32.3.2.jar:/mnt/c/Users/axrem/Documents/ZZ3/Java/gson-2.8.8.jar Main city_name
```

### Lancement des tests
Après avoir compiler le projet, faire un clic droit sur le dossier de test puis séléctionner "Run All Tests".

![](C:\Users\axrem\Documents\ZZ3\Java\TP4\resources\img.png)


## 📝 Détails sur le programme
Le fichier de base de données est weather.db.

### Utilité des classes
**Main.java** : lance le programme et récupère le nom de ville donné en argument pour en afficher la météo.<br/>
**WeatherFetcher.java** : interroge l'API de OpenWeatherMap pour récupérer les données de météo.<br/>
**CityWeather.java** : modélise l'objet construit suite au parsing des informations météorologiques récupérées.<br/>
**Temperature.java et Wind.java** : permettent de gérer la hiérarchie des tableaux du .json reprenant les informations météo.<br/>
**DBManager** : permet les opérations sur la base de données (création et suppression de table, insertion de données, recherche et affichage ordonné ou par défaut).

## ✅ Couverture des tests
- [x] Création de table (*testTableCreation*)
- [x] Insertion de données (*testValueInsertion*)
- [x] Insertion de données avec les mauvais types (*testInvalidValueInsertion*)
- [x] Insertion de données NULL (*testNullValueInsertion*)
- [x] Affichage par défaut (*testTableDisplay*)
- [x] Affichage ordonné par ville (*testTableDisplayOrderedByCity*)
- [x] Affichage ordonné par température (*testTableDisplayOrderedByTemperature*)
- [x] Affichage ordonné selon une colonne inexistante (*testTableDisplayOrderedByInvalid*)
- [x] Recherche de valeur dans la base de données (*testFinding*)
- [x] Succès de la récupération d'information d'une ville valide (*testGoodCity*)
- [x] Lancement d'exception lorsqu'une mauvaise ville est demandée (*testInvalidCity*)



