package com.idrawing.filemanager.api;

import com.idrawing.filemanager.domain.LocalFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

/**
 * Created by Admin on 19.02.2017.
 */
public interface FileSearcher {
    List<LocalFile> findFilesByExtension(Path startPath, String extension) throws IOException;

    List<LocalFile> findAllFilesByExtension(String extension) throws IOException;

    List<LocalFile> findFilesByExtensionPathSet(Set<Path> paths, String extension) throws IOException;
}
