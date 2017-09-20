package fr.plaisance.arn.bean;

import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.util.List;

public class TestWs {

    private static final String HOST = "https://musicbrainz.org";
    private static final String QUERY = "artist:\"%s\" AND status:official AND primarytype:album NOT secondarytype:[* TO *]";

    public static void main(String[] args) {
        ClientConfig config = new ClientConfig();
        config.connectorProvider(new ApacheConnectorProvider());
        config.property(ClientProperties.PROXY_URI, "http://proxy-rie.http.insee.fr:8080");


        Client client = ClientBuilder.newClient(config);
        List<MusicBrainzReleases.MusicBrainzReleaseGroupList.MusicBrainzReleaseGroup> releases = client.target(HOST)
                .path("/ws/2/")
                .path("release-group")
                .queryParam("limit", 100)
                .queryParam("query", String.format(QUERY, "Metallica"))
                .request(MediaType.APPLICATION_XML)
                .get(MusicBrainzReleases.class)
                .getList()
                .getReleases();
        releases.stream()
                .filter(release -> release.getScore() > 90)
                .forEach(release -> System.out.println(release.getId()));

    }
}
