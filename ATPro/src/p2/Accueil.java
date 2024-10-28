package p2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Accueil {
    private String identifiant;

    public Accueil(String identifiant) {
        this.identifiant = identifiant;
    }

    public void afficherFenetre() {
        JFrame accueilFrame = new JFrame("Page d'accueil");
        accueilFrame.setSize(400, 400);
        accueilFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        accueilFrame.setLayout(new BoxLayout(accueilFrame.getContentPane(), BoxLayout.Y_AXIS));

        JLabel welcomeLabel = new JLabel("Bienvenue, " + identifiant);
        welcomeLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        JButton deconnexionButton = new JButton("Déconnexion");
        deconnexionButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        deconnexionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accueilFrame.dispose();
                MainApp.main(null); // Retour à la page de connexion
            }
        });

        JButton changePasswordButton = new JButton("Modifier le mot de passe");
        changePasswordButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModificationMotDePasse modification = new ModificationMotDePasse(identifiant);
                modification.afficherFenetre();
            }
        });

        accueilFrame.add(welcomeLabel);
        accueilFrame.add(deconnexionButton);
        accueilFrame.add(changePasswordButton);
        accueilFrame.setVisible(true);
    }
}
