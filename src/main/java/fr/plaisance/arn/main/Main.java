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


    // TODO String genre
    public static void main(String[] args) {
        Params params = Params.getInstance();
        JCommander commander = new JCommander(params, args);
        commander.setProgramName("Album release notifier");

        if(params.help){
            commander.usage();
            System.exit(0);
        }

        try(AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml")){
            AlbumService albumService = context.getBean("albumService", AlbumService.class);

            if(params.quiet){
                params.logger.setLevel(Level.INFO);
            }

            if(params.verbose){
                params.logger.setLevel(Level.TRACE);
            }

            logger.trace("TRACE");
            logger.debug("DEBUG");
            logger.info("INFO");
            logger.warn("WARN");
            logger.error("ERROR");

            if(CollectionUtils.isNotEmpty(params.artists)) {
                System.out.println(String.format("Detected artists: %s",
                        params.artists.stream().map(Artist::getName).collect(Collectors.joining(", "))));
            }
            if(StringUtils.isNotBlank(params.year)) {
                System.out.println(String.format("Searching for missing albums after year %s",params.year));
            }
            Map<Artist, SortedSet<Album>> map = albumService.findMissingAlbums(params.artists, params.year, params.path);
            map.forEach((artist, albums) -> {
                System.out.println(artist.getName());
                albums.forEach(album -> System.out.println(String.format("* %s — %s", album.getYear(), album.getName())));
            });
        }
    }
}
