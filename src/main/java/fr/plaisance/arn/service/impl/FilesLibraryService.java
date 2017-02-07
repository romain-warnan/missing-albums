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
        try {
            TotalFileVisitor totalFileVisitor = new TotalFileVisitor();
            Files.walkFileTree(path, totalFileVisitor);
            AlbumsFileVisitor albumsFileVisitor = new AlbumsFileVisitor(totalFileVisitor.getTotal());
            // System.out.println("============================");
            Files.walkFileTree(path, albumsFileVisitor);
            System.out.println();
            return Model.newLibrary(albumsFileVisitor.getArtists());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private class TotalFileVisitor extends SimpleFileVisitor<Path> {

        private int total = 0;

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            // System.out.println(file.toString());
            if (!attrs.isDirectory() && FilenameUtils.isExtension(file.toFile().getName(), "mp3")) {
                total++;
                return FileVisitResult.SKIP_SIBLINGS;
            }
            return FileVisitResult.CONTINUE;
        }

        public int getTotal() {
            return total;
        }
    }

    private class AlbumsFileVisitor extends SimpleFileVisitor<Path> {

        private Map<String, Artist> artists = new HashMap<>();
        private int nombre = 0;
        private int total;
        private int previousStep = -1;

        public AlbumsFileVisitor(int total) {
            this.total = total;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            // System.out.println(file.toString());
            if (!attrs.isDirectory()) {
                nombre++;
                printProgressBar();
                if (FilenameUtils.isExtension(file.toFile().getName(), "mp3")) {
                    // System.out.println(String.format("Scanning folder [%s]", file.getParent().toString()));
                    ID3V1Tag tag = tagService.tag(file.toFile().getAbsolutePath());
                    if (tag != null) {
                        tagService.update(artists, tag);
                        return Params.getInstance().skipSiblings ? FileVisitResult.SKIP_SIBLINGS : FileVisitResult.CONTINUE;
                    } else {
                        // System.out.println(String.format("Impossible to extract infos from file '%s'", file.toFile().getName()));
                    }
                }
            }
            return FileVisitResult.CONTINUE;
        }

        private void printProgressBar() {
            int currentStep = (100 * nombre) / total;
            if(previousStep < currentStep){
                previousStep = currentStep;
                System.out.print(
                    StringUtils.rightPad("\r[", currentStep, "=") +
                    StringUtils.leftPad("]", 100 - currentStep, " ") +
                    " " +  currentStep + "% " +
                    nombre + "/" + total);
            }
        }

        public SortedSet<Artist> getArtists() {
            return new TreeSet<>(artists.values());
        }
    }
}