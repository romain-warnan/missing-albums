package fr.plaisance.arn.service.impl;

import fr.plaisance.arn.musicbrainz.ArtistCredit;
import fr.plaisance.arn.musicbrainz.MusicBrainzArtist;
import fr.plaisance.arn.musicbrainz.ReleaseGroup;
import fr.plaisance.arn.musicbrainz.ReleaseGroups;
import fr.plaisance.arn.main.Params;
import fr.plaisance.arn.model.Artist;
import fr.plaisance.arn.model.Model;
import fr.plaisance.arn.service.ArtistFinder;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Properties;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class MusicBrainzFinder implements ArtistFinder {

    private static String HOST, RELEASE_QUERY, USER_AGENT;
    private static Client client;

    private final Properties properties;

    @Autowired
    public MusicBrainzFinder(Properties properties) {
        this.properties = properties;
    }

    @PostConstruct
    private void postConstruct() {
        this.retrieveProperties();
        this.configureClient();
    }

    private void configureClient() {
        ClientConfig config = new ClientConfig();
        if (StringUtils.isNotBlank(Params.getInstance().proxy)) {
            config.connectorProvider(new ApacheConnectorProvider());
            config.property(ClientProperties.PROXY_URI, Params.getInstance().proxy);
        }
        client = ClientBuilder.newClient(config);
    }

    private void retrieveProperties() {
        HOST = properties.getProperty("musicbrainz.host");
        RELEASE_QUERY = properties.getProperty("release.query");
        USER_AGENT = properties.getProperty("user.agent");
    }


    @Override
    public Artist find(String name) {
        Artist artist = Model.newArtist(name);
        List<ReleaseGroup> releaseGroups = this.releaseGroups(name);
        this.addReleasesToArtist(artist, releaseGroups);
        return artist;
    }

    private MusicBrainzArtist artist(ArtistCredit artistCredit) {
        return client.target(HOST)
                .path("ws/2")
                .path("artist")
                .path(artistCredit.getArtist().getId().toString())
                .queryParam("limit", 100)
                .queryParam("inc", "release-groups")
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.USER_AGENT, USER_AGENT)
                .get(MusicBrainzArtist.class);
    }

    private List<ReleaseGroup> releaseGroups(String artistName) {
        this.sleep();
        return client.target(HOST)
                .path("ws/2")
                .path("release-group")
                .queryParam("limit", 100)
                .queryParam("query", String.format(RELEASE_QUERY, artistName))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.USER_AGENT, USER_AGENT)
                .get(ReleaseGroups.class).getReleaseGroups()
                .stream()
                .filter(release -> release.getScore() > 90)
                .collect(Collectors.toList());
    }

    private void addReleasesToArtist(Artist artist, List<ReleaseGroup> releaseGroups) {
        releaseGroups
                .stream()
                .filter(group -> group.getScore() >= 90)
                .findFirst()
                .map(releaseGroup -> releaseGroup.getArtistCredits()
                        .stream()
                        .findFirst()
                        .map(this::artist))
                .ifPresent(
                        a -> a.ifPresent(
                                b -> artist.setAlbums(b.getReleaseGroups()
                                        .stream()
                                        .filter(releaseGroups::contains)
                                        .map(releaseGroup -> Model.newAlbum(releaseGroup.getTitle(), releaseGroup.getReleaseDate()))
                                        .collect(Collectors.toCollection(TreeSet::new)))));
    }

    private void sleep() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
