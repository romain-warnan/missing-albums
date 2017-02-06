package fr.plaisance.arn.main;

import com.beust.jcommander.JCommander;
import com.google.common.collect.Sets;
import fr.plaisance.arn.model.Album;
import fr.plaisance.arn.model.Artist;
import fr.plaisance.arn.model.Model;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.nio.file.Paths;
import java.util.Set;

public class Main {

    // TODO String genre
    // TODO -v -vv -vvv
    public static void main(String[] args){
        Params params = Params.getInstance();
        JCommander commander = new JCommander(params, args);
        commander.setProgramName("Album release notifier", "arn");

        if(params.help){
            commander.usage();
            System.exit(0);
        }

        try(AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml")){
            AlbumService albumService = context.getBean("albumService", AlbumService.class);

            Set<Artist> artists = Sets.newHashSet(Model.newArtist("After Forever"), Model.newArtist("Metallica"));
            Set<Album> albums = albumService.findMissingAlbums(artists, "2015", Paths.get("Z:/share/music/albums"));
            albums.forEach(System.out::println);
        }
    }
}
