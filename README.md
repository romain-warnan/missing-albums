# Missing albums

Helps you find which album you are missing in your library. Based on musicbrainz database.

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

 - `-f`, `--artists-file` — Path to a file that contains a list of artists names you are interrested in. One artist name per line.  
 Example: `--artists-file /home/user/artists.txt`
    
 - `-a`, `--artists` — Comma separated list of artists names you are interrested in.  
 Example: `--artists "Amon Amarth,Bjork"`
 
 - `-g`, `--genre` — Search missing albums for all artists of the specified genre.  
 Example: `--genre Metal`
   
 - `-o`, `--output-format` — Format of the output. There are three formats:
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
 
 - `-y`, `--year` — If set, missing albums will not be displayed if they were released before this year.
 If not set, all missing albums that have ever been released will be displayed.
 If set to `after`, only missing albums that have been released after the latest one in the library will be displayed.
 Example: `--year 2015`

 - `-x`, `--proxy` — If your network uses a proxy to connect to the internet, the uri of the proxy server.
 Example:` --proxy http://123.15.12.1:8081`, `--proxy http://proxy.my-company.com:8080`  

 - `-p`, `--library-path` — The folder in which the program will recursively search for audio files.
 If the path is not specified, the program will search in the current directory.  
 Examples: `--library-path /mnt/audio/music`, `--library-path "D:\Mes Documents\Music"`  
 Default: `.`
