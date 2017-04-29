package fr.plaisance.arn.printer.impl;

import fr.plaisance.arn.model.Album;
import fr.plaisance.arn.model.Artist;
import fr.plaisance.arn.printer.AlbumsPrinter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.SortedSet;

@Component("csvPrinter")
public class CsvPrinter implements AlbumsPrinter {

    @Override
    public String print(Map<Artist, SortedSet<Album>> map) {
        StringBuilder builder = new StringBuilder();
        map.forEach((artist, albums) -> albums.forEach(
            album -> builder.append(
                String.format("\"%s\";\"%s\";\"%s\"%n",
                    artist.getName(),
                    album.getName(),
                    album.getYear()))));
        return builder.toString();
    }
}
