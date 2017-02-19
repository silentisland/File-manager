package com.idrawing.filemanager.model.visitors;

import com.google.common.io.Files;
import com.idrawing.filemanager.domain.LocalFile;
import lombok.*;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.io.Files.getFileExtension;

/**
 * Created by Admin on 19.02.2017.
 */

@Getter
@Setter
@RequiredArgsConstructor
public class SearchVisitor implements FileVisitor<Path> {
    @NonNull
    private String extension;
    private List<LocalFile> files = new ArrayList<>();

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if(getFileExtension(file.toAbsolutePath().toString()).toLowerCase().equals(extension.toLowerCase())){
            files.add(new LocalFile(file));
        }
        return FileVisitResult.CONTINUE;
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