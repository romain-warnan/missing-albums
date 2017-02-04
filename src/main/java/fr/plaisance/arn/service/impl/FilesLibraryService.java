package fr.plaisance.arn.service.impl;

import fr.plaisance.arn.model.Artist;
import fr.plaisance.arn.model.Library;
import fr.plaisance.arn.model.Model;
import fr.plaisance.arn.service.LocalLibraryService;
import fr.plaisance.arn.service.TagService;
import org.apache.commons.io.FilenameUtils;
import org.blinkenlights.jid3.v1.ID3V1Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

@Service
public class FilesLibraryService implements LocalLibraryService {

	private static final Logger logger = LoggerFactory.getLogger(FilesLibraryService.class);

	@Autowired
	private TagService tagService;

	@Override
	public Library library(Path path) {
		AlbumsFileVisitor fileVisitor = new AlbumsFileVisitor();
		try {
			Files.walkFileTree(path, fileVisitor);
		}
		catch (IOException e) {
			logger.error(e.getMessage());
		}
		return Model.newLibrary(fileVisitor.getArtists());
	}

	private class AlbumsFileVisitor extends SimpleFileVisitor<Path> {

		private Map<String, Artist> artists;

		public AlbumsFileVisitor() {
			this.artists = new HashMap<>();
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			if (!attrs.isDirectory()) {
				if (FilenameUtils.isExtension(file.toFile().getName(), "mp3")) {
					ID3V1Tag tag = tagService.tag(file.toFile().getAbsolutePath());
					if (tag != null) {
						tagService.update(artists, tag);
						return FileVisitResult.SKIP_SIBLINGS;
					}
					else {
						logger.error(file.toString());
					}
				}
			}
			return FileVisitResult.CONTINUE;
		}

		public SortedSet<Artist> getArtists() {
			return new TreeSet<>(artists.values());
		}
	}
}