package fr.plaisance.arn.service;

import fr.plaisance.arn.model.Artist;

public interface DiscogsService {

	Artist find(String name);
}