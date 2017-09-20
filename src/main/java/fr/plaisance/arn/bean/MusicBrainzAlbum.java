package fr.plaisance.arn.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "metadata", namespace = "http://musicbrainz.org/ns/mmd-2.0#")
public class MusicBrainzAlbum {

    private MusicBrainzReleaseGroup album;

    @XmlElement(name = "release-group")
    public MusicBrainzReleaseGroup getAlbum() {
        return album;
    }

    public void setAlbum(MusicBrainzReleaseGroup album) {
        this.album = album;
    }

    public static class MusicBrainzReleaseGroup {

        private String title;
        private Integer year;

        @XmlElement(name = "title")
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @XmlElement(name = "first-release-date")
        @XmlJavaTypeAdapter(YearAdapter.class)
        public Integer getYear() {
            return year;
        }

        public void setYear(Integer year) {
            this.year = year;
        }
    }
}
