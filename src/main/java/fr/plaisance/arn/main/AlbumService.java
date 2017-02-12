package fr.plaisance.arn.main;

import fr.plaisance.arn.model.Album;
import fr.plaisance.arn.model.Artist;
import fr.plaisance.arn.model.Library;
import fr.plaisance.arn.model.Model;
import fr.plaisance.arn.service.LocalLibraryService;
import fr.plaisance.arn.service.RemoteLibraryService;
import org.apache.commons.collections.CollectionUtils;
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

    // TODO String genre
    public Map<Artist, SortedSet<Album>> findMissingAlbums(List<Artist> artists, String year, Path path) {
        HashMap<Artist, SortedSet<Album>> map = new HashMap<>();
        Library localLibrary = filter(localLibraryService.library(path), artists);
        Library remoteLibrary = remoteLibraryService.library(localLibrary);

        for (Artist remoteArtist : remoteLibrary.getArtists()) {
            Optional<Artist> localArtist = Model.find(localLibrary, remoteArtist.getName());
            if(localArtist.isPresent()){
                SortedSet<Album> albums = new TreeSet<>(Model.missingAlbums(localArtist.get(), remoteArtist));
                if(CollectionUtils.isNotEmpty(albums)){
                    albums = albums.stream()
                            .filter(a -> a.isAfter(year))
                            .collect(Collectors.toCollection(TreeSet::new));
                    map.put(remoteArtist, albums);
                }
            }
        }
        return map;
    }

    private static Library filter(Library library, List<Artist> artists) {
        if(CollectionUtils.isEmpty(artists)){
            return library;
        }
        Library filteredLibrary = new Library();
        filteredLibrary.setArtists(library.getArtists()
                .stream()
                .filter(artists::contains)
                .collect(Collectors.toSet()));
        return filteredLibrary;
    }
}