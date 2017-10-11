package fr.plaisance.arn.main;

import com.beust.jcommander.JCommander;
import fr.plaisance.arn.model.Album;
import fr.plaisance.arn.model.Artist;
import fr.plaisance.arn.printer.AlbumsPrinter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Level;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;
import java.util.SortedSet;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Params params = handleParams(args);

        try (AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml")) {
            AlbumService albumService = context.getBean(AlbumService.class);
            Map<Artist, SortedSet<Album>> albums = albumService.findMissingAlbums(params.artists, params.genre, params.year, params.path);
            AlbumsPrinter printer = context.getBean(Params.getInstance().format, AlbumsPrinter.class);
            Params.logger.info(printer.print(albums));
        }
    }

    private static Params handleParams(String[] args) {
        Params params = Params.getInstance();
        JCommander commander = new JCommander(params, args);
        commander.setProgramName("Missing albums");

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
            if (StringUtils.equalsIgnoreCase(params.year, "after")) {
                Params.logger.debug("Searching for missing albums released after the latest of your library");
            }
            else {
                Params.logger.debug(String.format("Searching for missing albums released after year %s", params.year));
            }
        }

        return params;
    }
 }
