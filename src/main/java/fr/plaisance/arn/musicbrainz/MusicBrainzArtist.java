package fr.plaisance.arn.musicbrainz;

import com.owlike.genson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public class MusicBrainzArtist {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("release-groups")
    private List<ReleaseGroup> releaseGroups;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ReleaseGroup> getReleaseGroups() {
        return releaseGroups;
    }

    public void setReleaseGroups(List<ReleaseGroup> releaseGroups) {
        this.releaseGroups = releaseGroups;
    }
}
