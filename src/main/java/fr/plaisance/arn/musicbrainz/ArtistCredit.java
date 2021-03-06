package fr.plaisance.arn.musicbrainz;

import com.owlike.genson.annotation.JsonProperty;

public class ArtistCredit {

    @JsonProperty("artist")
    private MusicBrainzArtist artist;

    public MusicBrainzArtist getArtist() {
        return artist;
    }

    public void setArtists(MusicBrainzArtist artist) {
        this.artist = artist;
    }
}
