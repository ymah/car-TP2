README
------
TP réalisé par Yaker Mahieddine et Forest Dylan

==========


Ce qui a été Fait :
   - Passerelle Rest pour parcourir, telecharger et televerser des fichiers.
   - La passerelle se connecte avec l'utilisateur par defaut : {id : mah,mdp:toto}

Pour executer et utiliser :
     - Lancer le serveur ftp.
     - Lancer la passerelle rest (starter)
     - aller sur http://127.0.0.1:8080


Problemes :
Malheuresement, il y a un soucis avec la partie Téléchargement/Téléversement.
En effet, on arrive a executer ces actions plusieurs fois, mais il est impossible de relancer un ls ou un cdup ou un pwd (le serveur renvoyant NULL).
Sinon, au delas de cela, le téléchargement et le Téléversement fonctionnent.




Le Jar, contient :
   - la javadoc du service
   - l'UML du projet.
   - les sources




Réalisation du TP :
            l'API Rest permet, via le fichier RequestFTPResource.java, de generer un service RESTfull, chaque methode repondant à une requete sur une uri précice.
            Ces methodes font appel à des methodes dans FTPService.java, qui elle, utilise le client ftp d'apache pour faire les requetes sur le serveur FTP crée durant le précedent TP.

