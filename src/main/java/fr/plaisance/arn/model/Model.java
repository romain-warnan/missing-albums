package fr.plaisance.arn.model;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

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
}
