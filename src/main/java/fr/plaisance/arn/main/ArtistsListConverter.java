package fr.plaisance.arn.main;

import com.beust.jcommander.IStringConverter;
import fr.plaisance.arn.model.Artist;
import fr.plaisance.arn.model.Model;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class ArtistsListConverter implements IStringConverter<Set<Artist>> {

    @Override
    public Set<Artist> convert(String string) {
        Path path = Paths.get(string);
        try {
            return FileUtils.readLines(path.toFile())
                .stream()
                .map(StringUtils::trim)
                .map(Model::newArtist)
                .collect(Collectors.toSet());
        }
        catch (IOException e) {
            Params.logger.error(e.getMessage());
        }
        return Collections.emptySet();
    }
}
