package fr.plaisance.arn.musicbrainz;

import com.owlike.genson.annotation.JsonProperty;

import java.util.UUID;

public class Release {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("title")
    private String title;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
