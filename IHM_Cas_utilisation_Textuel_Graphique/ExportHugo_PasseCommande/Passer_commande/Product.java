package Passer_commande;

public class Product {
    private int id;
    private String codeProduit;
    private int stockMag;
    private String designationProduit;
    private double prixPdt;

    public Product(int id, String codeProduit, int stockMag, String designationProduit, double prixPdt) {
        this.id = id;
        this.codeProduit = codeProduit;
        this.stockMag = stockMag;
        this.designationProduit = designationProduit;
        this.prixPdt = prixPdt;
    }

    public int getId() {
        return id;
    }

    public String getCodeProduit() {
        return codeProduit;
    }

    public int getStockMag() {
        return stockMag;
    }

    public String getDesignationProduit() {
        return designationProduit;
    }

    public double getPrixPdt() {
        return prixPdt;
    }

    public void setStockMag(int stockMag) {
        this.stockMag = stockMag;
    }
}
