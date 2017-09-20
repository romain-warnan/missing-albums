package fr.plaisance.arn.service;

import fr.plaisance.arn.model.Artist;

public interface ArtistFinder {

	Artist find(String name);
}