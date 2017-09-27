package fr.plaisance.arn.service.impl;

import fr.plaisance.arn.bean.MusicBrainzAlbum;
import fr.plaisance.arn.bean.MusicBrainzReleases;
import fr.plaisance.arn.bean2.ArtistCredit;
import fr.plaisance.arn.bean2.MusicBrainzArtist;
import fr.plaisance.arn.bean2.ReleaseGroup;
import fr.plaisance.arn.bean2.ReleaseGroups;
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
import java.util.*;
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

    @Override
    public Artist find(String name) {
        List<ReleaseGroup> releaseGroups = this.releaseGroups(name)
                .getReleaseGroups()
                .stream()
                .filter(release -> release.getScore() > 90)
                .collect(Collectors.toList());

        Optional<Optional<MusicBrainzArtist>> musicBrainzArtist = releaseGroups
                .stream()
                .filter(group -> group.getScore() >= 90)
                .findFirst()
                .map(releaseGroup -> releaseGroup.getArtistCredits()
                        .stream()
                        .findFirst()
                        .map(this::artist));

        Artist artist = Model.newArtist(name);
        musicBrainzArtist.ifPresent(
                a -> a.ifPresent(
                        b -> artist.setAlbums(b.getReleaseGroups()
                                .stream()
                                .filter(releaseGroup -> releaseGroups.contains(releaseGroup))
                                .map(r -> Model.newAlbum(r.getTitle(), r.getReleaseDate()))
                                .collect(Collectors.toCollection(TreeSet::new)))));

        return artist;
    }

    private ReleaseGroups releaseGroups(String artistName) {
        this.sleep();
        return client.target(HOST)
                .path("ws/2")
                .path("release-group")
                .queryParam("limit", 100)
                .queryParam("query", String.format(RELEASE_QUERY, artistName))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.USER_AGENT, USER_AGENT)
                .get(ReleaseGroups.class);
    }

    private List<UUID> releases(String artistName) {
        this.sleep();
        return client.target(HOST)
                .path("ws/2")
                .path("release-group")
                .queryParam("limit", 100)
                .queryParam("query", String.format(RELEASE_QUERY, artistName))
                .request(MediaType.APPLICATION_XML)
                .header(HttpHeaders.USER_AGENT, USER_AGENT)
                .get(MusicBrainzReleases.class)
                .getList()
                .getReleases()
                .stream()
                .filter(release -> release.getScore() > 90)
                .map(release -> release.getId())
                .collect(Collectors.toList());
    }

    private MusicBrainzAlbum.MusicBrainzReleaseGroup album(UUID release) {
        this.sleep();
        return client.target(HOST)
                .path("ws/2")
                .path("release-group")
                .path(release.toString())
                .request(MediaType.APPLICATION_XML)
                .header(HttpHeaders.USER_AGENT, USER_AGENT)
                .get(MusicBrainzAlbum.class)
                .getAlbum();
    }

    private void sleep() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
