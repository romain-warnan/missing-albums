package fr.plaisance.arn.bean;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "metadata", namespace = "http://musicbrainz.org/ns/mmd-2.0#")
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
        private Integer count;

        @XmlElement(name = "release-group")
        public List<MusicBrainzReleaseGroup> getReleases() {
            return releases;
        }

        public void setReleases(List<MusicBrainzReleaseGroup> releases) {
            this.releases = releases;
        }

        @XmlAttribute(name = "count")
        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public static class MusicBrainzReleaseGroup {

            private String id, title;
            private Integer score;

            @XmlAttribute(name = "id")
            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            @XmlAttribute(name = "score", namespace = "http://musicbrainz.org/ns/ext#-2.0")
            public Integer getScore() {
                return score;
            }

            public void setScore(Integer score) {
                this.score = score;
            }

            @XmlElement(name = "title")
            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
