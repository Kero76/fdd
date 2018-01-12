Rapport du projet de fouille de données
================
*Arthur Breuneval
Nicolas Gille
Grégoire Pommier*

---

## Démarche

Le but de ce projet est de réaliser une chaıne de traitement de données allant de l'extraction à l'interpretation des données transformées. 
Les données sont récupérée depuis [pubmed]( https://www.ncbi.nlm.nih.gov/pubmed/), ils portent sur 5 sujets différents ([Asthme](https://fr.wikipedia.org/wiki/Asthme), [Hypertension](https://fr.wikipedia.org/wiki/Hypertension_art%C3%A9rielle) , [Maladie Rare](https://fr.wikipedia.org/wiki/Maladie_rare) , [ Diabete Sucré ](https://fr.wikipedia.org/wiki/Diab%C3%A8te_sucr%C3%A9) et [Infections Nosocomiales](https://fr.wikipedia.org/wiki/Infection_nosocomiale) ), et vont former 5 corpus. Chaques corpus va être éclaté en un ensemble de 100.000 articles, désigné par leur PMID. De chaque article, on garde le résumé, et les mots clés, que l'on garde de coté pour l'instant. Sur les résumés, on applique un algorithme de Tag, afin d'en extraire les termes et de les étiquetés. Ici, on a deux Algorithme, TreeTagger, qui est plus dirigé vers les terme de langage courrant, et GeniaTagger, qui est spécialisé dans les terme medicaux, notament géniques. Ces données taggées vont ensuite être passer par YaTea, afin d'en extraire les termes pertinents. A ce point, il est possible de comparer ses termes avec ceux du mesh, afin de calculer pertinence, et rappel de ces derniers. Enfin, on extrait de ces données des règles d'associations à l'aide l'algorithme APriori.

## Détails des différentes étapes

### Extraction depuis PubMed
Pour cela, on télécharge depuis les site internet au format xml les donnée sur les 100.000 premier articles de chacun des termes.
Nous avons donc en sorti, 5 fichier de taille assez conséquente (1 - 3.5 Go).

### Parsing
On utilise la bibliothèque Saxonica pour requèter le XML des fichiers extrait, et d'en tirer uniquement le contenu souhaité : D'une part le titre et le résumé, d'autre part, les termes clé, tels que défini humainement dans la decription de l'article.

### Tag

#### TreeTagger

#### GeniaTagger

### Extraction de termes

### Création des règles

## Détails de la solution technique

## Commentaire du Résultat

## Problèmes rencontrés