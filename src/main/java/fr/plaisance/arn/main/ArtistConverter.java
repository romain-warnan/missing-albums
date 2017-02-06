package fr.plaisance.arn.main;

import com.beust.jcommander.IStringConverter;
import fr.plaisance.arn.model.Artist;
import fr.plaisance.arn.model.Model;

public class ArtistConverter implements IStringConverter<Artist> {

    @Override
    public Artist convert(String string) {
        return Model.newArtist(string);
    }
}
