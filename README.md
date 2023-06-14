# battlesnake-viewer

Java implementation of Battlesnake Game States viewer.

## Overview

`battlesnake-viewer` is the command-line tool that renders Battlesnake Game State boards.

It support different output formats:

* `ascii` -- human readable format
* `snek` -- implementation of the snek spec, which is a plain text mock format that is mostly used for tests,
  see https://github.com/mike-anderson/snek-spec

## Build

run ```mvn package``` to build jar file

## Usage

```
Usage: battlesnake-viewer [-c=<codingName>] <file>
Prints battlesnake state file.
<file>   Game state JSON file
-c, --coding=<codingName>
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
