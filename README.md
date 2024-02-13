# battlesnake-viewer

Java implementation of Battlesnake Game States viewer.

## Overview

`battlesnake-viewer` is the command-line tool that renders Battlesnake Game State states.

#### Features

- view game frame from battlesnake.com engine
- view saved moveRequest
- CLI

#### Supported games' data sources:
* file in Battle Snake JSON move request format
* battlesnake.com engine
* locale cache

#### Supported output formats

* `ascii`
  * uses utf8 squares to render game state
* `snek` 
  * based on Snek-Spec by Echosec. Plain text game representation that could be used for snake unit tests.

> A snake always begins and ends with capital letter, with the middle segments represented by lower case letters . By
> default Snek-Spec assumes your snake is the snake starting with S (for snake) and ending in T (for tail).
>
Thus `SttT` represents a snake with a length of 4 that is moving to the left.
Details [https://github.com/mike-anderson/snek-spec](https://github.com/mike-anderson/snek-spec?tab=readme-ov-file#snek-spec).

## Build

run ```mvn package``` to build jar file

## Run

After build is done, run the viewer:
```
java -jar target/battlesnake-viewer-1.0-SNAPSHOT-jar-with-dependencies.jar
```

You may add function to your shell's init script (~/.bashrc, ~/.zshrc):
```
battlesnake-viewer () {
  java -jar ~/Downloads/battlesnake-viewer/target/battlesnake-viewer-1.0-SNAPSHOT-jar-with-dependencies.jar ${@}
}
```
Reload shell, and you should be able to run viewer with a simple command `battlesnake-viewer`.

## Usage

```
Usage: battlesnake-viewer [--color] [-f=<codingName>] [-t=<turn>] <uri|file>
Display Battlesnake game state.
      <uri|file>      Uri of the Battlesnake engine game or JSON file.
                      The JSON file should be in move request format. Examples:
                        https://play.battlesnake.com/game/d3537a9c-8f1d-41f3-9596-0f4a38e00e29
                        battlesnake://d3537a9c-8f1d-41f3-9596-0f4a38e00e29 # Alias to previous
                        path/to/local-file.json
      --color         Enable colors. Default true
  -f, --format=<codingName>
                      Format for displaying the state. Possible values: ascii, snek. Default: ascii
  -t, --turn=<turn>   Display specific turn. Defaults to the last turn
```

## Examples

## ASCII rendering

##### standard game

<pre>

  01234567890
0 <span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#A00">◼</span><span style="color:#A00">◼</span><span style="color:#AAA">·</span>
9 <span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#0A0">◼</span><span style="color:#0A0">◼</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#A00">◼</span><span style="color:#A00">◼</span><span style="color:#AAA">·</span>
8 <span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#0A0">◼</span><span style="color:#0A0">◼</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#A00">◼</span><span style="color:#A00">▪</span><span style="color:#AAA">·</span>
7 <span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#0A0">◼</span><span style="color:#0A0">◼</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#A00">◙</span><span style="color:#A00">◼</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span>
6 <span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#0A0">▪</span><span style="color:#0A0">◙</span><b><span style="color:#A0A">●</span></b><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span>
5 <span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span>
4 <span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span>
3 <span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#A50">◼</span><span style="color:#A50">◙</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#0AA">▪</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span>
2 <span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#A50">◼</span><span style="color:#A50">▪</span><span style="color:#0AA">◼</span><span style="color:#0AA">◼</span><span style="color:#0AA">◼</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span>
1 <span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#0AA">◙</span><span style="color:#0AA">◼</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span>
0 <span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span>

</pre>

##### maze
<pre>

  0123456789012345678
0 <span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span>
9 <span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span>
8 <span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span>
7 <span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><b><span style="color:#A0A">●</span></b><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span>
6 <span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span>
5 <span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span>
4 <span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span>
3 <span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span>
2 <span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#0AA">◙</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span>
1 <span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#0AA">◼</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#A00">▪</span><span style="color:#A00">◼</span><span style="color:#A00">◙</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span>
0 <span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#0AA">▪</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span>
9 <span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span>
8 <span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#0A0">◙</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span>
7 <span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#0A0">◼</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span>
6 <span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#0A0">◼</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span>
5 <span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#0A0">◼</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span>
4 <span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#0A0">◼</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span>
3 <span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#0A0">▪</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span>
2 <span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span>
1 <span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span>
0 <span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">#</span><span style="color:#AAA">·</span><span style="color:#AAA">#</span>


</pre>

## Snek spec rendering

<pre>

  01234567890
0 <span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span>
9 <span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#0AA">t</span><span style="color:#0AA">t</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span>
8 <span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#0AA">S</span><span style="color:#0AA">T</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span>
7 <span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#0A0">v</span><span style="color:#0A0">V</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span>
6 <span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#0A0">v</span><span style="color:#0A0">v</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span>
5 <span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#0A0">U</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span>
4 <span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><b><span style="color:#A0A">0</span></b><span style="color:#AAA">-</span>
3 <b><span style="color:#A0A">0</span></b><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span>
2 <span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span>
1 <span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span>
0 <span style="color:#AAA">-</span><span style="color:#AAA">-</span><b><span style="color:#A0A">0</span></b><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span><b><span style="color:#A0A">0</span></b><span style="color:#AAA">-</span><span style="color:#AAA">-</span><span style="color:#AAA">-</span>

</pre>
