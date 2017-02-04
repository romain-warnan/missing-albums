package fr.plaisance.arn.service;

import fr.plaisance.arn.model.Library;

import java.nio.file.Path;

public interface LocalLibraryService {

	Library library(Path path);
}