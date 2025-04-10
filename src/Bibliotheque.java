import java.io.*;
import java.util.*;

public class Bibliotheque {
    private List<Livre> livres = new ArrayList<>();

    public void ajouter(Livre livre) {
        livres.add(livre);
    }

    public boolean supprimer(Livre livre) {
        return livres.remove(livre);
    }

    public List<Livre> getLivres() {
        return livres;
    }

    public List<Livre> rechercher(String motCle) {
        List<Livre> resultats = new ArrayList<>();
        for (Livre l : livres) {
            if (l.getTitre().toLowerCase().contains(motCle.toLowerCase())) {
                resultats.add(l);
            }
        }
        return resultats;
    }

    public void sauvegarder(String fichier) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichier))) {
            for (Livre l : livres) {
                writer.write(l.getTitre() + ";" + l.getAuteur() + ";" + l.getAnnee() + ";" + l.getIsbn());
                writer.newLine();
            }
        }
    }

    public void charger(String fichier) throws IOException {
        File f = new File(fichier);
        if (!f.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] parts = ligne.split(";");
                if (parts.length == 4) {
                    Livre l = new Livre(parts[0], parts[1], Integer.parseInt(parts[2]), parts[3]);
                    livres.add(l);
                }
            }
        }
    }
}
