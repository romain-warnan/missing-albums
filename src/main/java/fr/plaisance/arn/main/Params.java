package fr.plaisance.arn.main;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.CommaParameterSplitter;
import fr.plaisance.arn.model.Artist;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Params {

    private static Params instance;

    private Params(){
        // Private
    }

    public static Params getInstance(){
        if(instance == null){
            instance = new Params();
        }
        return instance;
    }

    @Parameter(names = {"-a", "--artists"}, converter = ArtistConverter.class, splitter = CommaParameterSplitter.class, description =
        "Comma separated liste of artist's names you are interrested in. " +
        "Example: --artists \"Amon Amarth,Bjork\"")
    public List<Artist> artists;

    @Parameter(names = {"-s", "--skip-siblings"}, description =
         "If this flag is set, the program will assume that one folder contains only songs of the same album. " +
         "This is much faster so it is suggested that you put your music library in order and use this option. " +
         "Example: --skipSiblings")
    public Boolean skipSiblings = false;

    @Parameter(names = {"-y", "--year"}, description =
        "Missing albums will not be displayed if they were released before this year. " +
        "Example: --year 2015")
    public String year = null;

    @Parameter(names = {"-h", "--help"}, help = true, description =
        "Display this message. " +
        "Example: --help")
    public Boolean help = false;

    @Parameter(names = {"-p", "--path"}, description =
        "The folder in which the program will recursively search for audio files. " +
        "If the path is not specified, the program will search in the current directory. " +
        "Examples: --path /mnt/audio/music, --path \"D:\\Mes Documents\\Music\"")
    public Path path = Paths.get(".");
}
