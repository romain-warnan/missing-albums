package fr.plaisance.arn.bean;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import java.util.UUID;

public class MusicBrainzReleases {

    private MusicBrainzReleaseGroupList groupList;

    @XmlElement(name = "release-group-list")
    public MusicBrainzReleaseGroupList getList() {
        return groupList;
    }

    public void setList(MusicBrainzReleaseGroupList groupList) {
        this.groupList = groupList;
    }

    public static class MusicBrainzReleaseGroupList {

        private List<MusicBrainzReleaseGroup> releases;

        @XmlElement(name = "release-group")
        public List<MusicBrainzReleaseGroup> getReleases() {
            return releases;
        }

        public void setReleases(List<MusicBrainzReleaseGroup> releases) {
            this.releases = releases;
        }

        public static class MusicBrainzReleaseGroup {

            private UUID id;
            private Integer score;

            @XmlAttribute(name = "id")
            public UUID getId() {
                return id;
            }

            public void setId(UUID id) {
                this.id = id;
            }

            @XmlAttribute(name = "score", namespace = "http://musicbrainz.org/ns/ext#-2.0")
            public Integer getScore() {
                return score;
            }

            public void setScore(Integer score) {
                this.score = score;
            }
        }
    }
}
