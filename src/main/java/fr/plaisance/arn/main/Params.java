package fr.plaisance.arn.main;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.CommaParameterSplitter;
import fr.plaisance.arn.model.Artist;
import org.apache.log4j.Logger;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public static final Logger logger = Logger.getLogger("fr.plaisance");

    @Parameter(names = {"-a", "--artists"}, converter = ArtistConverter.class, splitter = CommaParameterSplitter.class, description =
        "Comma separated list of artists names you are interrested in. " +
        "Example: --artists \"Amon Amarth,Bjork\"")
    public List<Artist> artists = new ArrayList<>();


    @Parameter(names = {"-l", "--artist-list"}, converter = ArtistsListConverter.class, description =
        "File that contains a list of artists names you are interrested in. " +
        "One artist name per line. " +
        "Example: --artist-list /home/name/artists.txt")
    public Set<Artist> artistList = new HashSet<>();

    @Parameter(names = {"-q", "--quiet"}, description =
        "Hide log messages. Only the final result is shown." +
        "Example: --quiet")
    public Boolean quiet = false;

    @Parameter(names = {"-v", "--verbose"}, description =
        "Show detail log messages." +
        "Example: --verbose")
    public Boolean verbose = false;

    @Parameter(names = {"-s", "--skip-siblings"}, description =
        "If this flag is set, the program will assume that one folder contains only songs of the same album. " +
        "This is much faster so it is suggested that you put your music library in order and use this option. " +
        "Example: --skip-siblings")
    public Boolean skipSiblings = false;

    @Parameter(names = {"-y", "--year"}, description =
        "If set, missing albums will not be displayed if they were released before this year. " +
        "If not, only missing albums that have been released after the latest one in the library will be displayed. " +
        "Example: --year 2015")
    public String year = null;

    @Parameter(names = {"-g", "--genre"}, description =
        "Search missing albums for all artists of the specified genre. " +
        "Example: --genre Metal")
    public String genre = null;

    @Parameter(names = {"-x", "--proxy"}, description =
        "If your network uses a proxy to connect to the internet, the uri of the proxy server. " +
        "Example: --proxy http://123.15.12.1:8081, --proxy http://proxy.my-company.com:8080")
    public String proxy = null;

    @Parameter(names = {"-f", "--output-format"}, description =
        "Format of the output. There are three formats:" +
        "- csv: comma separated values, " +
        "- tsv: tabulation separated values, " +
        "- list: each values is in a column of fixed sized." +
        "Example: --output-format csv")
    public String format = "list";

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
