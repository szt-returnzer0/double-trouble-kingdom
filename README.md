# Double Trouble Kingdom


A __*returnzer0*__ csapat __Szofvertechnológia__ tárgyon fejlesztettet stratégiai játékát tároló repository.


## Tartalom
- [Double Trouble Kingdom](#double-trouble-kingdom)
  - [Tartalom](#tartalom)
  - [Rövid játékleírás](#rövid-játékleírás)
  - [Megvalósítás](#megvalósítás)
  - [Projekt Setup](#projekt-setup)
    - [Gradle command](#gradle-command)
    - [Buildelés](#buildelés)
    - [Futtatás](#futtatás)
    - [Tesztesetek futtatása](#tesztesetek-futtatása)
  - [Technológiák](#technológiák)
  - [További információ](#további-információ)
  - [A Csapat](#a-csapat)


## Rövid játékleírás
A Double Trouble Kingdom kétszemélyes, egy gépen játszható stratégiai játék. Egy 2D-s felülnézetes, négyzetrácsos pályán zajlik le. A játék körökre van osztva, körönként mindkét játékos tud építkezni, majd támadni. A támadás először csak zsoldos alapkatonákkal történhet, majd később lehet speciális egységeket is képezni. A két játékos egy-egy kastéllyal rendelkezik, a játék célja az ellenfél kastélyának ledöntése. Védekezésképpen a játékosok tornyokat építhetnek, melyekben akár íjászok tüzelhetnek az ellenfél támadó katonáira. A katonák célba vehetik a tornyokat és a kastélyokat is. A játék akkor ér véget, amikor egy játékos kastélya, illetve összes életben lévő katonája elveszti az életerőpontját.


## Megvalósítás
A feladat megvalósításához a Java nyelvet választottuk, mert hatékonyan lehet benne eseményvezérelt alkalmazásokat fejleszteni és beszédes a szintaxisa. A fejlesztést a legújabb LTS verzióban, a Java 17-ben végezzük. A projekt buildeléséhez a Gradle-t használjuk, a tisztán érthető dokumentációja és a felkapottsága miatt esett rá a választás. A felhasználói felület fejlesztéshez a Java Swing és AWT párost alkalmazzuk. A korábbi játékeredményeket SQLite adatbázisban tároljuk a serverless mivoltából és könnyen kezelhetőségéből kiindulva. 


## Képek
Menü:
![menu](https://user-images.githubusercontent.com/83843622/194316554-2ef52e42-f473-4771-8be6-c69c19847603.png)


Játékmenet:
![gameplay](https://user-images.githubusercontent.com/83843622/194316453-43e69a5f-740c-4719-af7d-e5954105b15e.gif)

Szerkesztő:
![editor](https://user-images.githubusercontent.com/83843622/194316507-cb2232ed-3f02-4a29-89b7-df7eb6933253.png)

Útkeresési debug:
![debugwaypoint](https://user-images.githubusercontent.com/83843622/194316630-368bc780-1c2a-4237-86a8-83f9cb33e5c7.gif)



## Projekt Setup
Klónozás után a projektmappát tetszőleges text editorban vagy IDE-ben megnyitva, a terminálban a következő parancsokkal tudjuk buildelni, futtatni és tesztelni a projektet.


### Gradle command
A __*gradle*__ command segítségével könnyedén elérhetjük a Gradle Task-jait.
A __*gradle*__  parancsot többféle módon is elérhetjük.

*Ha rendelkezünk telepített Gradle-lel:*
```
gradle
```
> __Információ:__ A példákat ezzel a paranccsal szemléltetjük.

__Ha nem rendelkezünk telepített Gradle-lel a prokjektmappában a megfelelő *gradle wrapper (gradlew)* fájl futtatásával helyettesíthetjük a telepítését.__


*Windows rendszeren:*
```
gradlew.bat
```

*Unix/Linux rendszeren:*
```
./gradlew
```
> __Érdekesség:__ Egyes IDE-k, például az IntelliJ alapból tartalmaz Gradle integrációt, más fejlesztőprogramokhoz pedig bővítményként adhatjuk hozzá, például a VSCode-hoz is.


### Buildelés
```
gradle build
```
> __Emlékeztető:__ Gradle wrapper esetén __*gradle.bat build*__ vagy __*./gradelw build*__


### Futtatás
```
gradle run
```


### Tesztesetek futtatása
```
gradle test
```

## Technológiák
- Java 17 LTS
- Gradle
- Swing
- AWT
- SQLite


## További információ
A projekt tervei és a játék részletes leírása a repository [Wiki](../../wikis/Home) oldalán található. \
A project javadoc dokumentációja a [GitLab Pages](https://szt-c-2022.szofttech.gitlab-pages.hu/group-02/double-trouble-kingdom/index.html) oldalon található.

## A Csapat
- Berecz Dániel 
- Neszlényi Kálmán 
- Rubóczki Benedek

__*2022.*__ *ELTE IK Szoftvertechnológia*
