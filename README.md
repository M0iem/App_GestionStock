# 1) Structure globale

1. Création d’un seul fichier `GestionStock.java` contenant la **classe publique** et la méthode `main`.
   → On garde tout dans une seule classe parce que tu voulais **programmation structurée** (pas d’objets).

2. Utilisation de **variables/statics globales** (au niveau de la classe) pour représenter l’inventaire :

   * `static final int MAX = 100;` — capacité maximale.
   * Quatre tableaux parallèles :

     * `static int[] codesProduits = new int[MAX];`
     * `static String[] nomsProduits = new String[MAX];`
     * `static int[] quantites = new int[MAX];`
     * `static double[] prix = new double[MAX];`
   * `static int nbProduits = 0;` — nombre actuel d’éléments utilisés dans les tableaux.

**Pourquoi** : tableaux parallèles = chaque index `i` représente un produit (code/noms/quantité/prix).

---

# 2) Méthodes principales (méthodes statiques)

Pour respecter la consigne, toutes les opérations sont des **méthodes statiques**. Voici le rôle de chacune :

1. `ajouterProduit(int code, String nom, int quantiteProd, double prixUnit)`

   * Vérifie l’espace (`nbProduits < MAX`).
   * Vérifie code positif et **unicité** (appel à `trouverIndexParCode(code)`).
   * Vérifie quantité/prix >= 0.
   * Si OK : stocke les valeurs dans l’index `nbProduits` et incrémente `nbProduits++`.

2. `modifierProduit(int code, String nouveauNom, int nouvelleQuantite, double nouveauPrix)`

   * Trouve l’index du produit avec `trouverIndexParCode(code)`.
   * Si trouvé : remplace `nomsProduits[idx]`, `quantites[idx]`, `prix[idx]`.

3. `supprimerProduit(int code)`

   * Trouve l’index.
   * Décale **tous** les éléments suivants vers la gauche à partir de l’index trouvé :

     ```text
     for (i = idx; i < nbProduits-1; i++) {
         codes[i] = codes[i+1];
         noms[i] = noms[i+1];
         ...
     }
     ```
   * Décrémente `nbProduits--` et optionnellement « nettoie » la dernière case.
   * **Pourquoi décalage** : parce qu’on veut que les tableaux restent « synchrones » et sans trous.

4. `afficherProduits()`

   * Parcourt `i = 0 .. nbProduits-1` et affiche une ligne formatée pour chaque produit.
   * Affichage agréable avec `printf` (colonnes).

5. `rechercherProduit(String nomRecherche)`

   * Parcourt tous les noms et compare en insensible à la casse (`toLowerCase()`).
   * Affiche tous les correspondants (un nom peut apparaître plusieurs fois).

6. `calculerValeurStock()`

   * Somme `total += quantites[i] * prix[i]` pour `i = 0..nbProduits-1`.
   * Retourne la valeur totale (double).

---

# 3) Fonctions utilitaires

* `trouverIndexParCode(int code)` : renvoie l’index ou -1 si non trouvé. Utilisé partout pour modification/suppression/unicité.
* Fonctions de lecture sûres (pour éviter plantage par saisie) :

  * `lireEntier(String prompt)` : boucle jusqu’à obtenir un entier valide.
  * `lireDouble(String prompt)` : idem pour double.
  * `lireTexte(String prompt)` : demande jusqu’à ce que la chaîne ne soit pas vide.

**Pourquoi** : évite les `NumberFormatException` et garde l’interface console robuste.

---

# 4) Menu interactif dans `main`

* Boucle `while(true)` qui affiche le menu (1..6, 0 quitter).
* Lit le choix (string → parseInt) et `switch` sur ce choix.
* Pour chaque option, appelle la méthode correspondante et demande les paramètres via les fonctions de lecture.
* Pour quitter : `sc.close()` puis `return`.

**Pourquoi** : c’est ce qui rend le programme interactif dans la console (Eclipse Console).

---

# 5) Logique de validation et erreurs gérées

* **Inventaire plein** : message clair quand `nbProduits >= MAX`.
* **Code dupliqué** : on empêche l’ajout d’un code déjà existant.
* **Valeurs négatives** : quantité/prix doivent être >= 0.
* **Produit introuvable** : message quand modification/suppression sur un code inexistant.
* **Entrées non numériques** : on redemande grâce aux fonctions `lireEntier`/`lireDouble`.

---

# 6) Comportements particuliers à retenir

* **Suppression** : si tu supprimes l’élément d’index 0 (premier), tout le tableau est décalé à gauche et ancien index 1 devient index 0, etc.
* **Ordre des produits** : on conserve l’ordre d’insertion (sauf après suppression qui réarrange par décalage).
* **Recherche** : par défaut match exact (insensible à la casse). Si tu veux recherche partielle, remplace `equals(...)` par `contains(...)`.
* **Doublons de nom** : possible (plusieurs produits peuvent avoir même nom mais codes différents).

---

# 7) Tests à exécuter (et comment vérifier)

1. Ajouter 3 produits (codes distincts) → `afficherProduits()` doit montrer les 3.
2. Modifier le produit du milieu → vérifier que la ligne correspondante change.
3. Supprimer le premier → vérifier que l’ancien 2ème devient 1er.
4. Rechercher en écrivant le nom en majuscules/minuscules → doit trouver.
5. Essayer d’ajouter avec code déjà utilisé → message d’erreur.
6. Remplir jusqu’à `MAX` (ou simuler) → tester message inventaire plein.
7. Calculer valeur : vérifier `total = Σ quantité*prix`.

---

# 8) Comment reproduire dans Eclipse (rappel succinct)

1. File → New → Java Project → nom = `GestionStock`.
2. Dans `src` → New → Class → nom `GestionStock` (cocher main).
3. Coller le code complet (remplacer tout).
4. Run As → Java Application.
5. Dans Console, saisis les options et paramètres.

---

# 9) Améliorations faciles (si tu veux faire évoluer)

* **Recherche partielle** : `if (noms[i].toLowerCase().contains(cible))`
* **Tri** : trier les produits par nom ou valeur (implémenter un tri à l’aide du même principe de tableaux parallèles).
* **Sauvegarde/Chargement** : écrire/lecture depuis un fichier texte (CSV) pour persister entre exécutions.
* **Interface graphique** : plus tard, si tu veux swing/simple GUI (mais ça sort de la consigne POO).
* **Remplacer tableaux par ArrayList** (si POO acceptée plus tard) pour plus de flexibilité.

---

# 10) Erreurs fréquentes & comment les corriger

* `NullPointerException` : généralement lié à `nomsProduits[i]` si jamais utilisé hors plage; toujours utiliser `i < nbProduits`.
* `NumberFormatException` : vient d’un parseInt/parseDouble → solution : boucles de validation (déjà faites).
* Oublier d’incrémenter/décrémenter `nbProduits` → inventaire faux (vérifie toujours après ajout/suppression).
* Mauvais index lors du décalage → la boucle doit aller jusqu’à `nbProduits - 2` pour copier `i+1` dans `i`; j’ai utilisé `i < nbProduits - 1`.
