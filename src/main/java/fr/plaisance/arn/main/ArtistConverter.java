package fr.plaisance.arn.main;

import com.beust.jcommander.IStringConverter;
import fr.plaisance.arn.model.Artist;
import fr.plaisance.arn.model.Model;
import org.apache.commons.lang3.StringUtils;

public class ArtistConverter implements IStringConverter<Artist> {

    @Override
    public Artist convert(String string) {
        return Model.newArtist(StringUtils.trim(string));
    }
}
