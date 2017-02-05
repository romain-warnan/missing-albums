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
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AlbumService {

    @Autowired
    private LocalLibraryService localLibraryService;

    @Autowired
    private RemoteLibraryService remoteLibraryService;

    // TODO Boolean skipSiblings
    // TODO String genre
    public Set<Album> findMissingAlbums(Set<Artist> artists, String year, Path path) {
        Set<Album> missingAlbums = new LinkedHashSet<>();

        Library localLibrary = filter(localLibraryService.library(path), artists);
        Library remoteLibrary = remoteLibraryService.library(localLibrary);

        for (Artist remoteArtist : remoteLibrary.getArtists()) {
            Optional<Artist> localArtist = Model.find(localLibrary, remoteArtist.getName());
            if(localArtist.isPresent()){
                Set<Album> albums = Model.missingAlbums(localArtist.get(), remoteArtist);
                if(CollectionUtils.isNotEmpty(albums)){
                    missingAlbums.addAll(albums.stream()
                            .filter(a -> a.isAfter(year))
                            .collect(Collectors.toSet()));
                }
            }
        }

        return missingAlbums;
    }

    private static Library filter(Library library, Set<Artist> artists) {
        if(CollectionUtils.isEmpty(artists)){
            return library;
        }
        Library filteredLibrary = new Library();
        filteredLibrary.setArtists(library.getArtists()
                .stream()
                .filter(a -> artists.contains(a))
                .collect(Collectors.toSet()));
        return filteredLibrary;
    }
}
// filtre artist (liste), genre, date de début de l'album, skip siblings, path (par défaut répertoire courant)
