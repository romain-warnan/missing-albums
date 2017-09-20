package fr.plaisance.arn.bean;

import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TestWs {

    private static final String HOST = "https://musicbrainz.org";
    private static final String QUERY = "artist:\"%s\" AND status:official AND primarytype:album NOT secondarytype:[* TO *]";

    public static void main(String[] args) {

        // Création client
        ClientConfig config = new ClientConfig();
        config.connectorProvider(new ApacheConnectorProvider());
        config.property(ClientProperties.PROXY_URI, "http://proxy-rie.http.insee.fr:8080");
        Client client = ClientBuilder.newClient(config);
//
//        for (String l :
//                Collections.list(LogManager.getLogManager().getLoggerNames())) {
//            if (l.startsWith("com.sun.jersey")) {
//                Logger.getLogger(l).setLevel(Level.OFF);
//            }
//        }

        // Liste des identifiants de releases
        List<UUID> releases = client.target(HOST)
                .path("/ws/2/")
                .path("release-group")
                .queryParam("limit", 100)
                .queryParam("query", String.format(QUERY, "Metallica"))
                .request(MediaType.APPLICATION_XML)
                .get(MusicBrainzReleases.class)
                .getList()
                .getReleases()
                .stream()
                .filter(release -> release.getScore() > 90)
                .map(release -> release.getId())
                .collect(Collectors.toList());

        releases.stream()
                .forEach(release -> {
                    MusicBrainzAlbum.MusicBrainzReleaseGroup album = client.target(HOST)
                            .path("/ws/2/")
                            .path("release-group")
                            .path(release.toString())
                            .request(MediaType.APPLICATION_XML)
                            .get(MusicBrainzAlbum.class)
                            .getAlbum();
                    System.out.println("album = " + album.getYear() + ": " + album.getTitle());
                });

    }
}
