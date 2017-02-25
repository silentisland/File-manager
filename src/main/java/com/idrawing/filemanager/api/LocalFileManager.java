package com.idrawing.filemanager.api;

import com.idrawing.filemanager.domain.FileCriteria;
import com.idrawing.filemanager.domain.LocalFile;
import com.idrawing.filemanager.model.visitors.*;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Sergej Povzanyuk on 07.08.2016.
 */
public class LocalFileManager implements FileManager {

    @Override
    public Iterable<Path> getDiscsList() {
        return FileSystems.getDefault().getRootDirectories();
    }

    @Override
    public Path copy(Path from, Path to) throws IOException {
        return Files.copy(from, to);
    }

    @Override
    public boolean rename(Path oldPath, Path newPath) throws IOException {
        return oldPath.toFile().renameTo(newPath.toFile());
    }

    @Override
    public boolean isSameFile(Path path1, Path path2) throws IOException {
        return Arrays.equals(Files.readAllBytes(path1), Files.readAllBytes(path2));
    }

    @Override
    public void delete(Path path) throws IOException {
        Files.delete(path);
    }

    @Override
    public Path create(Path path) throws IOException {
        return Files.createFile(path);
    }

    @Override
    public Path createDirectory(Path directory) throws IOException {
        return Files.createDirectories(directory);
    }

    @Override
    public Path deleteDirectory(Path directory) throws IOException {
        return Files.walkFileTree(directory, new DeleteVisitor());
    }

    @Override
    public Path move(Path from, Path to) throws IOException {
        return Files.move(from, to);
    }

    @Override
    public void moveDirectory(Path from, Path to) throws IOException {
        Files.walkFileTree(from, new CopyDirVisitor(from, to));
        Files.walkFileTree(from, new CleanDirVisitor(from));
    }

    @Override
    public Path cleanDirectory(Path directory) throws IOException {
        return Files.walkFileTree(directory, new CleanDirVisitor(directory));
    }

    @Override
    public Collection<LocalFile> findFilesByExtension(Path startPath, String... extension) throws IOException {
        SearchVisitor sv = new SearchVisitor(extension);
        Files.walkFileTree(startPath, sv);
        return sv.getFiles();
    }

    @Override
    public Collection<LocalFile> findAllFilesByExtension(String... extension) throws IOException {
        return findFilesByExtensionPathSet(getDiscsList(), extension);
    }

    @Override
    public Collection<LocalFile> findFilesByExtensionPathSet(Iterable<Path> paths, String... extension) throws IOException {
        List<LocalFile> result = new ArrayList<>();
        for (Path path : paths) {
            result.addAll(findFilesByExtension(path, extension));
        }
        return result;
    }

    @Override
    public Collection<LocalFile> findAllFilesBetweenTwoDates(LocalDateTime from, LocalDateTime to, String... extension) throws IOException {
        return findAllFilesByExtension(extension)
                .parallelStream()
                .filter(localFile -> localFile.getCreationDate().isAfter(from) && localFile.getCreationDate().isBefore(to))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<LocalFile> findByCriteria(FileCriteria criteria) throws IOException {
        Collection<LocalFile> result = new ArrayList<>();
        CriteriaSearchVisitor csv = new CriteriaSearchVisitor(criteria, result);
        criteria.getPaths().parallelStream().forEach(path -> {
            try {
                Files.walkFileTree(path, csv);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return result;
    }
}