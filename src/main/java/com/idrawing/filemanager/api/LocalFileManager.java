package com.idrawing.filemanager.api;

import com.idrawing.filemanager.domain.LocalFile;
import com.idrawing.filemanager.model.visitors.CleanDirVisitor;
import com.idrawing.filemanager.model.visitors.CopyDirVisitor;
import com.idrawing.filemanager.model.visitors.DeleteVisitor;
import com.idrawing.filemanager.model.visitors.SearchVisitor;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

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
    public List<LocalFile> findFilesByExtension(Path startPath, String extension) throws IOException {
        SearchVisitor sv = new SearchVisitor(extension);
        Files.walkFileTree(startPath, sv);
        return sv.getFiles();
    }

    @Override
    public List<LocalFile> findAllFilesByExtension(String extension) throws IOException {
        List<LocalFile> result = new ArrayList<>();
        getDiscsList().forEach(path -> {
            try {
                result.addAll(findFilesByExtension(path, extension));
            } catch (IOException ignored) {}
        });
        return result;
    }

    @Override
    public List<LocalFile> findFilesByExtensionPathSet(Set<Path> paths, String extension) throws IOException {
        List<LocalFile> result = new ArrayList<>();
        paths.forEach(path -> {
            try {
                result.addAll(findFilesByExtension(path, extension));
            } catch (IOException ignored) {}
        });
        return result;
    }


}