package fr.plaisance.arn.service.impl;

import fr.plaisance.arn.bean.DiscogsQuery;
import fr.plaisance.arn.bean.DiscogsQuery.DiscogsArtist;
import fr.plaisance.arn.bean.DiscogsReleases;
import fr.plaisance.arn.main.Params;
import fr.plaisance.arn.model.Album;
import fr.plaisance.arn.model.Artist;
import fr.plaisance.arn.model.Model;
import fr.plaisance.arn.service.DiscogsService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.HttpHeaders;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class DiscogsServiceSimple implements DiscogsService {

	@Autowired
	private Properties properties;

	@Override
	public Artist find(String name) {
        Params.logger.debug(String.format("Searching missing albums for artist '%s'", name));
		Artist artist = Model.newArtist(name);
		DiscogsQuery query = this.query(name);
		if (CollectionUtils.isNotEmpty(query.getArtists())) {
            Params.logger.trace(String.format("Artist '%s' found in database", name));
            DiscogsReleases releases = this.releases(query.getArtists().get(0));
			Set<Album> albums = releases
				.getAlbums()
				.stream()
				.filter(a -> StringUtils.equals(a.getType(), "master"))
				.map(a -> Model.newAlbum(a.getTitle(), String.valueOf(a.getYear())))
				.collect(Collectors.toSet());
			artist.setAlbums(new TreeSet<>(albums));
		}
		else {
            Params.logger.trace(String.format("Artist '%s' not found!", name));
        }
		return artist;
	}

	private DiscogsQuery query(String artistName) {
		Params.logger.trace(String.format("Search database for artist '%s'", artistName));
		return ClientBuilder.newClient()
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
	}

	private DiscogsReleases releases(DiscogsArtist artist){
		if(Params.getInstance().onlyAlbums){
			return this.onlyAlbums(artist);
		}
		return this.allReleases(artist);
	}

	private DiscogsReleases allReleases(DiscogsArtist artist) {
		Params.logger.trace(String.format("Fetch all releases at URL [%s]", artist.getResourceUrl()));
		Client client = ClientBuilder.newClient();
		return client
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
	}

	private DiscogsReleases onlyAlbums(DiscogsArtist artist) {
		Params.logger.trace(String.format("Fetch only albums releases at URL [%s]", artist.getResourceUrl()));

		// TODO à implémenter avec Jsoup
		return this.allReleases(artist);
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
