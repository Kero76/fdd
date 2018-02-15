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

(Etape A)
Pour cela, on télécharge depuis les site internet au format xml les donnée sur les 100.000 premier articles de chacun des termes.
Nous avons donc en sorti, 5 fichier de taille assez conséquente (1 - 3.5 Go).

### Parsing

(Etape B)
On utilise la bibliothèque Saxonica pour requèter le XML des fichiers extrait, et d'en tirer uniquement le contenu souhaité : D'une part le titre et le résumé, d'autre part, les termes clé (mesh), tels que défini humainement dans la decription de l'article.

### Tag

(Etape C)
A l'aide des bibliothèques TreeTagger et GéniaTagger, les fichiers contenant titres et résumés sont parcourus, et l'on en extrait l'ensemble de leurs contenu catégorisé selon la nature des lexèmes. Ces fichiers permettent de donner plus de sens au text.
#### TreeTagger
TreeTagger est un outils d'annotation de texte avec des informations sur les lemmes, et les natures grammaticales. Il a été developpé par Helmut Schmid, un Allement de l'université de Stuttgart. Il permet de travailler de façon efficace sur l'Allemand, l'Anglais, le Francais, l'Italien, le Danois, le Hollandais, l'Espagnol, le Bulgare, le Russe, le Portugais, le Galois, le Grec, le Chinois, le Swahili, le Slovaque, le Slovène, le Latin, l'Estonien, le Polonais, le Roumain, le Tscheque, le Copte, et le vieux Francais. Il peut etre utiliser sur d'autres langage si un lexique est manuellement Taggé, et qu'un corpus d'entrainement est fourni.

Exemple : (L'ensemble des fichier Taggé est fourni en Annexe, dans le dossier Etape C)
```
Preoperative	JJ	<unknown>
diagnosis	NN	diagnosis
is	VBZ	be
a	DT	a
matter	NN	matter
of	IN	of
challenge	NN	challenge
and	CC	and
usually	RB	usually
made	VBN	make
at	IN	at
laparotomy	NN	laparotomy
.	SENT	.
```
Ici, On peut voir que les mots sont classé en fonction de leur nature grammatical, on peut voir que les verbe conjugué sont remis a leurs forme infinitive.

Le mot Preoperative n'est pas connu par TreeTagger, il le marque unknown. 

#### GeniaTagger

GéniaTagger est un outils de Tagging réaliser pour le domaine biomédical par l'Université de Tokyo, il analyse des phrases en Anglais, et sort les forme basiques, la nature grammaticale, le groupe nominaux / verbaux, les entitées nomées. Il a été particulièrement entraîné pour des textes biomédiacaux, comme des résumés de medline.


Le même exemple avec GéniaTagger :
```
Preoperative	Preoperative	JJ	I-NP	O
diagnosis	diagnosis	NN	I-NP	O
is	be	VBZ	B-VP	O
a	a	DT	B-NP	O
matter	matter	NN	I-NP	O
of	of	IN	B-PP	O
challenge	challenge	NN	B-NP	O
and	and	CC	O	O
usually	usually	RB	B-VP	O
made	make	VBN	I-VP	O
at	at	IN	B-PP	O
laparotomy.	laparotomy.	FW	B-NP	O
``` 
Ici, on remarque que géniaTagger connait le terme "Preoperative"

Du reste, geniaTagger fournit plus d'information que TreeTagger, notamment les information sur le "chunks".
On peut noter que le "." n'est pas détecter comme un élément à part entière.

### Extraction de termes

(Partie D)
Cette partie est réalisée grave à Yatea; Yet Another Term ExtrActor. 
YaTeA est un extracteur de termes. Il identifie et extrait des groupes nominaux pouvant être des termes, i.e. des termes candidats. Chaque terme candidat est analysé syntaxiquement pour faire apparaître sa structure sous la forme de têtes et modifieurs. Il a été developpé par Sophie Aubin et Thierry Hamon dans le cadre du projet ALVIS. 


Exemple, avec le document dont a était extrait l'exemple précédent.
``` 
# Inflected form	Frequency	DDW
abdominal cocoon	2	0
etiology of this disease	1	0
intestinal obstruction	1	0
small bowel	1	0
matter of challenge	1	0
rare disease	1	0
partial intestinal obstruction	1	0
fibrotic membrane	1	0
Thirty-five cases	1	0
Preoperative diagnosis	1	0
encasement of the small bowel	1	0
partial encasement of the small bowel	1	0
```

On retrouve "Matter of challenge", et "Preoperative diagnosis", on peut voir que le terme abdominal cocoon est classé premier car il apparait deux fois.
Les termes sont ainsi classé par importance.
### Création des règles

## Détails de la solution technique

Pour le parsing, un programme en Java, en utilisant la bibliothèque Saxonica.
Pour TreeTagger, GeniaTagger et Yatea, un script bash lance les 3, et automatise les operation
Pour Apriori, un algorithme executable en java se charge du reste
## Commentaire du Résultat

## Problèmes rencontrés

- Les temps de process :
 > A cause de la taille conséquente de l'echantillon, et de la lenteur des algorithmes, le temps de process est très important, pour TreeTagger | GeniaTagger + YaTea, qui sont regrouper dans un seul script, il faut 9 s secondes pour traiter un fichier. Considèrant qu'il y a 8158 articles (pour le plus petit des corpus d'origine), il a fallu plus de 20 heures pour traiter tous les articles. Pour cette raison, un seul corpus à été traité.
 - La taille des fichier d'origine :
 > Pour avoir un echantillon suffisament consequent, les fichier téléchargés sur pubmed devait comporter un grand nombre d'entrée, en conséquence, les fichier d'origine pèse très lourd, de l'ordre du Giga octet. Ca prend un temps consequent de télécharger, et de déplacer ces fichiers.
 - L'instalation des librairies :
 > Les librairy TreeTagger, GeniaTagger et YaTea ont été particulièrement difficile à installer, elles sont dans differents langages (Perl, c++, Python) sont parfois incompatible windows, parfois fonctionnent mieux sous windows. Cela a pris un temps consequent de les faire toutes fonctionner.
 - Genia Tagger :
> L'algorithme GeniaTagger est plus efficace pour traiter les fichier traitant du domaine médical, ce qui est normal, ayant été entraîné sur PubMed, Malheureusement, le format de sortie de géniatagger n'est pas reconnu par YaTea, et est donc pas exploitable. Les règle d'association sont donc crées avec la sortie de YaTea par TreeTagger.
 - L'algorithme apriori :
 > Il a été complexe de trouver une implémentation d'apriori / close qui créer réelement des règles d'association, la plupart ne faisant que du pattern matching.
