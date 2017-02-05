package fr.plaisance.arn.model;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import org.apache.commons.lang3.StringUtils;

import java.util.SortedSet;
import java.util.TreeSet;

public class Artist implements Comparable<Artist> {

    private String name;
    private SortedSet<Album> albums = new TreeSet<>();

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
            return StringUtils.equalsIgnoreCase(this.name, other.name);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(StringUtils.lowerCase(this.name));
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
