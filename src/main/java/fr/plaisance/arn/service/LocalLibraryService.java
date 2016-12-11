package fr.plaisance.arn.service;

import java.nio.file.Path;

import fr.plaisance.arn.model.Library;

public interface LocalLibraryService {

	Library library(Path path);

}