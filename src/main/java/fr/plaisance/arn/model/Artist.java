package fr.plaisance.arn.model;

import java.util.SortedSet;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

public class Artist implements Comparable<Artist> {

	private String name;
	private SortedSet<Album> albums;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SortedSet<Album> getAlbums() {
		return albums;
	}

	public void setAlbums(SortedSet<Album> albums) {
		this.albums = albums;
	}

	@Override
	public boolean equals(Object object) {
		if (object != null && object instanceof Artist) {
			Artist other = (Artist) object;
			return Objects.equal(this.name, other.name);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.name);
	}

	@Override
	public int compareTo(Artist other) {
		return ComparisonChain.start().compare(this.name, other.name).result();
	}

	@Override
	public String toString() {
		return this.name;
	}

}
