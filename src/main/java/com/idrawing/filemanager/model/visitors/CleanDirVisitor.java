package com.idrawing.filemanager.model.visitors;

import lombok.AllArgsConstructor;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

@AllArgsConstructor
public class CleanDirVisitor extends SimpleFileVisitor<Path> {
    private Path from;

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Files.delete(file);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path directory, IOException exception) throws IOException {
        if (exception == null) {
            if(!from.equals(directory))
                Files.delete(directory);
            return FileVisitResult.CONTINUE;
        } else {
            throw exception;
        }
    }
}
