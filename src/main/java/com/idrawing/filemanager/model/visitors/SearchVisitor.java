package com.idrawing.filemanager.model.visitors;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.io.Files;
import com.idrawing.filemanager.domain.LocalFile;
import lombok.*;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.stream.Stream;

import static com.google.common.io.Files.getFileExtension;

/**
 * Created by Admin on 19.02.2017.
 */
@Getter
@Setter
@RequiredArgsConstructor
public class SearchVisitor implements FileVisitor<Path> {
    @NonNull
    private String[] extension;
    private Collection<LocalFile> files = new ArrayList<>();

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (isEqualExtension(file)) {
            files.add(new LocalFile(file));
        }
        return FileVisitResult.CONTINUE;
    }

    private boolean isEqualExtension(Path file) {
        String fileExtension = getFileExtension(file.toAbsolutePath().toString());
        return Stream.of(extension).anyMatch(ext -> ext.equalsIgnoreCase(fileExtension));
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }
}