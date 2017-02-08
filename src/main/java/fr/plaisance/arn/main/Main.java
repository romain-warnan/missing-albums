package fr.plaisance.arn.main;

import com.beust.jcommander.JCommander;
import fr.plaisance.arn.model.Album;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Set;

public class Main {

    // TODO String genre
    // TODO -v -vv -vvv
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

            Set<Album> albums = albumService.findMissingAlbums(params.artists, params.year, params.path);
            albums.forEach(System.out::println);
        }
    }
}
