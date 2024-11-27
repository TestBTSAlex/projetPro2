package Passer_commande;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockManagementApp extends JFrame {
    private JTextField searchField;
    private JPanel productListPanel;
    private JPanel cartPanel;
    private JLabel cartCountLabel;
    private List<Product> products;
    private Cart cart;

    public StockManagementApp() {
        setTitle("Gestion de Stock");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 600);
        getContentPane().setLayout(new BorderLayout(10, 10));
        cart = new Cart();
        products = loadProductsFromDatabase(); // Charger les produits depuis la base de données

        // Barre de recherche
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setFont(new Font("Arial", Font.PLAIN, 16));
        searchField.setText("Recherchez un produit...");
        searchField.setForeground(Color.GRAY); // Couleur grise pour le texte de suggestion

        // Ajouter un FocusListener pour gérer le comportement du texte de suggestion
        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                // Effacer le texte de suggestion lorsqu'on clique sur le champ
                if (searchField.getText().equals("Recherchez un produit...")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK); // Texte en noir quand l'utilisateur tape
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                // Remettre le texte de suggestion si le champ est vide après avoir perdu le focus
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Recherchez un produit...");
                    searchField.setForeground(Color.GRAY); // Texte en gris pour indiquer le placeholder
                }
            }
        });
        
        JButton searchButton = new JButton("Rechercher");
        searchButton.setFont(new Font("Arial", Font.PLAIN, 14));
        searchButton.addActionListener(e -> updateProductList(searchField.getText()));

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        // Liste des produits
        productListPanel = new JPanel();
        productListPanel.setLayout(new BoxLayout(productListPanel, BoxLayout.Y_AXIS));
        updateProductList(""); // Afficher tous les produits initialement

        JScrollPane productScrollPane = new JScrollPane(productListPanel);
        productScrollPane.setPreferredSize(new Dimension(500, 400));
        productScrollPane.setBorder(BorderFactory.createTitledBorder("Produits"));

        // Panier
        cartPanel = new JPanel();
        cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.Y_AXIS));
        cartPanel.setBorder(BorderFactory.createTitledBorder("Panier"));
        
        cartCountLabel = new JLabel("Nombre de produits : 0");
        cartCountLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        JButton orderButton = new JButton("Commander");
        orderButton.setFont(new Font("Arial", Font.BOLD, 14));
        orderButton.setBackground(new Color(34, 139, 34));
        orderButton.setForeground(Color.WHITE);
        orderButton.addActionListener(e -> confirmOrder());

        JButton cancelButton = new JButton("Annuler");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setBackground(new Color(220, 20, 60));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(e -> clearCart());

        cartPanel.add(cartCountLabel);
        cartPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        cartPanel.add(orderButton);
        cartPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        cartPanel.add(cancelButton);

        // Ajouter les composants au JFrame
        getContentPane().add(searchPanel, BorderLayout.NORTH);
        getContentPane().add(productScrollPane, BorderLayout.CENTER);
        getContentPane().add(cartPanel, BorderLayout.EAST);

        setVisible(true);
    }

    // Charger les produits depuis la base de données
    private List<Product> loadProductsFromDatabase() {
        List<Product> productList = new ArrayList<>();
        String query = "SELECT * FROM produit";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String codeProduit = resultSet.getString("codeProduit");
                int stockMag = resultSet.getInt("stockMag");
                String designationProduit = resultSet.getString("designationProduit");
                double prixPdt = resultSet.getDouble("prixPdt");

                Product product = new Product(id, codeProduit, stockMag, designationProduit, prixPdt);
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des produits.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        return productList;
    }

    private void updateProductList(String searchQuery) {
        productListPanel.removeAll();
        for (Product product : products) {
            if (product.getDesignationProduit().toLowerCase().contains(searchQuery.toLowerCase())) {
                JPanel productItem = new JPanel(new BorderLayout(10, 10));
                productItem.setBorder(new EmptyBorder(5, 5, 5, 5));

                JLabel nameLabel = new JLabel(product.getDesignationProduit() + " - " + product.getPrixPdt() + "€ (Stock : " + product.getStockMag() + ")");
                nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));

                JTextField quantityField = new JTextField(5);
                quantityField.setFont(new Font("Arial", Font.PLAIN, 16));
                quantityField.setHorizontalAlignment(JTextField.CENTER);

                JButton addButton = new JButton("+");
                addButton.setFont(new Font("Arial", Font.BOLD, 14));
                addButton.addActionListener(e -> addToCart(product, quantityField));

                productItem.add(nameLabel, BorderLayout.WEST);
                productItem.add(quantityField, BorderLayout.CENTER);
                productItem.add(addButton, BorderLayout.EAST);

                productListPanel.add(productItem);
            }
        }
        productListPanel.revalidate();
        productListPanel.repaint();
    }

    private void addToCart(Product product, JTextField quantityField) {
        try {
            int quantity = Integer.parseInt(quantityField.getText());
            if (quantity > product.getStockMag()) {
                JOptionPane.showMessageDialog(this, "Stock insuffisant pour " + product.getDesignationProduit(), "Erreur", JOptionPane.ERROR_MESSAGE);
            } else {
                cart.addProduct(product, quantity);
                updateCartPanel();
                quantityField.setText("");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer une quantité valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateCartPanel() {
        cartPanel.removeAll();
        cartCountLabel.setText("Nombre de produits : " + cart.getTotalProducts());
        cartPanel.add(cartCountLabel);

        for (CartItem item : cart.getItems()) {
            JLabel itemLabel = new JLabel(item.getProduct().getDesignationProduit() + " x" + item.getQuantity());
            itemLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            cartPanel.add(itemLabel);
        }

        JButton orderButton = new JButton("Commander");
        orderButton.addActionListener(e -> confirmOrder());

        JButton cancelButton = new JButton("Annuler");
        cancelButton.addActionListener(e -> clearCart());

        cartPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        cartPanel.add(orderButton);
        cartPanel.add(cancelButton);

        cartPanel.revalidate();
        cartPanel.repaint();
    }

    private void confirmOrder() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            for (CartItem item : cart.getItems()) {
                String updateQuery = "UPDATE produit SET stockMag = stockMag - ? WHERE id = ?";
                try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
                    statement.setInt(1, item.getQuantity());
                    statement.setInt(2, item.getProduct().getId());
                    statement.executeUpdate();
                }
            }
            JOptionPane.showMessageDialog(this, "Commande confirmée.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            cart.clear();
            updateCartPanel();
            products = loadProductsFromDatabase(); // Recharger les produits pour actualiser les stocks
            updateProductList(searchField.getText());
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la confirmation de la commande.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearCart() {
        cart.clear();
        updateCartPanel();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new StockManagementApp());
    }
}
