package fr.plaisance.arn.service.impl;

import fr.plaisance.arn.main.Params;
import fr.plaisance.arn.model.Artist;
import fr.plaisance.arn.model.Library;
import fr.plaisance.arn.model.Model;
import fr.plaisance.arn.service.LocalLibraryService;
import fr.plaisance.arn.service.TagService;
import org.apache.commons.io.FilenameUtils;
import org.blinkenlights.jid3.v1.ID3V1Tag;
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

	@Autowired
	private TagService tagService;

	@Override
	public Library library(Path path) {
	    System.out.println(String.format("Analysing local music library [%s]", path.toString()));
		AlbumsFileVisitor fileVisitor = new AlbumsFileVisitor();
		try {
			Files.walkFileTree(path, fileVisitor);
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
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
				    System.out.println(String.format("Scanning folder [%s]", file.getParent().toString()));
					ID3V1Tag tag = tagService.tag(file.toFile().getAbsolutePath());
					if (tag != null) {
						tagService.update(artists, tag);
                        return Params.getInstance().skipSiblings ? FileVisitResult.SKIP_SIBLINGS : FileVisitResult.CONTINUE;
					}
					else {
						System.out.println(String.format("Impossible to extract infos from file '%s'", file.toFile().getName()));
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