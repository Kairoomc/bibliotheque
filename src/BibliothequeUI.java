import com.formdev.flatlaf.FlatDarkLaf;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class BibliothequeUI extends JFrame {

    private Bibliotheque biblio = new Bibliotheque();
    private DefaultListModel<Livre> model = new DefaultListModel<>();
    private JList<Livre> listeLivres = new JList<>(model);
    private static final String FICHIER = "data.txt";

    public BibliothequeUI() {
        super(" Bibliothèque Moderne");

        try { UIManager.setLookAndFeel(new FlatDarkLaf()); } catch (Exception e) { }

        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        try {
            biblio.charger(FICHIER);
            for (Livre l : biblio.getLivres()) model.addElement(l);
        } catch (IOException e) {
            System.out.println("Erreur chargement fichier.");
        }

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JPanel ajoutPanel = new JPanel(new GridBagLayout());
        ajoutPanel.setBorder(new EmptyBorder(20, 50, 20, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField tfTitre = new JTextField(20);
        JTextField tfAuteur = new JTextField(20);
        JTextField tfAnnee = new JTextField(10);
        JTextField tfIsbn = new JTextField(15);
        JButton btnAjout = new JButton("➕ Ajouter");

        gbc.gridx = 0; gbc.gridy = 0; ajoutPanel.add(new JLabel("Titre"), gbc);
        gbc.gridx = 1; ajoutPanel.add(tfTitre, gbc);
        gbc.gridx = 0; gbc.gridy = 1; ajoutPanel.add(new JLabel("Auteur"), gbc);
        gbc.gridx = 1; ajoutPanel.add(tfAuteur, gbc);
        gbc.gridx = 0; gbc.gridy = 2; ajoutPanel.add(new JLabel("Année"), gbc);
        gbc.gridx = 1; ajoutPanel.add(tfAnnee, gbc);
        gbc.gridx = 0; gbc.gridy = 3; ajoutPanel.add(new JLabel("ISBN"), gbc);
        gbc.gridx = 1; ajoutPanel.add(tfIsbn, gbc);
        gbc.gridx = 1; gbc.gridy = 4; ajoutPanel.add(btnAjout, gbc);

        btnAjout.addActionListener(e -> {
            try {
                Livre l = new Livre(tfTitre.getText(), tfAuteur.getText(), Integer.parseInt(tfAnnee.getText()), tfIsbn.getText());
                biblio.ajouter(l);
                model.addElement(l);
                tfTitre.setText(""); tfAuteur.setText(""); tfAnnee.setText(""); tfIsbn.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur de saisie.");
            }
        });

        JPanel listePanel = new JPanel(new BorderLayout(10, 10));
        listePanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JTextField tfRecherche = new JTextField();
        JButton btnSupprimer = new JButton("- Supprimer sélection");

        tfRecherche.setToolTipText("Tape un titre pour chercher...");
        tfRecherche.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void update() {
                String query = tfRecherche.getText();
                model.clear();
                for (Livre l : biblio.rechercher(query)) model.addElement(l);
            }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { update(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { update(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { update(); }
        });

        btnSupprimer.addActionListener(e -> {
            Livre selected = listeLivres.getSelectedValue();
            if (selected != null) {
                biblio.supprimer(selected);
                model.removeElement(selected);
            }
        });

        listeLivres.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(listeLivres);
        listePanel.add(tfRecherche, BorderLayout.NORTH);
        listePanel.add(scroll, BorderLayout.CENTER);
        listePanel.add(btnSupprimer, BorderLayout.SOUTH);

        tabs.add("+ Ajouter", ajoutPanel);
        tabs.add("° Liste", listePanel);

        add(tabs, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    biblio.sauvegarder(FICHIER);
                } catch (IOException ex) {
                    System.out.println("Erreur sauvegarde.");
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BibliothequeUI::new);
    }
}
