package p2;

public class MainApp {

    public static void main(String[] args) {
        Connexion_app connexionApp = new Connexion_app();
        connexionApp.afficherFenetreConnexion();
    }

    // Méthode pour afficher la fenêtre d'accueil
    public static void afficherFenetreAccueil(String identifiant) {
        Accueil accueil = new Accueil(identifiant);
        accueil.afficherFenetre();
    }
}