package fr.plaisance.arn.service.impl;

import fr.plaisance.arn.bean.MusicBrainzAlbum;
import fr.plaisance.arn.bean.MusicBrainzReleases;
import fr.plaisance.arn.main.Params;
import fr.plaisance.arn.model.Album;
import fr.plaisance.arn.model.Artist;
import fr.plaisance.arn.model.Model;
import fr.plaisance.arn.service.ArtistFinder;
import org.apache.commons.collections.CollectionUtils;
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

    private static String HOST, QUERY, USER_AGENT;
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
        if(StringUtils.isNotBlank(Params.getInstance().proxy)) {
            config.connectorProvider(new ApacheConnectorProvider());
            config.property(ClientProperties.PROXY_URI, Params.getInstance().proxy);
        }
        client = ClientBuilder.newClient(config);
    }

    private void retrieveProperties() {
	    HOST = properties.getProperty("musicbrainz.host");
	    QUERY = properties.getProperty("albums.query");
	    USER_AGENT = properties.getProperty("user.agent");
    }

	@Override
	public Artist find(String name) {
        Params.logger.debug(String.format("Searching missing albums for artist '%s'", name));
		Artist artist = Model.newArtist(name);
        List<UUID> releases = this.releases(name);
        if (CollectionUtils.isNotEmpty(releases)) {
            Params.logger.trace(String.format("Artist '%s' found in database", name));
			Set<Album> albums = releases
                .stream()
				.map(this::album)
				.map(a -> Model.newAlbum(a.getTitle(), String.valueOf(a.getYear())))
				.collect(Collectors.toSet());
			artist.setAlbums(new TreeSet<>(albums));
		}
		else {
            Params.logger.trace(String.format("Artist '%s' not found!", name));
        }
		return artist;
	}

	private List<UUID> releases(String artistName) {
	    this.sleep();
        return client.target(HOST)
                .path("ws/2")
                .path("release-group")
                .queryParam("limit", 100)
                .queryParam("query", String.format(QUERY, artistName))
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
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
