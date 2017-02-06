package fr.plaisance.arn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.plaisance.arn.model.Artist;
import fr.plaisance.arn.model.Library;
import fr.plaisance.arn.model.Model;
import fr.plaisance.arn.service.DiscogsService;
import fr.plaisance.arn.service.RemoteLibraryService;

@Service
public class DiscogsLibraryService implements RemoteLibraryService {

	@Autowired
	private DiscogsService discogsService;

	@Override
	public Library library(Library localLibrary) {
		System.out.println("Building remote library from local library");
		Library remoteLibrary = Model.newLibrary();
		for (Artist artist : localLibrary.getArtists()) {
			Artist remoteArtist = discogsService.find(artist.getName());
			Model.addArtistToLibrary(remoteLibrary, remoteArtist);
		}
		return remoteLibrary;
	}
}
