public class Livre {
    private String titre;
    private String auteur;
    private int annee;
    private String isbn;

    public Livre(String titre, String auteur, int annee, String isbn) {
        this.titre = titre;
        this.auteur = auteur;
        this.annee = annee;
        this.isbn = isbn;
    }

    public String getTitre() { return titre; }
    public String getAuteur() { return auteur; }
    public int getAnnee() { return annee; }
    public String getIsbn() { return isbn; }

    @Override
    public String toString() {
        return titre + " par " + auteur + " (" + annee + "), ISBN: " + isbn;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Livre)) return false;
        Livre autre = (Livre) obj;
        return this.isbn.equalsIgnoreCase(autre.isbn);
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();
    }
}
