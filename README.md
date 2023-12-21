# JAVA SUDOKU

Projektarbeit ON23B von [angi-mint L.W.](https://github.com/angi-mint), 
[Ninntexx N.D.](https://github.com/Ninntexx), 
[PanikLanguste25 F.Z.](https://github.com/PanikLanguste25),
[Goetz04 G.H.](https://github.com/Goetz04)
im Fach T1 - Programmierung

## Projektstruktur
Das Programm ist in 3 verschiedene Klassen unterteilt:
- Main.java
- Board.java
- Window.java

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
  - 2x2
  - 3x3
- Difficulty: Der Schwierigkeitsgrad bestimmt lediglich, wie viele der ursprünglichen Felder entfernt werden,
  bevor das Spiel beginnt.
  - easy: ein Drittel der Felder werden entfernt.
  - middle: die Hälfte der Felder werden entfernt.
  - hard: es werden so viele Felder wie möglich entfernt.

### In-Game Menü
- Eingabefelder:
#### Buttons
- New Game:
- Submit:
- Clear Field:
- Exit Game:
- View Game:

## Quellen
- [How to Create a Sudoku Puzzle Part One: Algorithm Explained used to Fill a Sudoku Grid.](https://www.youtube.com/watch?v=iSdW8OM_b3E)
- [How to Create a Sudoku Part Two: Algorithm to create a symmetrical sudoku a human can solve!](https://www.youtube.com/watch?v=DpmTbMQFHaI)
