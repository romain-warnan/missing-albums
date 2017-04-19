package fr.plaisance.arn.main;

import com.beust.jcommander.JCommander;
import fr.plaisance.arn.model.Album;
import fr.plaisance.arn.model.Artist;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;
import java.util.SortedSet;
import java.util.stream.Collectors;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    // TODO 1. Ne récupérer que les albums à l'aide d'une option (instable, ne passe pas par le WS discocgs)
    // TODO 2. Rechercher uniquement les albums après le dernier en date présent dans la bibliothèque locale
    // TODO 3. Lire la liste d'artistes à partir d'un fichier texte
    // TODO 4. Images de l'artiste à copier dans le dossier de l'artist
    public static void main(String[] args) {
        Params params = beforeRunning(args);

        try (AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml")) {
            AlbumService albumService = context.getBean("albumService", AlbumService.class);

            Map<Artist, SortedSet<Album>> map = albumService.findMissingAlbums(params.artists, params.genre, params.year, params.path);
            map.forEach((artist, albums) -> {
                Params.logger.info(artist.getName());
                albums.forEach(album -> Params.logger.info(String.format("* %s - %s", album.getYear(), album.getName())));
            });
        }
    }

    private static Params beforeRunning(String[] args) {
        Params params = Params.getInstance();
        JCommander commander = new JCommander(params, args);
        commander.setProgramName("Album release notifier");

        if (params.help) {
            commander.usage();
            System.exit(0);
        }

        if (params.quiet) {
            Params.logger.setLevel(Level.INFO);
        }

        if (params.verbose) {
            Params.logger.setLevel(Level.TRACE);
        }

        if (CollectionUtils.isNotEmpty(params.artists)) {
            Params.logger.debug(String.format("Detected artists: %s",
                    params.artists.stream().map(Artist::getName).collect(Collectors.joining(", "))));
        }

        if (StringUtils.isNotBlank(params.year)) {
            Params.logger.debug(String.format("Searching for missing albums after year %s", params.year));
        }

        return params;
    }
}
