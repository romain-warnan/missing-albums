package fr.plaisance.arn.service;

import fr.plaisance.arn.model.Album;
import fr.plaisance.arn.model.Artist;
import org.blinkenlights.jid3.v1.ID3V1Tag;

import java.util.Map;

public interface TagService {

	ID3V1Tag tag(String path);

	Album album(ID3V1Tag tag);

	Artist artist(ID3V1Tag tag);

	Map<String, Artist> update(Map<String, Artist> artists, ID3V1Tag tag);
}