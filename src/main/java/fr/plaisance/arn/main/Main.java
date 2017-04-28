package fr.plaisance.arn.main;

import com.beust.jcommander.JCommander;
import fr.plaisance.arn.model.Album;
import fr.plaisance.arn.model.Artist;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Level;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;
import java.util.SortedSet;
import java.util.stream.Collectors;

public class Main {

    // private static final Logger logger = LoggerFactory.getLogger(Main.class);

    // TODO 1. Rechercher uniquement les albums après le dernier en date présent dans la bibliothèque locale
    // TODO 2. Améliorer le format de sortie (fichier texte, colonnes type ls -l, à voir…)
    // TODO 3. Regex dans l'option --genre
    // TODO 4. Images de l'artiste à copier dans le dossier de l'artist
    public static void main(String[] args) {
        Params params = handleParams(args);

        try (AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml")) {
            AlbumService albumService = context.getBean(AlbumService.class);

            Map<Artist, SortedSet<Album>> map = albumService.findMissingAlbums(params.artists, params.genre, params.year, params.path);

            map.forEach((artist, albums) -> {
                Params.logger.info(artist.getName());
                albums.forEach(album -> Params.logger.info(String.format("* %s - %s", album.getYear(), album.getName())));
            });
        }
    }

    private static Params handleParams(String[] args) {
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

        if (CollectionUtils.isNotEmpty(params.artists) || CollectionUtils.isNotEmpty(params.artistList)) {
            params.artists.addAll(params.artistList);
            Params.logger.debug(String.format("Detected artists: %s",
                params.artists.stream()
                    .map(Artist::getName)
                    .collect(Collectors.joining(", "))));
        }

        if (StringUtils.isNotBlank(params.year)) {
            Params.logger.debug(String.format("Searching for missing albums after year %s", params.year));
        }

        return params;
    }
}
