# JAVA SUDOKU

Projektarbeit ON23B von [angi-mint L.W.](https://github.com/angi-mint), 
[Ninntexx N.D.](https://github.com/Ninntexx), 
[PanikLanguste25 F.Z.](https://github.com/PanikLanguste25),
[Goetz04 G.H.](https://github.com/Goetz04)
im Fach T1 - Programmierung

## Projektstruktur
Das Programm ist in 3 verschiedene Klassen unterteilt:
- Main.java:    Diese Klasse beinhaltet die main-Methode des Programms und dient als Einstiegspunkt.
- Board.java:   Diese Klasse ist verantwortlich für die Erstellung eines gültigen Sudoku-Rätsel.
- Window.java:  Dise Klasse verwaltet die grafischen Elemente und prüft das gelöste Rätsel auf Richtigkeit.

## Sudoku-Regeln
*"In der üblichen Version ist es das Ziel, ein 9×9-Gitter mit den Ziffern 1 bis 9 so zu füllen,
dass jede Ziffer in jeder Einheit (Spalte, Zeile, Block = 3×3-Unterquadrat) genau einmal vorkommt –
und in jedem der 81 Felder exakt eine Ziffer vorkommt. Ausgangspunkt ist ein Gitter,
in dem bereits mehrere Ziffern vorgegeben sind."*

([Wikipedia, "Sudoku"](https://de.wikipedia.org/wiki/Sudoku#) 20.12.2023)

## GUI
Die grafische Benutzeroberfläche (GUI) des Sudoku-Spiels bietet eine Reihe von Einstellungen und Optionen,
die es dem Spieler ermöglichen, die Puzzlegröße und den Schwierigkeitsgrad anzupassen.

### Einstellungsmenü
- Nickname: Hier kann der Spieler seinen Namen eingeben, der später auf dem Gewinn- und Verlierbildschirm angezeigt wird.
- Grid Size: Wählt die Größe des Spielfeldes aus, es gibt zwei Optionen:
  - 2x2 (4 Felder)
  - 3x3 (9 Felder)
- Difficulty: Der Schwierigkeitsgrad bestimmt lediglich, wie viele der ursprünglichen Felder entfernt werden,
  bevor das Spiel beginnt.
  - easy: Ein Drittel der Felder werden entfernt.
  - middle: Die Hälfte der Felder werden entfernt.
  - hard: Es werden so viele Felder wie möglich entfernt.

### In-Game Menü
- Eingabefelder: Hier kann der Nutzer seine Zahlen eintragen. Bei einem 3x3 Grid sind Zahlen von 1-9 gültig und bei einem 2x2 Zahlen von 1-4. 
#### Buttons
- New Game: Dieser Button befindet sich auf der Spielbrettseite und auf den beiden Ergebnisseiten. Bei Betätigung erstellt er ein neues Spielfeld, wichtige Einstellungen wie Grid Größe und Schwierigkeitsgrad bleiben dabei unverändert.
- Submit: Der Submit Button ist auf der Spielbrettseite zu finden und wertet bei Betätigung das Spiel aus. Nach der Auswertung leitet er den Nutzer je nach Ergebnis auf eine der beiden Ergebnisseiten (gewonnen/verloren) weiter.  
- Clear Field: Der Clear Field Button ist auf der Spielbrettseite zu finden und entfernt bei Betätigung die Eingaben des Nutzers im Spiel. Somit ist das Spielfeld wieder leer und kann neu befüllt werden.
- Exit Game: Der Exit Game Button befindet sich auf der Spielbrettseite und auf den beiden Ergebnisseiten. Bei Betätigung wird der Nutzer zurück zur Start/Menüseite geleitet. 
- View Game: Der View Game Button ist ausschließlich auf den Ergebnisseiten (gewonnen/verloren) zu finden. Bei Betätigung kommt der Nutzer zurück auf die Spielbrettseite und kann dort wieder alle vorhandenen Funktionen anwenden.

## Quellen
- [How to Create a Sudoku Puzzle Part One: Algorithm Explained used to Fill a Sudoku Grid.](https://www.youtube.com/watch?v=iSdW8OM_b3E)
- [How to Create a Sudoku Part Two: Algorithm to create a symmetrical sudoku a human can solve!](https://www.youtube.com/watch?v=DpmTbMQFHaI)
