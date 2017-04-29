package fr.plaisance.arn.printer.impl;

import fr.plaisance.arn.model.Album;
import fr.plaisance.arn.model.Artist;
import fr.plaisance.arn.printer.AlbumsPrinter;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;

@Component("listPrinter")
public class ListPrinter implements AlbumsPrinter {

    @Override
    public String print(Map<Artist, SortedSet<Album>> map) {
        StringBuilder builder = new StringBuilder();
        map.forEach((artist, albums) -> albums.forEach(
            album -> builder.append(
                String.format(format(map),
                    artist.getName(),
                    album.getYear(),
                    album.getName()))));
        return builder.toString();
    }

    private static int albumColSize(Map<Artist, SortedSet<Album>> map) {
        return map.values()
            .stream()
            .flatMap(SortedSet::stream)
            .map(Album::getName)
            .max(Comparator.comparingInt(String::length))
            .get()
            .length();
    }

    private static int artistColSize(Map<Artist, SortedSet<Album>> map) {
        return map.keySet()
            .stream()
            .map(Artist::getName)
            .max(Comparator.comparingInt(String::length))
            .get()
            .length();
    }


    private static String colFormat(int colSize){
        return "%-" + (colSize + 1) + "s";
    }

    private static String format(Map<Artist, SortedSet<Album>> map){
        int albumColSize = albumColSize(map);
        int artistColSize = artistColSize(map);
        int yearColSize = 4;
        return String.format("%s%s%s",
            colFormat(artistColSize),
            colFormat(yearColSize),
            colFormat(albumColSize)) + "%n";
    }
}
