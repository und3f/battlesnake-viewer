# battlesnake-viewer

Java implementation of Battlesnake Game States viewer.

## Overview

`battlesnake-viewer` is the command-line tool that renders Battlesnake Game State boards.

Supported games sources:
* File in Battle Snake JSON move request format
* Loading game from BattleSnake engine

Supported output formats:

* `ascii` -- human readable format
* `snek` -- implementation of the snek spec, which is a plain text mock format that is mostly used for tests,
  see https://github.com/mike-anderson/snek-spec

## Build

run ```mvn package``` to build jar file

## Usage

```
Usage: battlesnake-viewer [-f=<codingName>] <uri>
Display Battlesnake game state.
      <uri>   Uri of the game state JSON file or Battlesnake game.
              Example: battlesnake://367d5926-7d06-42be-8af1-2781e0eade93
  -f, --format=<codingName>
              Format for displaying the state. Possible values: ascii, snek.
                Default: ascii
```

Currently three are no launcher script, so you need to substitute `battlesnake-viewer` with next command:

```
java -jar target/battlesnake-viewer-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## Examples

## ASCII rendering

```
#◻###############.#
#◻◻◻◻◻◻◻◻◼........#
#.######.#.######.#
#....#...#...#....#
##.#.#.#####.#.#.##
#..#...........#..#
#.##.###.#.###.##.#
#........#........#
#..#.#.#####.#.#..#
#..#.#.......#.#..#
####.#.#.#.#.#.####
.......#...#.......
####.#.#.#.#.#.####
#..#.#.......#.#◻◻#
#..#.###.#.###.#◻◻#
#....#...#...#◼◻◻◻#
#.##.#.#####.#.##◻#
#◻◻◻◻.........◻..◻#
#◻##.###.#.###◻##◻#
#◻.......#....◻◻◻◻#
#◻###############.#
```

## Snek spec rendering

```
/v///////////////-/
/vvvvvvvvU--------/
/-//////-/-//////-/
/----/---/---/----/
//-/-/-/////-/-/-//
/--/-----------/--/
/-//-///-/-///-//-/
/--------/--------/
/--/-/-/////-/-/--/
/--/-/-------/-/--/
////-/-/-/-/-/-////
-------/---/-------
////-/-/-/-/-/-////
/--/-/-------/-/tt/
/--/-///-/-///-/tt/
/----/---/---/Sttt/
/-//-/-/////-/-//t/
/vvvV---------T--t/
/v//-///-/-///t//t/
/v-------/----tttt/
/v///////////////-/
```
