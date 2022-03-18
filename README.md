# TP2 Android - ExpressVoyage

## Etudiants (F2) :
> Etienne Gouinaud

> Slimane Fakani

## Présentation de l'application

ExpressVoyage permet d'en savoir plus sur les pays du monde. Nous avons également repris le concept d'une
carte du monde à gratter, qui permet de garder une trace des pays visités.

## Features et utilisation de l'appli

### Liste principale

Au chargement, nous faisons un appel à l'API pour récupérer la liste des pays avec leur nom et leur
drapeau. En cas de problème réseau, nous cherchons dans la base de données.
Dans le cas où nous n'avons toujours pas de pays disponibles, nous affichons un bouton un bouton pour
réessayer de les recharger.

Nous avons également ajouté une AutoCompleteTextView pour pouvoir filtrer les pays sur la base de leur nom.

Dans la liste principale, les pays sous forme de carte, où nous affichons leur drapeau et leur nom.

### Détails d'un pays

En cliquant sur une de ces cartes, nous affichons les détails
d'un pays avec sa capitale et sa monnaie. Nous sommes obligés, à cause de l'implémentation de l'API
de refaire un appel pour récupérer ces deux données. Bien évidemment, nous récupérons les données que
nous avons déjà grâce au premier appel d'API fait au lancement de l'appli, comme le drapeau ou le nom
du pays afin d'optimiser le nombre d'appels à l'API. Nous utilisons là aussi la base de données pour
récupérer la donnée au cas où nous n'avons pas de réseau.

Sur cette page de détail, l'utilisateur peut ajouter le pays en favori ou voir son emplacement sur
la carte grâce à un bouton qui l'amène dessus.

### Carte du monde

Nous avons ajouté une carte du monde à l'aide de Google Maps. Lorsqu'un pays est en favori,
nous l'affichons en jaune sur la carte.

### Paramètres

Pour finir, nous avons ajouté un dernier écran "paramètres". A l'heure actuelle,
il ne contient qu'un bouton pour supprimer tous les pays mis en favori.

## Dépendances

Nous utilisons les dépendances suggérées par le sujet, à savoir :
* Room pour la base de données SQLite
* Ktor pour le client HTTP
* Coil pour le chargement des images

Nous utilisons de plus des utilitaires pour la carte Google Maps.
