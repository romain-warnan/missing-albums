### Missing albums finder

Helps you find which album you are missing in your library. Based on discogs database.

```
Usage: Album release notifier [options]
  Options:
    
    -l, --artist-list
      File that contains a list of artists names you are interrested in. One
      artist name per line. Example: --artist-list /home/name/artists.txt
      Default: []
    
    -a, --artists
      Comma separated list of artists names you are interrested in. Example:
      --artists "Amon Amarth,Bjork"
      Default: []
    
    -g, --genre
      Search missing albums for all artists of the specified genre. Example:
      --genre Metal
    
    -h, --help
      Display this message. Example: --help
      Default: false
    
    -o, --only-albums
      If this flag is set, the program will only return releases that are
      albums. This is very convenient to limit the number of results. But it
      rely on HTML parsing and not on the discogs webservice because the API
      does not allow to search for album only. For this reason, this function
      might be broken at some point.Example: --only-albums
      Default: false
    
    -f, --output-format
      Format of the output. There are three formats:- csv: comma separated
      values, - tsv: tabulation separated values, - list: each values is in a
      column of fixed sized.Example: --output-format csv
      Default: list
    
    -p, --path
      The folder in which the program will recursively search for audio files.
      If the path is not specified, the program will search in the current
      directory. Examples: --path /mnt/audio/music, --path "D:\Mes
      Documents\Music"
      Default: .
    
    -q, --quiet
      Hide log messages. Only the final result is shown.Example: --quiet
      Default: false
    
    -s, --skip-siblings
      If this flag is set, the program will assume that one folder contains
      only songs of the same album. This is much faster so it is suggested
      that you put your music library in order and use this option. Example:
      --skip-siblings
      Default: false
    
    -v, --verbose
      Show detail log messages.Example: --verbose
      Default: false
    
    -y, --year
      Missing albums will not be displayed if they were released before this
      year. Example: --year 2015
      Default: 1900
```