package fr.plaisance.arn.service.impl;

import fr.plaisance.arn.main.Params;
import fr.plaisance.arn.model.Artist;
import fr.plaisance.arn.model.Library;
import fr.plaisance.arn.model.Model;
import fr.plaisance.arn.service.LocalLibraryService;
import fr.plaisance.arn.service.TagService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.blinkenlights.jid3.v1.ID3V1Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

@Service
public class FilesLibraryService implements LocalLibraryService {

    private static final int BAR_SIZE = 75;

    @Autowired
    private TagService tagService;

    @Override
    public Library library(Path path) {
        try {
            System.out.println("Estimating total time…");
            // long total = Files.find(path, 5, (p, a) -> a.isDirectory()).count() - 1;
            TotalFileVisitor totalFileVisitor = new TotalFileVisitor();
            Files.walkFileTree(path, totalFileVisitor);
            int total = totalFileVisitor.getTotal();

            System.out.println(String.format("Analysing local music library [%s]", path.toString()));
            AlbumsFileVisitor albumsFileVisitor = new AlbumsFileVisitor(total);
            Files.walkFileTree(path, albumsFileVisitor);
            System.out.println();

            return Model.newLibrary(albumsFileVisitor.getArtists());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private class TotalFileVisitor extends SimpleFileVisitor<Path> {

        private int total;

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            if (!attrs.isDirectory()) {
                total++;
                if (FilenameUtils.isExtension(file.toFile().getName(), "mp3")) {
                    return Params.getInstance().skipSiblings ? FileVisitResult.SKIP_SIBLINGS : FileVisitResult.CONTINUE;
                }
            }
            return FileVisitResult.CONTINUE;
        }

        public int getTotal() {
            return total;
        }
    }

    private class AlbumsFileVisitor extends SimpleFileVisitor<Path> {

        private Map<String, Artist> artists = new HashMap<>();
        private int total;
        private int nombre = 0;
        private int previousStep = -1;

        public AlbumsFileVisitor(int total) {
            this.total = total;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            if (!attrs.isDirectory()) {
                nombre++;
                printProgressBar();
                if (FilenameUtils.isExtension(file.toFile().getName(), "mp3")) {
                    ID3V1Tag tag = tagService.tag(file.toFile().getAbsolutePath());
                    if (tag != null) {
                        tagService.update(artists, tag);
                        return Params.getInstance().skipSiblings ? FileVisitResult.SKIP_SIBLINGS : FileVisitResult.CONTINUE;
                    } else {
                        // TODO Gérer les erreurs : au moins un booléen pour dire qu'il y en a. Dans l'idéal, la liste des fichiers en erreur.
                        // TODO String.format("Impossible to extract infos from file '%s'", file.toFile().getName())
                    }
                }
            }
            return FileVisitResult.CONTINUE;
        }

        private void printProgressBar() {
            int currentStep = Math.min((BAR_SIZE * nombre) / total, BAR_SIZE);
            if (previousStep < currentStep) {
                previousStep = currentStep;
                int pourcentage = (currentStep * 100) / BAR_SIZE;
                System.out.print(
                        StringUtils.rightPad("\r[", currentStep + 1, "=") +
                        StringUtils.leftPad("]", BAR_SIZE + 1 - currentStep, " ") +
                        " " + pourcentage + "% ");
            }
        }

        public SortedSet<Artist> getArtists() {
            return new TreeSet<>(artists.values());
        }
    }
}