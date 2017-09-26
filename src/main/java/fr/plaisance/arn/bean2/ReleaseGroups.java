package fr.plaisance.arn.bean2;

import com.owlike.genson.annotation.JsonProperty;

import java.util.List;

public class ReleaseGroups {

    @JsonProperty("release-groups")
    private List<ReleaseGroup> releaseGroups;

    public List<ReleaseGroup> getReleaseGroups() {
        return releaseGroups;
    }

    public void setReleaseGroups(List<ReleaseGroup> releaseGroups) {
        this.releaseGroups = releaseGroups;
    }
}
