package fr.plaisance.arn.model;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Model {

	private Model() {}

	public static Artist newArtist(String name) {
		Artist artist = new Artist();
		artist.setName(name);
		return artist;
	}

	public static Artist newArtist(String name, SortedSet<Album> albums) {
		Artist artist = new Artist();
		artist.setName(name);
		artist.setAlbums(albums);
		return artist;
	}

	public static Album newAlbum(String name, String year) {
		Album album = new Album();
		album.setName(name);
		album.setYear(year);
		return album;
	}

	public static Library newLibrary() {
		Library library = new Library();
		library.setArtists(new TreeSet<>());
		return library;
	}

	public static Library newLibrary(Set<Artist> artists) {
		Library library = new Library();
		library.setArtists(artists);
		return library;
	}

	public static Artist addAlbumToArtist(Artist artist, Album album) {
		if (artist.getAlbums() == null) {
			artist.setAlbums(new TreeSet<>());
		}
		artist.getAlbums().add(album);
		return artist;
	}

	public static Library addArtistToLibrary(Library library, Artist artist) {
		if (library.getArtists() == null) {
			library.setArtists(new TreeSet<>());
		}
		library.getArtists().add(artist);
		return library;
	}

    public static Optional<Artist> find(Library library, String name){
        return library.getArtists().stream()
                .filter(artist -> StringUtils.equals(artist.getName(), name))
                .findFirst();
    }

	public static Set<Album> missingAlbums(Artist localArtist, Artist remoteArtist) {
        if(CollectionUtils.isEmpty(remoteArtist.getAlbums())){
            return Collections.emptySet();
        }
		return remoteArtist.getAlbums().stream()
				.filter(album -> !localArtist.getAlbums().contains(album))
				.collect(Collectors.toSet());
	}
}