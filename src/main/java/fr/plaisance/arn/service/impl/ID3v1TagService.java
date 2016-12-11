package fr.plaisance.arn.service.impl;

import java.io.File;
import java.util.Map;

import org.blinkenlights.jid3.MP3File;
import org.blinkenlights.jid3.v1.ID3V1Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import fr.plaisance.arn.model.Album;
import fr.plaisance.arn.model.Artist;
import fr.plaisance.arn.model.Model;
import fr.plaisance.arn.service.TagService;

@Service
public class ID3v1TagService implements TagService {

	private static final Logger logger = LoggerFactory.getLogger(ID3v1TagService.class);

	@Override
	public Album album(ID3V1Tag tag) {
		return Model.newAlbum(tag.getAlbum(), tag.getYear());
	}

	@Override
	public Artist artist(ID3V1Tag tag) {
		return Model.newArtist(tag.getArtist());
	}

	@Override
	public ID3V1Tag tag(String path) {
		try {
			MP3File mp3 = new MP3File(new File(path));
			return mp3.getID3V1Tag();
		}
		catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Map<String, Artist> update(Map<String, Artist> artists, ID3V1Tag tag) {
		Album album = this.album(tag);
		Artist artist = this.artist(tag);
		String name = artist.getName();
		if (artists.containsKey(name)) {
			artist = artists.get(name);
		}
		else {
			artists.put(name, artist);
		}
		Model.addAlbumToArtist(artist, album);
		return artists;
	}

}
