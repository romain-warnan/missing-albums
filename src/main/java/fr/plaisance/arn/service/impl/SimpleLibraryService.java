package fr.plaisance.arn.service.impl;

import fr.plaisance.arn.main.Params;
import fr.plaisance.arn.model.Artist;
import fr.plaisance.arn.model.Library;
import fr.plaisance.arn.model.Model;
import fr.plaisance.arn.service.ArtistFinder;
import fr.plaisance.arn.service.RemoteLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimpleLibraryService implements RemoteLibraryService {

	@Autowired
	private ArtistFinder artistFinder;

	@Override
	public Library library(Library localLibrary) {
		Params.logger.trace("Building remote library from local library");
		Library remoteLibrary = Model.newLibrary();
		for (Artist artist : localLibrary.getArtists()) {
			Artist remoteArtist = artistFinder.find(artist.getName());
			Model.addArtistToLibrary(remoteLibrary, remoteArtist);
		}
		return remoteLibrary;
	}
}
