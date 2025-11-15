import java.util.Scanner;

public class GestionStock {
    // Constante de taille max
    static final int MAX = 100;

    // Tableaux parallèles
    static int[] codesProduits = new int[MAX];
    static String[] nomsProduits = new String[MAX];
    static int[] quantites = new int[MAX];
    static double[] prix = new double[MAX];

    // Nombre actuel de produits
    static int nbProduits = 0;

    // Scanner global
    static Scanner sc = new Scanner(System.in);

    // ----------------- Méthodes demandées -----------------

    // Ajouter un produit
    static void ajouterProduit(int code, String nom, int quantiteProd, double prixUnit) {
        if (nbProduits >= MAX) {
            System.out.println("Erreur : inventaire plein (max " + MAX + " produits).");
            return;
        }
        if (code <= 0) {
            System.out.println("Erreur : le code doit être un entier positif.");
            return;
        }
        if (trouverIndexParCode(code) != -1) {
            System.out.println("Erreur : un produit avec ce code existe déjà.");
            return;
        }
        if (quantiteProd < 0 || prixUnit < 0) {
            System.out.println("Erreur : quantité et prix doivent être >= 0.");
            return;
        }

        codesProduits[nbProduits] = code;
        nomsProduits[nbProduits] = nom;
        quantites[nbProduits] = quantiteProd;
        prix[nbProduits] = prixUnit;
        nbProduits++;
        System.out.println("Produit ajouté avec succès.");
    }

    // Modifier un produit existant (par code)
    static void modifierProduit(int code, String nouveauNom, int nouvelleQuantite, double nouveauPrix) {
        int idx = trouverIndexParCode(code);
        if (idx == -1) {
            System.out.println("Produit introuvable (code " + code + ").");
            return;
        }
        if (nouvelleQuantite < 0 || nouveauPrix < 0) {
            System.out.println("Erreur : quantité et prix doivent être >= 0.");
            return;
        }
        nomsProduits[idx] = nouveauNom;
        quantites[idx] = nouvelleQuantite;
        prix[idx] = nouveauPrix;
        System.out.println("Produit modifié avec succès.");
    }

    // Supprimer un produit (par code)
    static void supprimerProduit(int code) {
        int idx = trouverIndexParCode(code);
        if (idx == -1) {
            System.out.println("Produit introuvable (code " + code + ").");
            return;
        }
        // Décaler les éléments
        for (int i = idx; i < nbProduits - 1; i++) {
            codesProduits[i] = codesProduits[i + 1];
            nomsProduits[i] = nomsProduits[i + 1];
            quantites[i] = quantites[i + 1];
            prix[i] = prix[i + 1];
        }
        // Nettoyer la dernière case (optionnel)
        codesProduits[nbProduits - 1] = 0;
        nomsProduits[nbProduits - 1] = null;
        quantites[nbProduits - 1] = 0;
        prix[nbProduits - 1] = 0.0;
        nbProduits--;
        System.out.println("Produit supprimé avec succès.");
    }

    // Afficher tous les produits
    static void afficherProduits() {
        if (nbProduits == 0) {
            System.out.println("Aucun produit en stock.");
            return;
        }
        System.out.println("Liste des produits (" + nbProduits + "):");
        System.out.println("------------------------------------------------------------");
        System.out.printf("%-8s | %-20s | %-10s | %-10s%n", "Code", "Nom", "Quantité", "Prix U.");
        System.out.println("------------------------------------------------------------");
        for (int i = 0; i < nbProduits; i++) {
            System.out.printf("%-8d | %-20s | %-10d | %-10.2f%n",
                    codesProduits[i], nomsProduits[i], quantites[i], prix[i]);
        }
        System.out.println("------------------------------------------------------------");
    }

    // Rechercher par nom (insensible à la casse, recherche exacte)
    static void rechercherProduit(String nomRecherche) {
        boolean trouve = false;
        String cible = nomRecherche.trim().toLowerCase();
        for (int i = 0; i < nbProduits; i++) {
            if (nomsProduits[i] != null && nomsProduits[i].toLowerCase().equals(cible)) {
                System.out.println("Produit trouvé :");
                System.out.printf("Code: %d, Nom: %s, Quantité: %d, Prix U.: %.2f%n",
                        codesProduits[i], nomsProduits[i], quantites[i], prix[i]);
                trouve = true;
                // on continue pour afficher d'éventuels doublons portant le même nom
            }
        }
        if (!trouve) {
            System.out.println("Aucun produit trouvé pour le nom \"" + nomRecherche + "\".");
        }
    }

    // Calculer la valeur totale du stock
    static double calculerValeurStock() {
        double total = 0.0;
        for (int i = 0; i < nbProduits; i++) {
            total += quantites[i] * prix[i];
        }
        return total;
    }

    // ----------------- Méthodes utilitaires -----------------

    // Trouver l'index d'un produit par son code (retourne -1 si pas trouvé)
    static int trouverIndexParCode(int code) {
        for (int i = 0; i < nbProduits; i++) {
            if (codesProduits[i] == code) return i;
        }
        return -1;
    }

    // Lire un entier avec contrôle simple
    static int lireEntier(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String line = sc.nextLine().trim();
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide. Veuillez saisir un entier.");
            }
        }
    }

    // Lire un double avec contrôle simple
    static double lireDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String line = sc.nextLine().trim();
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide. Veuillez saisir un nombre (ex: 12.5).");
            }
        }
    }

    // Lire une chaîne non vide
    static String lireTexte(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            if (!line.isEmpty()) return line;
            System.out.println("Entrée vide. Veuillez saisir du texte.");
        }
    }

    // ----------------- Menu principal -----------------
    public static void main(String[] args) {
        while (true) {
            System.out.println();
            System.out.println("----- Gestion de Stock -----");
            System.out.println("1. Ajouter un produit");
            System.out.println("2. Modifier un produit");
            System.out.println("3. Supprimer un produit");
            System.out.println("4. Afficher la liste des produits");
            System.out.println("5. Rechercher un produit (par nom)");
            System.out.println("6. Calculer la valeur totale du stock");
            System.out.println("0. Quitter");
            System.out.print("Choisissez une option: ");

            String choixStr = sc.nextLine().trim();
            int choix;
            try {
                choix = Integer.parseInt(choixStr);
            } catch (NumberFormatException e) {
                System.out.println("Choix invalide. Veuillez entrer un numéro.");
                continue;
            }

            switch (choix) {
                case 1:
                    // Ajouter
                    int code = lireEntier("Code produit (entier positif) : ");
                    String nom = lireTexte("Nom du produit : ");
                    int qte = lireEntier("Quantité : ");
                    double prixU = lireDouble("Prix unitaire : ");
                    ajouterProduit(code, nom, qte, prixU);
                    break;

                case 2:
                    // Modifier
                    int codeMod = lireEntier("Code du produit à modifier : ");
                    if (trouverIndexParCode(codeMod) == -1) {
                        System.out.println("Produit introuvable.");
                    } else {
                        String nouveauNom = lireTexte("Nouveau nom : ");
                        int nouvelleQte = lireEntier("Nouvelle quantité : ");
                        double nouveauPrix = lireDouble("Nouveau prix unitaire : ");
                        modifierProduit(codeMod, nouveauNom, nouvelleQte, nouveauPrix);
                    }
                    break;

                case 3:
                    // Supprimer
                    int codeSup = lireEntier("Code du produit à supprimer : ");
                    supprimerProduit(codeSup);
                    break;

                case 4:
                    // Afficher
                    afficherProduits();
                    break;

                case 5:
                    // Rechercher
                    String nomRecherche = lireTexte("Nom du produit à rechercher : ");
                    rechercherProduit(nomRecherche);
                    break;

                case 6:
                    // Calculer valeur
                    double total = calculerValeurStock();
                    System.out.printf("Valeur totale du stock : %.2f%n", total);
                    break;

                case 0:
                    System.out.println("Au revoir !");
                    sc.close();
                    return;

                default:
                    System.out.println("Option inconnue. Veuillez réessayer.");
            }
        }
    }
}
