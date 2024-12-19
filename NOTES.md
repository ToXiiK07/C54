# Notes en Java & Kotlin pour le développement Android 


--- 
# Kotlin

## Histoire de Kotlin 

- **Premiers balbutiements :** Introduit en 2011, Kotlin est officiellement reconnu par Google en 2019 comme une alternative à Java pour le développement d'applications Android. Le langage se distingue par une syntaxe plus concise que celle de Java.  
- **Développement :** Kotlin a été conçu par une équipe dirigée par *Andrey Breslav*.  
- **Interopérabilité avec Java :** Kotlin permet l'utilisation d'objets ou de méthodes écrits dans des fichiers Java au sein de fichiers Kotlin.  
- **Simplification :** Le point-virgule (`;`) n’est pas obligatoire en Kotlin.- **Premiers balbutiements :** Introduit en 2011, Kotlin est officiellement reconnu par Google en 2019 comme une alternative à Java pour le développement d'applications Android. Le langage se distingue par une syntaxe plus concise que celle de Java.  
- **Développement :** Kotlin a été conçu par une équipe dirigée par *Andrey Breslav*.  
- **Interopérabilité avec Java :** Kotlin permet l'utilisation d'objets ou de méthodes écrits dans des fichiers Java au sein de fichiers Kotlin.  
- **Simplification :** Le point-virgule (`;`) n’est pas obligatoire en Kotlin.

## Les variables en Kotlin 

- **`var` :** Utilisé pour une variable, c’est-à-dire une valeur qui peut être modifiée.  
- **`val` :** Comparable à une constante, une valeur qui ne peut pas être modifiée après son initialisation.  
  - ⚠️ Une référence à un objet peut être déclarée avec `val`, mais ses propriétés internes, elles, restent modifiables.  

### Exemple :
```kotlin
var ajouter: Button? = null
```

- **Initialisation obligatoire :**  
  Les variables doivent être **initialisées au moment de leur déclaration** ou **dans un constructeur**, surtout lorsqu’on travaille dans une activité (vue) en Android.

- **Utilisation de `lateinit var` :**  
  Si une variable ne peut pas être initialisée immédiatement, on peut utiliser le mot-clé **`lateinit var`**, mais il sera impératif de l’initialiser avant son utilisation.

- **Gestion du `null` :**  
  Kotlin réduit considérablement les risques de *NullPointerException* grâce à son système de gestion stricte du `null`.  

## Classe Interne Kotlin

- **Mot-clé `inner` :**  
  Permet de déclarer une classe interne (*inner class*) qui a accès aux variables et fonctions de la classe parente ou principale.  

- **Le `:` en Kotlin :**  
  Utilisé pour indiquer qu’une classe **implémente** une interface ou **hérite** d’une classe parente.  

## Fonction en Kotlin 

- **Déclaration :**  
  Le mot-clé **`fun`** est utilisé pour déclarer une fonction.  

- **Exemple :**  
  Une fonction qui prend deux paramètres de type `Int` et retourne un `Int` :
```kotlin
  fun sum(a: Int, b: Int): Int {
      // return a + b
  }
```

## TRY/CATCH KOTLIN

- En Kotlin, toutes les exceptions sont **non contrôlées**, donc on n’a pas à faire de `try/catch` de sûreté.
- On peut néanmoins utiliser un `try/catch`, mais il n’est pas obligatoire.

### Exemple :

```kotlin
try {
    singleton.deserialiserListe()
    sonnerie.progress = singleton.getSonnerieProgress()
    media.progress = singleton.getMediaProgress()
    notif.progress = singleton.getNotifProgress()
} catch (e: Exception) {
    e.printStackTrace()
}
```

## JAVA → KOTLIN

| **Java**                                     | **Kotlin**                                     |
|----------------------------------------------|-----------------------------------------------|
| `double valeur = 90.5;`                      | `var valeur = 90.5`                           |
| `class Gestion implements View.OnClickListener` | `inner class Gestion : View.OnClickListener`  |
| `System.out.println("bonjour");`             | `println("bonjour")`                          |
| `public void calculerSomme(int nbEntrees)`   | `fun calculerSomme(nb: Int)`                  |


## CODE POUR ÉCRIRE À UN .TXT en Kotlin

Exemple de code pour ajouter une tâche dans un fichier `.txt` en Kotlin :

```kotlin
var tacheAjoutee = memo.text.toString()
val fos: FileOutputStream = openFileOutput("fichier.txt", MODE_APPEND) // append : écrit à la fin du fichier plutôt qu'au début
val osw = OutputStreamWriter(fos) // traduit en caractères
val bw = BufferedWriter(osw) // plus rapide
bw.write(tacheAjoutee)
bw.newLine() // pour changer de ligne
bw.close() // fermer le buffer
finish() // pour revenir au menu principal
```

## CODE POUR LIRE DANS UN .TXT en Kotlin

Exemple de code pour lire dans un fichier `.txt` en Kotlin :

```kotlin
fun lesMemos(): Vector<String> {
    val v = Vector<String>()
    val fis: FileInputStream = openFileInput("fichier.txt") // lit le fichier
    val isr = InputStreamReader(fis) // traduit en caractères
    val br = BufferedReader(isr) // plus rapide
    br.forEachLine { s -> v.add(s) } // itère sur chaque ligne
    br.close() // ferme le flux
    return v
}
```

---

# Stockage et mémoire

## STOCKAGE INTERNE VS EXTERNE EN ANDROID

| **Type de stockage**                     | **Format**                                                                                       | **Accessibilité**                                                                                                                                                 |
|------------------------------------------|-------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Pour toutes les versions du SDK / API**                                                                                                                                                                                                                                          |
| **Stockage interne**                     | `.xml` (ex. *SharedPreferences*, *DataStore*)  <br> `.txt` (fichiers texte)                     | Seulement l’application qui a créé ces données peut y accéder.                                                                                                  |
| **Pour les versions antérieures à SDK 29 / API 10**                                                                                                                                                                                                                              |
| **Stockage externe primaire (partagé)**  | Photos, images, documents téléchargés, fichiers de données disponibles pour plusieurs apps      | Plusieurs applications peuvent y accéder, à condition que les permissions soient définies dans le fichier `AndroidManifest`.                                    |
| **Stockage externe secondaire (carte SD)** | Photos, images, documents téléchargés, fichiers de données disponibles pour plusieurs apps      | Même règle que pour le stockage primaire : accessible à plusieurs apps si les permissions sont définies dans le fichier `AndroidManifest`.                      |
| **Depuis SDK 29 / API 10 et suivantes**                                                                                                                                                                                                                                           |
| **Scoped Storage (stockage étendu)**     | Restreint l’accès aux fichiers sur le téléphone : création/ajout possible uniquement dans des emplacements précis | Pas besoin de demander de permissions pour écrire/ajouter/lire, car les emplacements accessibles sont limités par défaut.                                        |

### Différence entre la gestion de fichiers textes et la gestion de fichiers de sérialisation :
- Les fichiers textes sont simplement des fichiers où les données sont stockées sous forme de texte brut, tandis que les fichiers de sérialisation contiennent des objets transformés en un format binaire ou texte spécial pour être stockés ou transférés.

### Gestion des flux de caractères vs flux d’octets :
- Un flux d’octets gère les données brutes sous forme d’octets, tandis qu’un flux de caractères traite des données encodées en caractères.


## ACCÈS À LA MÉMOIRE INTERNE

### On définit les entrées/sorties en terme de flots de données (streams) :
- **Flot de données :** C’est une suite ordonnée d’infos qui provient d’une source (input stream) ou qui se dirige vers une destination (output stream).

### Types de flots :
- **Flots d’octets / binaires** : (ex. `FileInputStream`, `FileOutputStream`, …) pour travailler avec des images, du son ou faire de la sérialisation d’objets.
- **Flots de caractères** : (ex. `FileReader`, `FileWriter`, `StringReader`,…) pour travailler avec des caractères.

### Méthode `flush` :
- La méthode `flush` est appelée par la méthode `close` et vide le flux dans le fichier texte de destination.


## EN ÉCRITURE VS EN LECTURE

| **En écriture**                            | **En lecture**                                    |
|--------------------------------------------|--------------------------------------------------|
| **JAVA :** `openFileOutput(String nomDuFichier)` | **KOTLIN :** Permet d’accéder à un fichier en mémoire interne : retourne un flux binaire (propre à Android) |
| Retourne un `FileOutputStream`             | **JAVA :** `openFileInput(String nomDuFichier)`   |
| **KOTLIN :** Permet d’accéder à un fichier en mémoire interne | Retourne un `FileInputStream`                   |
| **OutputStreamWriter**                     | Permet de convertir le flux binaire en flux de caractères |
| **BufferedWriter**                         | Tampon accélérant le processus d’écriture (ne pas oublier de fermer le flux à la fin de l’utilisation) |
|                                            | **BufferedReader**                               |

---

# GESTION DU BUFFER

- **`use` :** Garantit que le buffer est fermé à la fin de son utilisation (Annexe 1B).
- **`BufferedReader` ou `BufferedWriter` :** Optimise le temps de lecture/écriture, réduit l’impact de latence.
- **Buffer (tampon) :** Permet de régulariser l’accès aux données, évite que chaque lecture d’octet ou de caractère nécessite un accès au fichier texte.
- **Taille par défaut :** 8192 octets.

### Exemple d'utilisation de `use` avec `BufferedReader` :

```kotlin
val fileName = "example.txt" // Utilisation du mot-clé 'use' pour garantir la fermeture du buffer
BufferedReader(FileReader(fileName)).use { reader -> // Lecture du contenu du fichier
    var line: String?
    while (reader.readLine().also { line = it } != null) {
        println(line)
    }
}
```
---

# Sérialisation & Désérialisation 

## SÉRIALISATION DANS LA MÉTHODE `onStop()`

- On fait la sérialisation dans la méthode `onStop()` si on veut sauvegarder les données lorsque l'on quitte l'application (Voir Annexe 3C, projetDentiste).

### Exemple de code pour sérialiser:

```kotlin
override fun onStop() {
    super.onStop()

    val memo = noteDent1.text.toString()
    val numDent = numDent1.text.toString()
    val canalDent = canalDent1.isChecked()
    Singleton.getInstance(applicationContext).ajouterDent(Dent(memo, numDent, canalDent))
    
    val memo2 = noteDent2.text.toString()
    val numDent2 = numDent2.text.toString()
    val canalDent2 = canalDent2.isChecked()
    Singleton.getInstance(applicationContext).ajouterDent(Dent(memo2, numDent2, canalDent2))

    try {
        Singleton.getInstance(applicationContext).serialiserListe()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
```

## DÉSÉRIALISATION DANS LA MÉTHODE `onCreate()`

- On désérialise dans le `onCreate()` si on veut charger les données au démarrage de l’application (Voir Annexe 3C, projetDentiste).

### Exemple de code pour désérialiser:

```kotlin
try {
    val temp = singleton.deserialiserListe()
    singleton.listeDent = temp
    noteDent1.setText(singleton.listeDent[0].memo)
    numDent1.setText(singleton.listeDent[0].numDent)
    canalDent1.isChecked = singleton.listeDent[0].check

    noteDent2.setText(singleton.listeDent[1].memo)
    numDent2.setText(singleton.listeDent[1].numDent)
    canalDent2.isChecked = singleton.listeDent[1].check
} catch (e: Exception) {
    e.printStackTrace()
}
```

--- 
