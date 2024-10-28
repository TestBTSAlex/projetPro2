package p2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Connexion_app {

    // Méthode principale pour afficher la fenêtre de connexion
    public void afficherFenetreConnexion() {
        // Créer la fenêtre principale de connexion
        JFrame frame = new JFrame("Page de Connexion");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLayout(new BorderLayout());

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Avatar
        ImageIcon avatarIcon = new ImageIcon("C:\\Users\\alexl\\Desktop\\Cours BTS 2ème année\\Ap pro\\Partie_2\\Icon_Connexion.png");
        JLabel avatarLabel = new JLabel(avatarIcon);
        avatarLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(avatarLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Espace vertical

        // Champ Identifiant
        JLabel identifiantLabel = new JLabel("Saisissez votre identifiant:");
        identifiantLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField identifiantField = new JTextField(20);
        identifiantField.setMaximumSize(new Dimension(Integer.MAX_VALUE, identifiantField.getPreferredSize().height));
        JLabel identifiantErrorLabel = new JLabel(" ");
        identifiantErrorLabel.setForeground(Color.RED);
        identifiantErrorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(identifiantLabel);
        mainPanel.add(identifiantField);
        mainPanel.add(identifiantErrorLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Espace vertical

        // Champ Mot de passe
        JLabel passwordLabel = new JLabel("Saisissez votre mot de passe:");
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, passwordField.getPreferredSize().height));
        JLabel passwordErrorLabel = new JLabel(" ");
        passwordErrorLabel.setForeground(Color.RED);
        passwordErrorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);
        mainPanel.add(passwordErrorLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Espace vertical

        // Bouton Se connecter
        JButton loginButton = new JButton("Se connecter");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setBackground(new Color(81, 106, 246)); // Couleur du bouton
        loginButton.setForeground(Color.WHITE); // Couleur du texte du bouton

        // Ajouter l'action de vérification lors du clic sur le bouton
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean valid = true;

                // Réinitialiser les messages d'erreur
                identifiantErrorLabel.setText(" ");
                passwordErrorLabel.setText(" ");

                // Vérification du champ Identifiant
                if (identifiantField.getText().trim().isEmpty()) {
                    identifiantErrorLabel.setText("Erreur : Champ identifiant vide.");
                    valid = false;
                }

                // Vérification du champ Mot de passe
                if (new String(passwordField.getPassword()).trim().isEmpty()) {
                    passwordErrorLabel.setText("Erreur : Champ mot de passe vide.");
                    valid = false;
                }

                // Si tous les champs sont valides, vérifier dans la base de données
                if (valid) {
                    String identifiant = identifiantField.getText().trim();
                    String password = new String(passwordField.getPassword()).trim();

                    // Vérifier les identifiants dans la base de données
                    if (verifierIdentifiantsDansBDD(identifiant, password)) {
                        JOptionPane.showMessageDialog(frame, "Connexion réussie !");
                        MainApp.afficherFenetreAccueil(identifiant); // Ouvre la fenêtre d'accueil
                        frame.dispose(); // Ferme la fenêtre de connexion
                    } else {
                        JOptionPane.showMessageDialog(frame, "Erreur : Identifiant ou mot de passe incorrect.", "Erreur de connexion", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        mainPanel.add(loginButton);

        // Ajouter le panneau principal à la fenêtre
        frame.add(mainPanel, BorderLayout.CENTER);

        // Rendre la fenêtre visible
        frame.setVisible(true);
    }

    // Méthode pour vérifier les identifiants dans la base de données
    private boolean verifierIdentifiantsDansBDD(String identifiant, String password) {
        boolean connexionReussie = false;

        // Requête SQL pour vérifier les identifiants
        String query = "SELECT * FROM salarie WHERE identifiant = ? AND motDePasse = ?";

        // Utiliser la méthode getConnection() de Connexion_BDD pour obtenir la connexion
        try (Connection conn = Connexion_BDD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Assigner les valeurs saisies aux paramètres de la requête
            stmt.setString(1, identifiant);
            stmt.setString(2, password);

            // Exécuter la requête
            ResultSet rs = stmt.executeQuery();

            // Si une correspondance est trouvée, la connexion est réussie
            if (rs.next()) {
                connexionReussie = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connexionReussie;
    }
}
