package fr.plaisance.arn.printer;

import fr.plaisance.arn.model.Album;
import fr.plaisance.arn.model.Artist;

import java.util.Map;
import java.util.SortedSet;

public interface AlbumsPrinter {

    String print(Map<Artist, SortedSet<Album>> albums);
}
