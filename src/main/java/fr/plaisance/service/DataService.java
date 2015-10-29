package fr.plaisance.service;

import java.util.List;

import fr.plaisance.model.Challenge;

public interface DataService {

	/**
	 * Retrieve all the challenges contained in the given file.
	 * The file must be comma separated.
	 * 
	 * @param name - The name of the file in which are located the data.
	 * @return A list of all the challenges loaded from the given file.
	 */
	List<Challenge> load(String name);
}
