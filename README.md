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
Usage: battlesnake-viewer [-f=<codingName>] [-t=<turn>] <uri|file>
Display Battlesnake game state.
      <uri|file>      Uri of the game state JSON file or Battlesnake game.
                      e.g.: battlesnake://367d5926-7d06-42be-8af1-2781e0eade93
  -f, --format=<codingName>
                      Format for displaying the state. Possible values: ascii,
                        snek. Default: ascii
  -t, --turn=<turn>   Display specific turn. Defaults to the last turn
```

Currently three are no launcher script, so you need to substitute `battlesnake-viewer` with next command:

```
java -jar target/battlesnake-viewer-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## Features

- Loading game from battlesnake engine
- Caching loaded games
- Different output formats

## Examples

## ASCII rendering

```
  0123456789012345678
0 #.###############.#
9 #........#........#
8 #.##.###.#.###.##.#
7 #.............●...#
6 #.##.#.#####.#.##.#
5 #....#...#...#....#
4 #..#.###.#.###.#..#
3 #..#.#.......#.#..#
2 ####.#.#◼#.#.#.####
1 .......#◻..#◻◻◼....
0 ####.#.#◻#.#.#.####
9 #..#.#.......#.#..#
8 #..#.#.#####.#◼#..#
7 #........#....◻...#
6 #.##.###.#.###◻##.#
5 #..#..........◻#..#
4 ##.#.#.#####.#◻#.##
3 #....#...#...#◻...#
2 #.######.#.######.#
1 #.................#
0 #.###############.#
```

## Snek spec rendering

```
  01234567890
0 -----------
9 -----------
8 --0--------
7 -----------
6 -----------
5 ----tS-----
4 ----tttt---
3 -----Ttt-U0
2 ---------v-
1 ----Vvvvvv-
0 -----------
```
