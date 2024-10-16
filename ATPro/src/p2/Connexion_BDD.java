package p2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion_BDD {

    // Méthode pour obtenir une connexion à la base de données
    public static Connection getConnection() {
        // Informations de connexion
        String url = "jdbc:mysql://localhost:3306/projet_pro";  // URL complète
        String user = "root";  // Nom d'utilisateur MySQL
        String password = "";  // Mot de passe (vide ici)

        try {
            // Charger explicitement le pilote JDBC (optionnel selon la version de Java)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Obtenir la connexion
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Problème de connexion à la base de données : " + e.getMessage());
            return null;  // Ou relancer l'exception selon ta stratégie d'erreur
        } catch (ClassNotFoundException e) {
            System.out.println("Pilote JDBC non trouvé : " + e.getMessage());
            return null;
        }
    }
}