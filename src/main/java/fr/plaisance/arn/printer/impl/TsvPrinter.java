package fr.plaisance.arn.printer.impl;

import fr.plaisance.arn.model.Album;
import fr.plaisance.arn.model.Artist;
import fr.plaisance.arn.printer.AlbumsPrinter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.SortedSet;

@Component("tsv")
public class TsvPrinter implements AlbumsPrinter {

    @Override
    public String print(Map<Artist, SortedSet<Album>> map) {
        StringBuilder builder = new StringBuilder();
        map.forEach((artist, albums) -> albums.forEach(
            album -> builder.append(
                String.format("%s\t%s\t%s%n",
                    artist.getName(),
                    album.getYear(),
                    album.getName()))));
        return builder.toString();
    }
}
