# Missing albums finder

Helps you find which album you are missing in your library. Based on discogs database.

## Install

```bash
git clone git@github.com:romain-warnan/missing-albums.git
cd missing-albums
./scripts/compile.sh
```
> Note that `mvn` and `java` must be in your `PATH` for the command to run properly.

## Run

```bash
java -jar missing-albums.jar [options]
```

## Options

 - `-l`, `--artist-list` — File that contains a list of artists names you are interrested in. One artist name per line.  
 Example: `--artist-list /home/name/artists.txt`
    
 - `-a`, `--artists` — Comma separated list of artists names you are interrested in.  
 Example: `--artists "Amon Amarth,Bjork"`
 
 - `-g`, `--genre` — Search missing albums for all artists of the specified genre.  
 Example: `--genre Metal`
   
 - `-f`, `--output-format` — Format of the output. There are three formats:
    - csv: comma separated values,
    - tsv: tabulation separated values,
    - list: each values is in a column of fixed sized.  
 Example: `--output-format csv`
 Default: `list`
 
 - `-q`, `--quiet` — Hide log messages. Only the final result is shown.  
 Example: `--quiet`
 
 - `-s`, `--skip-siblings` — If this flag is set, the program will assume that one folder contains only songs of the same album.
 This is much faster so it is suggested that you put your music library in order and use this option.  
 Example: `--skip-siblings`
    
 - `-v`, `--verbose` — Show detail log messages.  
 Example: `--verbose`
 
 - `-y`, `--year` — Missing albums will not be displayed if they were released before this year.  
 Example: `--year 2015`

 - `-p`, `--path` — The folder in which the program will recursively search for audio files.
 If the path is not specified, the program will search in the current directory.  
 Examples: `--path /mnt/audio/music`, `--path "D:\Mes Documents\Music"`  
 Default: `.`