package fr.plaisance.arn.bean2;

import com.owlike.genson.annotation.JsonConverter;
import com.owlike.genson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ReleaseGroup {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("score")
    private Integer score;

    @JsonProperty("artist-credit")
    private List<ArtistCredit> artistCredits;

    @JsonProperty("releases")
    private List<Release> releases;

    @JsonProperty("title")
    private String title;

    @JsonProperty("first-release-date")
    @JsonConverter(YearConverter.class)
    private String releaseDate;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public List<ArtistCredit> getArtistCredits() {
        return artistCredits;
    }

    public void setArtistCredits(List<ArtistCredit> artistCredits) {
        this.artistCredits = artistCredits;
    }

    public List<Release> getReleases() {
        return releases;
    }

    public void setReleases(List<Release> releases) {
        this.releases = releases;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof ReleaseGroup) {
            ReleaseGroup other = (ReleaseGroup) object;
            return Objects.equals(this.id, other.id);
        }
        return false;
    }
}
