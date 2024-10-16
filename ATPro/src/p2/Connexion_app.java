package p2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Connexion_app {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Créer la fenêtre principale
        JFrame frame = new JFrame("Page_Connexion");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLayout(new BorderLayout());

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Avatar
        ImageIcon avatarIcon = new ImageIcon("C:\\Users\\alexl\\Desktop\\Cours BTS 2ème année\\Ap pro\\Partie_2\\Icon_Connexion.png"); // Ajoutez votre propre image ici
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

                // Si tous les champs sont valides
                if (valid) {
                    JOptionPane.showMessageDialog(frame, "Connexion réussie !");
                }
            }
        });

        mainPanel.add(loginButton);

        // Ajouter le panneau principal à la fenêtre
        frame.add(mainPanel, BorderLayout.CENTER);

        // Rendre la fenêtre visible
        frame.setVisible(true);
    }
}