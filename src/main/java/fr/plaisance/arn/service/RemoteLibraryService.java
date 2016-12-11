package fr.plaisance.arn.service;

import fr.plaisance.arn.model.Library;

public interface RemoteLibraryService {

	Library library(Library localLibrary);
}
