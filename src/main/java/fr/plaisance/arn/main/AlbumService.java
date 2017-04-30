package fr.plaisance.arn.main;

import fr.plaisance.arn.model.Album;
import fr.plaisance.arn.model.Artist;
import fr.plaisance.arn.model.Library;
import fr.plaisance.arn.model.Model;
import fr.plaisance.arn.service.LocalLibraryService;
import fr.plaisance.arn.service.RemoteLibraryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AlbumService {

    @Autowired
    private LocalLibraryService localLibraryService;

    @Autowired
    private RemoteLibraryService remoteLibraryService;

    public Map<Artist, SortedSet<Album>> findMissingAlbums(List<Artist> artists, String genre, String year, Path path) {
        HashMap<Artist, SortedSet<Album>> map = new HashMap<>();
        Library localLibrary = filter(localLibraryService.library(path), artists, genre);
        Library remoteLibrary = remoteLibraryService.library(localLibrary);

        for (Artist remoteArtist : remoteLibrary.getArtists()) {
            Optional<Artist> localArtist = Model.find(localLibrary, remoteArtist.getName());
            if (localArtist.isPresent()) {
                SortedSet<Album> albums = new TreeSet<>(Model.missingAlbums(localArtist.get(), remoteArtist));

                String fromYear = fromYear(year, albums);
                Params.logger.info(fromYear);
                if (CollectionUtils.isNotEmpty(albums)) {
                    albums = albums.stream()
                        .filter(a -> a.isAfter(fromYear))
                        .collect(Collectors.toCollection(TreeSet::new));
                    map.put(remoteArtist, albums);
                }
            }
        }
        return map;
    }

    private static String fromYear(String year, SortedSet<Album> albums) {
        if(StringUtils.isNotBlank(year)){
            return year;
        }
        return albums.stream()
            .map(Album::getYear)
            .max(Comparator.naturalOrder())
            .orElse("0");
    }

    private static Library filter(Library library, List<Artist> artists, String genre) {
        if (CollectionUtils.isEmpty(artists) && StringUtils.isBlank(genre)) {
            return library;
        }
        Library filteredLibrary = new Library();
        filteredLibrary.setArtists(library.getArtists()
                .stream()
                .filter(artist -> matches(artist, artists, genre))
                .collect(Collectors.toSet()));
        return filteredLibrary;
    }

    private static boolean matches(Artist artist, List<Artist> artists, String genre) {
        if (CollectionUtils.isEmpty(artists)) {
            return StringUtils.isBlank(genre) || StringUtils.equalsIgnoreCase(genre, artist.getGenre());
        }
        return artists.contains(artist) || StringUtils.equalsIgnoreCase(genre, artist.getGenre());
    }
}