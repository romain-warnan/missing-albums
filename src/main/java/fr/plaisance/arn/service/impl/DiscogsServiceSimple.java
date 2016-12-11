package fr.plaisance.arn.service.impl;

import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.HttpHeaders;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import fr.plaisance.arn.bean.DiscogsQuery;
import fr.plaisance.arn.bean.DiscogsQuery.DiscogsArtist;
import fr.plaisance.arn.bean.DiscogsReleases;
import fr.plaisance.arn.model.Album;
import fr.plaisance.arn.model.Artist;
import fr.plaisance.arn.model.Model;
import fr.plaisance.arn.service.DiscogsService;

@Service
public class DiscogsServiceSimple implements DiscogsService {

	private static final Logger logger = LoggerFactory.getLogger(DiscogsServiceSimple.class);

	@Autowired
	private Properties properties;

	@Override
	public Artist find(String name) {
		Artist artist = Model.newArtist(name);
		DiscogsQuery query = this.query(name);
		if (CollectionUtils.isNotEmpty(query.getArtists())) {
			DiscogsReleases releases = this.releases(query.getArtists().get(0));
			Set<Album> albums = releases
				.getAlbums()
				.stream()
				.filter(a -> StringUtils.equals(a.getType(), "master"))
				.map(a -> Model.newAlbum(a.getTitle(), String.valueOf(a.getYear())))
				.collect(Collectors.toSet());
			artist.setAlbums(new TreeSet<>(albums));
		}
		return artist;
	}

	private DiscogsQuery query(String artistName) {
		logger.info(artistName);
		Client client = ClientBuilder.newClient();
		DiscogsQuery result = client
			.target("https://api.discogs.com")
			.path("database/search")
			.queryParam("q", artistName)
			.queryParam("type", "artist")
			.queryParam("per_page", "1")
			.request(MediaType.APPLICATION_JSON_VALUE)
			.header(HttpHeaders.USER_AGENT, this.userAgent())
			.header(HttpHeaders.ACCEPT, this.version())
			.header(HttpHeaders.AUTHORIZATION, this.authorization())
			.get(DiscogsQuery.class);
		return result;
	}

	private DiscogsReleases releases(DiscogsArtist artist) {
		logger.info(artist.getResourceUrl());
		Client client = ClientBuilder.newClient();
		DiscogsReleases result = client
			.target(artist.getResourceUrl())
			.path("releases")
			.queryParam("per_page", "100")
			.queryParam("sort", "year")
			.queryParam("sort_order", "desc")
			.request(MediaType.APPLICATION_JSON_VALUE)
			.header(HttpHeaders.USER_AGENT, this.userAgent())
			.header(HttpHeaders.ACCEPT, this.version())
			.header(HttpHeaders.AUTHORIZATION, this.authorization())
			.get(DiscogsReleases.class);
		return result;
	}

	private String version() {
		return this.properties.getProperty("discogs.version");
	}

	private String userAgent() {
		return this.properties.getProperty("discogs.useragent");
	}

	private String authorization() {
		String key = this.properties.getProperty("discogs.key");
		String secret = this.properties.getProperty("discogs.secret");
		return "Discogs key=" + key + ", secret=" + secret;
	}
}
