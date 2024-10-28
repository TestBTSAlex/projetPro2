package p2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ModificationMotDePasse {
    private String identifiant;

    public ModificationMotDePasse(String identifiant) {
        this.identifiant = identifiant;
    }

    public void afficherFenetre() {
        JFrame frame = new JFrame("Modification du mot de passe");
        frame.setSize(400, 300);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel oldPasswordLabel = new JLabel("Ancien mot de passe:");
        JPasswordField oldPasswordField = new JPasswordField(20);
        JLabel newPasswordLabel = new JLabel("Nouveau mot de passe:");
        JPasswordField newPasswordField = new JPasswordField(20);

        JButton updateButton = new JButton("Mettre à jour");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String oldPassword = new String(oldPasswordField.getPassword());
                String newPassword = new String(newPasswordField.getPassword());

                if (modifierMotDePasse(identifiant, oldPassword, newPassword)) {
                    JOptionPane.showMessageDialog(frame, "Mot de passe mis à jour avec succès.");
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Échec de la mise à jour du mot de passe.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.add(oldPasswordLabel);
        frame.add(oldPasswordField);
        frame.add(newPasswordLabel);
        frame.add(newPasswordField);
        frame.add(updateButton);

        frame.setVisible(true);
    }

    private boolean modifierMotDePasse(String identifiant, String oldPassword, String newPassword) {
        String query = "UPDATE salarie SET motDePasse = ? WHERE identifiant = ? AND motDePasse = ?";

        try (Connection conn = Connexion_BDD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, newPassword);
            stmt.setString(2, identifiant);
            stmt.setString(3, oldPassword);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
