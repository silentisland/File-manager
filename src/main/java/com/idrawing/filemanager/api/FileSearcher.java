package com.idrawing.filemanager.api;

import com.idrawing.filemanager.domain.LocalFile;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Admin on 19.02.2017.
 */
public interface FileSearcher {
    Iterable<LocalFile> findFilesByExtension(Path startPath, String ... extension) throws IOException;

    Iterable<LocalFile> findAllFilesByExtension(String ... extension) throws IOException;

    Iterable<LocalFile> findFilesByExtensionPathSet(Iterable<Path> paths, String ... extension) throws IOException;

    Iterable<LocalFile> findAllFilesBetweenTwoDates(LocalDateTime from, LocalDateTime to, String ... extension) throws IOException;
}